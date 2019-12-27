package com.mhussain.studium.reactive;

import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.*;

public class OperatorTest {

    @Test
    public void testFilter() {
        var observable = just("1", "2", "3", "4").filter(s -> s.equals("2") || s.equals("4"));
        observable.subscribe(System.out::println, System.err::println, () -> System.out.println("Done!"));
    }

    @Test
    public void testCount() {
        just("1", "2", "3", "4")
                .count()
                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done!"));
    }

    @Test
    public void testCountWithEndlessStream() throws InterruptedException {
        interval(1, MILLISECONDS)
                .count()
                .subscribe(System.out::println, System.err::println, () -> System.out.println("Done!"));

        Thread.sleep(5000);
    }

    @Test
    public void testZip() {
        zip(
                just("A", "B", "C"),
                just("1", "2", "3"),
                (x, y) -> x + y)
                .forEach(System.out::println);
    }

}