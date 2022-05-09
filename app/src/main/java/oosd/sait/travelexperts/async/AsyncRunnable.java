package oosd.sait.travelexperts.async;

import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;

/**
 * AsyncRunnable class simplifies the process of running a task on another thread, and then updating
 * the UI on the main thread
 * @author Nate Penner
 * */
public class AsyncRunnable<T> {
    private OutputTask<T> outputTask;
    private InputTask<T> inputTask;
    private Activity activity;

    /**
     * Constructor
     * @param outputTask The task to be run on a different thread, like a network task. Returns type T
     * @param inputTask The task to be executed on the UI thread, takes type T output from output task as
     *                  its parameter
     * @param activity The activity to run inputTask on its UI thread
     * */
    public AsyncRunnable(OutputTask<T> outputTask, InputTask<T> inputTask, Activity activity) {
        this.outputTask = outputTask;
        this.inputTask = inputTask;
        this.activity = activity;
    }

    /**
     * Executes the async outputTask on a new thread and passes its output to the inputTask on
     * the UI thread
     * */
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
