package ipleiria.estg.dei.ei.pi.model.picking;

import ipleiria.estg.dei.ei.pi.utils.EdgeDirection;

import java.util.ArrayList;
import java.util.List;

public class Edge<N extends Node> {

    private List<N> nodes;
    private double length;
    private EdgeDirection edgeDirection;

    public Edge(double distanceOfNodes, EdgeDirection edgeDirection) {
        this.length = distanceOfNodes;
        this.edgeDirection = edgeDirection;
        this.nodes = new ArrayList<>();
    }

    public List<N> getNodes() {
        return nodes;
    }

    public double getLength() {
        return length;
    }

    public EdgeDirection getEdgeDirection() {
        return edgeDirection;
    }

    public void addNode(N node) {
        if (node != null) {
            this.nodes.add(node);
        }
    }
}
