package com.mhussain.studium.reactive;

import org.junit.jupiter.api.Test;
import rx.Observable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.interval;
import static rx.Observable.just;

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
        Observable.zip(Observable.just("A", "B", "C"),
                Observable.just("1", "2", "3"),
                (x, y) -> x + y)
                .forEach(System.out::println);
    }

}