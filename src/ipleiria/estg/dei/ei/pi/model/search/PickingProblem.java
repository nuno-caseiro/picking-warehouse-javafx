package ipleiria.estg.dei.ei.pi.model.search;

import ipleiria.estg.dei.ei.pi.model.Graph;
import ipleiria.estg.dei.ei.pi.model.Node;

import java.util.List;

public class PickingProblem extends Problem<Node> {

    private Graph graph;

    public PickingProblem(Node initialState, Node goalState, Graph graph) {
        super(initialState, goalState);
        this.graph = graph;
    }

    @Override
    public boolean isGoal(int identifier) {
        return this.goalState.getIdentifier() == identifier;
    }

    @Override
    public List<Node> getSuccessors() {
        return this.graph.getSuccessors();
    }
}
