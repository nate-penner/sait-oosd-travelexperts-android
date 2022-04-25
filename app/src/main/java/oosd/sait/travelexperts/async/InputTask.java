package oosd.sait.travelexperts.async;

@FunctionalInterface
public interface InputTask<I> {
    void run(I data);
}
