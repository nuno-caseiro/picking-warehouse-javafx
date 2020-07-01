package ipleiria.estg.dei.ei.pi.model.search;

import ipleiria.estg.dei.ei.pi.model.State;

import java.util.List;

public abstract class Problem<S extends State> {

    protected S initialState;
    protected S goalState;

    public Problem(S initialState, S goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
    }

    public S getInitialState() {
        return initialState;
    }

    public S getGoalState() {
        return goalState;
    }

    public abstract boolean isGoal(int identifier);

    public abstract List<S> getSuccessors();
}
