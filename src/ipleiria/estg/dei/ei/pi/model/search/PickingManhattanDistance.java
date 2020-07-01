package ipleiria.estg.dei.ei.pi.model.search;

import ipleiria.estg.dei.ei.pi.model.Node;

public class PickingManhattanDistance extends Heuristic<Node> {

    @Override
    public double compute(Node state, Node goalState) {
        return Math.abs(goalState.getLine() - state.getLine()) + Math.abs(goalState.getColumn() - state.getColumn());
    }
}
