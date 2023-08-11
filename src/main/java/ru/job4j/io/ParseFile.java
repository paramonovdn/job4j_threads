package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) {
        StringBuffer output = new StringBuffer();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int c;
            while ((c = in.read()) != -1) {
                if (filter.test((char) c)) {
                    output.append((char) c);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }

    public String getContent() {
        Predicate<Character> filter = character -> true;
        return content(filter);
    }

    public String getContentWithoutUnicode() {
        Predicate<Character> filter = character -> Character.UnicodeBlock.of(character) == Character.UnicodeBlock.BASIC_LATIN;
        return content(filter);
    }
}