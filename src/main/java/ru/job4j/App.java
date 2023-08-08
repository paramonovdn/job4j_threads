package ru.job4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Текущее состояние
 * Изменения 2
 */
public class App {
    public static void main(String[] args) {
        //List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        //list.stream().parallel().peek(System.out::println).toList();
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        String[] parts = url.split("/");
        System.out.println(parts[0]);
        System.out.println(parts[1]);
        System.out.println(parts[2]);
        System.out.println(parts[3]);
        System.out.println(parts[4]);
        System.out.println(parts[5]);
        System.out.println(parts[parts.length - 1]);



    }
}
