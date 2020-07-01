package ipleiria.estg.dei.ei.pi.model.search;

import ipleiria.estg.dei.ei.pi.model.State;

public abstract class Heuristic<S extends State> {

    public abstract double compute(S state, S goalState);
}
