package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }


    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        String[] urlParts = url.split("/");
        var file = new File(urlParts[3] + " " + urlParts[urlParts.length - 1]);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            int downloadBytes = 0;
            double downloadAt = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                downloadBytes += bytesRead;
                if (downloadBytes > speed) {
                    double downloadTimeInMs = (System.nanoTime() - downloadAt) / 1000000;
                    if (downloadTimeInMs < 1000) {
                        double pauseInMillis = 1000 - downloadTimeInMs;
                        Thread.sleep((long) pauseInMillis);
                        System.out.println("Download " + downloadBytes + " bytes: " + "download time- "
                                + downloadTimeInMs + " ms, " + "download speed limit- " + speed + " B/s, "
                                + "pause- " + pauseInMillis + " ms.");
                        downloadBytes = 0;
                    }
                }
                downloadAt = System.nanoTime();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Total file size- " + Files.size(file.toPath()) + " bytes.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void argsValid(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(String.format("Invalid number of arguments- %s. "
                    + "Expected quantity  - 2", args.length));
        }

        try {
            new URL(args[0]).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException(String.format("Wrong URL- %s. ", args[0]));
        }

        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Wrong argument- %s. ", args[1]));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        argsValid(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}