package com.example.myapp.lesson3;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Creation {
    private static final String TAG = Creation.class.getSimpleName();

    private static final class Producer {
        Observable<String> just() {
            return Observable.just("1", "2", "3");
        }

        Observable<String> fromIterable() {
            return Observable.fromIterable(Arrays.asList("1", "2", "3"));
        }

        Observable interval() {
            return Observable.interval(5, TimeUnit.SECONDS);
        }

        Observable timer() {
            return Observable.timer(5, TimeUnit.SECONDS);
        }

        Observable range() {
            return Observable.range(1, 10);
        }

        boolean randomResult() {
            return Arrays.asList(true, false, true).get(new Random().nextInt(2));
        }

        Observable fromCallable() {
            return Observable.fromCallable(() -> randomResult());
        }

        Observable create() {
            return Observable.create((emitter -> {
                try {
                    for (int i = 0; i < 10; i++) {
                        if (randomResult()) {
                            emitter.onNext("Success");
                            emitter.onComplete();
                        } else {
                            emitter.onError(new RuntimeException("Error!"));
                            break;
                        }
                    }
                } catch (Throwable t) {
                    emitter.onError(new RuntimeException("Error!"));
                }
            }));
        }
    }

    private static final class Consumer {
        Producer producer;

        final Observer<String> stringObserver = new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
                Log.i(TAG, "onSubscribe ");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete ");
            }
        };

        Consumer(Producer producer) {
            this.producer = producer;
        }

        private void execJust() {
            producer.just().subscribe(stringObserver);
        }

        private void execLambda() {
            Disposable disposable = producer.just().subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError " + e.getMessage()),
                    () -> Log.i(TAG, "onComplete "));
        }

        private void execFromIterable() {
            producer.fromIterable().subscribe(stringObserver);
        }

        public void execInterval() {
            Disposable disposable = producer.interval().subscribe((s) -> Log.i(TAG, "onNext " + s));
        }

        public void execTimer() {
            Disposable disposable = producer.timer().subscribe((s) -> Log.i(TAG, "onNext " + s));
        }

        public void execRange() {
            Disposable disposable = producer.range().subscribe((s) -> Log.i(TAG, "onNext " + s));
        }


        public void execCallable() {
            Disposable disposable = producer.fromCallable().subscribe((s) -> Log.i(TAG, "from callable " + s));
        }

        public void execCreate() {
            producer.create().subscribe(
                    (s) -> Log.i(TAG, "onNext " + s),
                    (e) -> Log.i(TAG, "onError"),
                    () -> Log.i(TAG, "onComplete "));
        }

        public void exec() {
            //execJust();
            //execLambda();
            //execFromIterable();
            //execInterval();
            //execTimer();
            //execRange();
            //execCallable();
            execCreate();
        }
    }

    public static void exec() {
        new Consumer(new Producer()).exec();
    }
}