package com.example.myapp.lesson3;

import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Function4;

public class Operators {
    private static final String TAG = Creation.class.getSimpleName();

    private static final class Producer {
        Observable<String> just() {
            return Observable.just("1", "2", "3", "3");
        }

        Observable<String> just2() {
            return Observable.just("4", "5", "6");
        }
    }

    private static final class Consumer {
        Producer producer;

        Consumer(Producer producer) {
            this.producer = producer;
        }

        public void exec() {
            //execTake();
            //execSkip();
            //execMap();
            //execDistinct();
            //execFilter();
            //execMerge();
            //execFlatMap();
            execZip();
        }

        public void execTake() {
            producer.just().take(2).subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete"));
        }

        public void execSkip() {
            producer.just().skip(2).subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete"));
        }


        public void execDistinct() {
            producer.just().distinct().subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete"));
        }

        public void execFilter() {
            producer.just().filter(s -> Integer.valueOf(s) > 1).subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete"));
        }

        public void execMerge() {
            producer.just().mergeWith(producer.just2()).subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete"));
        }

        public void execMap() {
            producer.just().map(new Function<String, String>() {
                @Override
                public String apply(String s) throws Throwable {
                    return s + s;
                }
            }).subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete"));
        }

        public void execFlatMap() {
            producer.just().flatMap(new Function<String, ObservableSource<? extends String>>() {
                @Override
                public ObservableSource<? extends String> apply(String s) throws Throwable {
                    int random = new Random().nextInt(10);
                    return Observable.just(s + random).delay(random, TimeUnit.MILLISECONDS);
                }
            }).subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete"));
        }

        public void execZip() {
            Observable observable1 = Observable.just("1", "7").delay(1, TimeUnit.SECONDS);
            Observable observable2 = Observable.just("2", "9").delay(2, TimeUnit.SECONDS);
            Observable observable3 = Observable.just("3", "10").delay(3, TimeUnit.SECONDS);
            Observable observable4 = Observable.just("4", "11").delay(4, TimeUnit.SECONDS);

            Observable.zip(observable1, observable2, observable3, observable4,
                    new Function4<String, String, String, String, List<String>>() {
                        @Override
                        public List<String> apply(String o, String o2, String o3, String o4) throws Throwable {
                            return Arrays.asList(o, o2, o3, o4);
                        }
                    })

                    .subscribe(
                            (s) -> Log.i(TAG, "onNext Zip result " + s),
                            (e) -> Log.i(TAG, "onError"),
                            () -> Log.i(TAG, "onComplete"));
        }
    }

    public static void exec() {
        new Consumer(new Producer()).exec();
    }

    // Изучить switchMap
}