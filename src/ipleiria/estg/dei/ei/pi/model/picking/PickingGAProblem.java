package ipleiria.estg.dei.ei.pi.model.picking;

import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.search.AStarSearch;

import java.util.ArrayList;

public class PickingGAProblem extends GAProblem {

    private final int numberPicks;
    private final int numberAgent;
    private final PickingGraph graph;
    private final AStarSearch<Node> searchMethod;
    private final ArrayList<PickNode> picks;
    private final ArrayList<Node> agents;
    private final Node offloadArea;

    public PickingGAProblem(PickingGraph graph, AStarSearch<Node> searchMethod) {
        this.graph = graph;
        this.searchMethod = searchMethod;
        this.numberPicks = this.graph.getNumberOfPicks();
        this.numberAgent = this.graph.getNumberOfAgents();
        this.picks = this.graph.getPicks();
        this.agents = this.graph.getAgents();
        this.offloadArea = this.graph.getOffloadArea();
    }

    public int getNumberPicks() {
        return numberPicks;
    }

    public int getNumberAgent() {
        return numberAgent;
    }

    public PickingGraph getGraph() {
        return graph;
    }

    public AStarSearch<Node> getSearchMethod() {
        return searchMethod;
    }

    public ArrayList<PickNode> getPicks() {
        return picks;
    }

    public ArrayList<Node> getAgents() {
        return agents;
    }

    public Node getOffloadArea() {
        return offloadArea;
    }
}
