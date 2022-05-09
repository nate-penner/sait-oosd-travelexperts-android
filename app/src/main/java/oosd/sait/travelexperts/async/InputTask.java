package oosd.sait.travelexperts.async;

/**
 * An interface for a lambda that takes input of type I and does something
 * @author Nate Penner
 * */
@FunctionalInterface
public interface InputTask<I> {
    void run(I data);
}
