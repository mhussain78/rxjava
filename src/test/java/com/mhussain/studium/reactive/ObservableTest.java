package com.mhussain.studium.reactive;

import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.functions.Action1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static rx.Observable.*;

public class ObservableTest {

    @Test
    public void testCreate() {
        var observale = Observable.create((
                        Observable.OnSubscribe<String>) sub -> {
                    sub.onNext("Hello, reactive world!");
                    sub.onCompleted();
                }
        );
        observale.subscribe(System.out::println, System.err::println, () -> System.out.println("Done!"));
    }

    @Test
    public void testJust() {
        var observable = just("1", "2", "3", "4");
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
        interval(1, TimeUnit.SECONDS).subscribe(e -> System.out.println("Received: " + e));
        Thread.sleep(5000);
    }

    @Test
    public void testUnsubscribe() throws InterruptedException {
        CountDownLatch externalSignal = new CountDownLatch(7);
        var subscription = interval(100, MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                System.out.println(aLong);
                externalSignal.countDown();
            }
        });

        externalSignal.await();
        subscription.unsubscribe();
        Thread.sleep(1000);
    }

}