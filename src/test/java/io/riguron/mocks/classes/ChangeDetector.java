package io.riguron.mocks.classes;

public class ChangeDetector {

    private int counter;
    private int previous;

    public void change() {
        this.previous = counter;
        counter++;
    }

    public boolean wasChanged() {
        return counter > previous;
    }

}
