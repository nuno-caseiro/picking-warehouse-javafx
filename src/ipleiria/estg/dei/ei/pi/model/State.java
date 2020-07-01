package ipleiria.estg.dei.ei.pi.model;

public abstract class State {

    protected int identifier;
    protected double costFromPreviousState;

    public State(int identifier, double costFromPreviousState) {
        this.identifier = identifier;
        this.costFromPreviousState = costFromPreviousState;
    }

    public int getIdentifier() {
        return identifier;
    }

    public double getCostFromPreviousState() {
        return costFromPreviousState;
    }
}
