package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            boolean result = false;
            if (accounts.putIfAbsent(account.id(), account) == null) {
                result = true;
            }
            return result;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), accounts.get(account.id()), account);
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.remove(id, accounts.get(id));
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            Optional<Account> result = Optional.empty();
            Account account = accounts.get(id);
            if (account != null) {
                result = Optional.of(account);
            }
            return result;
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean result = false;
            if (accounts.containsKey(fromId) && accounts.containsKey(toId) && accounts.get(fromId).amount() >= amount) {
                Account fromAccount = new Account(fromId, accounts.get(fromId).amount() - amount);
                Account toAccount = new Account(toId, accounts.get(toId).amount() + amount);
                update(fromAccount);
                update(toAccount);
                result = true;
            }
            return result;
        }
    }
}