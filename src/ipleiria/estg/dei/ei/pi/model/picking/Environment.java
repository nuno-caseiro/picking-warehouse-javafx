package ipleiria.estg.dei.ei.pi.model.picking;

import com.google.gson.JsonObject;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.pi.utils.exceptions.InvalidNodeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Environment<I extends Individual<? extends GAProblem>> {

    private PickingGraph graph;
    private I bestInRun;
    private HashMap<String, List<Node>> pairsMap;
    private Boolean pauseGA;
    private JsonObject jsonLayout;
    private ArrayList<EnvironmentListener> environmentListeners;

    public Environment() {
        this.graph = new PickingGraph();
        this.environmentListeners = new ArrayList<>();
    }

    public PickingGraph getGraph() {
        return graph;
    }

    public void loadLayout(JsonObject jsonObject) throws InvalidNodeException {
        this.graph.createGraphFromFile(jsonObject);
        fireCreateEnvironment();
    }

    public void loadPicks(JsonObject jsonObject) throws InvalidNodeException {
        this.graph.importPicks(jsonObject);
    }

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!environmentListeners.contains(l)) {
            environmentListeners.add(l);
        }
    }

    public void fireUpdateEnvironment() {
        for (EnvironmentListener listener : environmentListeners) {
            listener.updateEnvironment();
        }
    }

    public void fireCreateEnvironment() {
        for (EnvironmentListener listener : environmentListeners) {
            listener.createEnvironment(graph.getDecisionNodes(),graph.getPicks(),graph.getEdges(),graph.getAgents());
        }
    }

    /*public void fireCreateSimulation() {
        for (EnvironmentListener listener : listeners) {
            listener.createSimulation();
        }
    }*/

    public void fireCreateSimulationPicks() {
        for (EnvironmentListener listener : environmentListeners) {
            listener.createSimulationPicks();
        }
    }
}
