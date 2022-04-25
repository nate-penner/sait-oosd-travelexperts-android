package oosd.sait.travelexperts.async;

import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;

public class AsyncRunnable<T> {
    private OutputTask<T> outputTask;
    private InputTask<T> inputTask;
    private Activity activity;

    public AsyncRunnable(OutputTask<T> outputTask, InputTask<T> inputTask, Activity activity) {
        this.outputTask = outputTask;
        this.inputTask = inputTask;
        this.activity = activity;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final T result = outputTask.run();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        inputTask.run(result);
                    }
                });
            }
        }).start();
    }
}
