package ru.job4j.io;

import java.io.*;

public final class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] buffer = content.getBytes();
            out.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
