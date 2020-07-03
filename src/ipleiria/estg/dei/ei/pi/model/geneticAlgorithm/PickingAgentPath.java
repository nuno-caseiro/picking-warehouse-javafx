package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.pi.model.Node;
import ipleiria.estg.dei.ei.pi.model.PathNode;
import ipleiria.estg.dei.ei.pi.model.search.SearchNode;

import java.util.ArrayList;
import java.util.List;

public class PickingAgentPath {

    private List<Node> path;
    private double value;

    public PickingAgentPath() {
        this.path = new ArrayList<>();
        this.value = 0;
    }

    public List<Node> getPath() {
        return this.path;
    }

    public double getValue() {
        return this.value;
    }

    public void addPath(List<SearchNode<Node>> path) {

        for (SearchNode<Node> searchNode : path) {
            this.path.add(new PathNode(searchNode.getState().getIdentifier(), searchNode.getState().getLine(), searchNode.getState().getColumn(), this.value + searchNode.getCost()));
        }

        this.value += path.get(path.size() - 1).getCost();
    }

    public void addAgentInitialPosition(Node node) {
        this.path.add(new PathNode(node.getIdentifier(), node.getLine(), node.getColumn(), 0));
    }
}
