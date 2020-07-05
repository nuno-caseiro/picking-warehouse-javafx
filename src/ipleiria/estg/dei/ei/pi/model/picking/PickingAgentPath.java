package ipleiria.estg.dei.ei.pi.model.picking;

import ipleiria.estg.dei.ei.pi.model.search.SearchNode;
import ipleiria.estg.dei.ei.pi.utils.PickLocation;

import java.util.ArrayList;
import java.util.List;

public class PickingAgentPath {

    private List<PathNode> path;
    private double value;

    public PickingAgentPath() {
        this.path = new ArrayList<>();
        this.value = 0;
    }

    public List<PathNode> getPath() {
        return path;
    }

    public double getValue() {
        return this.value;
    }

    public void addPath(List<SearchNode<Node>> path, Node node) {

        for (SearchNode<Node> searchNode : path) {
            this.path.add(new PathNode(searchNode.getState().getIdentifier(), searchNode.getState().getLine(), searchNode.getState().getColumn(), this.value + searchNode.getCost(), PickLocation.NONE));
        }

        if (node instanceof PickNode) { // TODO test
            this.path.get(this.path.size() - 1).setPickLocation(((PickNode) node).getPickLocation());
        }

        this.value += path.get(path.size() - 1).getCost();
    }

    public void addAgentInitialPosition(Node node) {
        this.path.add(new PathNode(node.getIdentifier(), node.getLine(), node.getColumn(), 0, PickLocation.NONE));
    }
}
