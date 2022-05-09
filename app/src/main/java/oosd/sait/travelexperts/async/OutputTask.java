package oosd.sait.travelexperts.async;

/**
 * An interface for a lambda that executes some code and returns data of type O
 * @author Nate Penner
 * */
@FunctionalInterface
public interface OutputTask<O> {
    O run();
}
