package ipleiria.estg.dei.ei.pi.model.picking;

import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.search.AStarSearch;
import ipleiria.estg.dei.ei.pi.utils.WeightLimitation;

import java.util.ArrayList;

public class PickingGAProblem extends GAProblem {

    private final int numberPicks;
    private final int numberAgent;
    private final PickingGraph graph;
    private final AStarSearch<Node> searchMethod;
    private final ArrayList<PickingPick> picks;
    private final ArrayList<PickingAgent> agents;
    private final Node offloadArea;
    private final WeightLimitation weightLimitation;
    // Collision handling

    public PickingGAProblem(PickingGraph graph, AStarSearch<Node> searchMethod, WeightLimitation weightLimitation) {
        this.graph = graph;
        this.searchMethod = searchMethod;
        this.weightLimitation = weightLimitation;
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

    public ArrayList<PickingPick> getPicks() {
        return picks;
    }

    public ArrayList<PickingAgent> getAgents() {
        return agents;
    }

    public Node getOffloadArea() {
        return offloadArea;
    }

    public WeightLimitation getWeightLimitation() {
        return weightLimitation;
    }
}
