package com.mhussain.studium.reactive;

import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static rx.Observable.from;
import static rx.Observable.just;

public class ObservableTest {

    @Test
    public void testCreate() {
        var observale = Observable.create(sub -> {
                    sub.onNext("Hello, reactive world!");
                    sub.onCompleted();
                }
        );
        observale.subscribe(System.out::println, System.err::println, () -> System.out.println("Done!"));
    }

    @Test
    public void testJust() {
        var observable = Observable.just("1", "2", "3", "4");
        observable.subscribe(System.out::println, System.err::println, () -> System.out.println("Done!"));
    }

    @Test
    public void testConcat() {
        var future = Executors.newCachedThreadPool().submit(() -> "7");
        var observable = Observable.concat(just("1", "2", "3"), from(asList("4", "5", "6")), from(future));
        observable.subscribe(System.out::println, System.err::println, () -> System.out.println("Done!"));
    }

    @Test
    public void testInterval() throws InterruptedException {
        var observable = Observable.interval(1, TimeUnit.SECONDS);
        observable.subscribe(e -> System.out.println("Subscriber-1: " + e));

        Thread.sleep(3000);

        observable.subscribe(e -> System.out.println("Subscriber-2: " + e));
        Thread.sleep(5000);
    }

    @Test
    public void testUnsubscribe() throws InterruptedException {
        CountDownLatch externalSignal = new CountDownLatch(7);
        var subscription = Observable.interval(100, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            System.out.println(aLong);
            externalSignal.countDown();
        });

        externalSignal.await();
        subscription.unsubscribe();
        Thread.sleep(1000);
    }

}