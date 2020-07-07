package ipleiria.estg.dei.ei.pi.model.picking;

import java.util.HashMap;
import java.util.List;

public interface EnvironmentListener {

    void updateEnvironment();

    void createEnvironment(List<Node> decisionNodes, HashMap<Integer,Edge<Node>> edges, List<Node> agents);

    //void createSimulation();

    void createSimulationPicks(List<PickNode> pickNodes);
}
