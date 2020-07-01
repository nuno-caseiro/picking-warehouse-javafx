package ipleiria.estg.dei.ei.pi.model.search;

import ipleiria.estg.dei.ei.pi.model.State;

public abstract class InformedSearch<S extends State> extends GraphSearch<NodePriorityQueue, S>{

    protected Heuristic<S> heuristic;

    public InformedSearch(Heuristic<S> heuristic) {
        super(new NodePriorityQueue());
        this.heuristic = heuristic;
    }
}
