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
        try (InputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[512];
            int count;
            while ((count = in.read(buffer)) != -1) {
                for (int i = 0; i < count; i++) {
                    if (filter.test((char) buffer[i])) {
                        output.append((char) buffer[i]);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }
}