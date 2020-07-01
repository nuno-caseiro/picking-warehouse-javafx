package ipleiria.estg.dei.ei.pi.model.search;

import ipleiria.estg.dei.ei.pi.model.State;

public class SearchNode implements Comparable<SearchNode> {

    private SearchNode parent;
    private double f; // g + h
    private double cost; // cost of the path from the start node to this node
    private State state; // graph node

    public SearchNode(State state) {
        this(null, 0, 0, state);
    }

    public SearchNode(SearchNode parent, double f, double cost, State state) {
        this.parent = parent;
        this.f = f;
        this.cost = cost;
        this.state = state;
    }

    public SearchNode getParent() {
        return parent;
    }

    public double getF() {
        return f;
    }

    public double getCost() {
        return cost;
    }

    public State getState() {
        return state;
    }

    @Override
    public int compareTo(SearchNode searchNode) {
        return (this.f < searchNode.f) ? -1 : (f == searchNode.f) ? 0 : 1;
    }
}
