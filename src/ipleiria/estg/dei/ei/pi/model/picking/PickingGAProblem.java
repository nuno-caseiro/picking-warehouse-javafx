package ipleiria.estg.dei.ei.pi.model.picking;

import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.search.AStarSearch;

import java.util.ArrayList;

public class PickingGAProblem extends GAProblem {

    private final int numberPicks;
    private final int numberAgent;
    private final Graph graph;
    private final AStarSearch<Node> searchMethod;
    private final ArrayList<PickNode> picks;
    private final ArrayList<Node> agents;
    private final Node offloadArea;

    public PickingGAProblem(int numberPicks, int numberAgent, Graph graph, AStarSearch<Node> searchMethod, ArrayList<PickNode> picks, ArrayList<Node> agents, Node offloadArea) {
        this.numberPicks = numberPicks;
        this.numberAgent = numberAgent;
        this.graph = graph;
        this.searchMethod = searchMethod;
        this.picks = picks;
        this.agents = agents;
        this.offloadArea = offloadArea;
    }

    public int getNumberPicks() {
        return numberPicks;
    }

    public int getNumberAgent() {
        return numberAgent;
    }

    public Graph getGraph() {
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
