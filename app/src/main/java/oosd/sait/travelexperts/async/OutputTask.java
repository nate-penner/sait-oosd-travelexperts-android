package oosd.sait.travelexperts.async;

@FunctionalInterface
public interface OutputTask<O> {
    O run();
}
