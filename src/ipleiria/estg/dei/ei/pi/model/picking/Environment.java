package ipleiria.estg.dei.ei.pi.model.picking;

import com.google.gson.JsonObject;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.pi.utils.exceptions.InvalidNodeException;

import java.util.HashMap;
import java.util.List;

public class Environment<I extends Individual<? extends GAProblem>> {

    private PickingGraph graph;
    private I bestInRun;
    private HashMap<String, List<Node>> pairsMap;
    private Boolean pauseGA;
    private JsonObject jsonLayout;

    public Environment() {
        this.graph = new PickingGraph();
    }

    public PickingGraph getGraph() {
        return graph;
    }

    public void loadLayout(JsonObject jsonObject) throws InvalidNodeException {
        this.graph.createGraphFromFile(jsonObject);
    }

    public void loadPicks(JsonObject jsonObject) throws InvalidNodeException {
        this.graph.importPicks(jsonObject);
    }
}
