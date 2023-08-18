package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
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
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean result = false;
            Optional<Account> optionalFromAccount = getById(fromId);
            Optional<Account> optionalToAccount = getById(toId);
            if (!optionalFromAccount.isEmpty() && !optionalToAccount.isEmpty() && optionalFromAccount.get().amount() >= amount) {
                Account fromAccount = new Account(fromId, optionalFromAccount.get().amount() - amount);
                Account toAccount = new Account(toId, optionalToAccount.get().amount() + amount);
                update(fromAccount);
                update(toAccount);
                result = true;
            }
            return result;
        }
    }
}