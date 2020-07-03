package ipleiria.estg.dei.ei.pi.model;

import ipleiria.estg.dei.ei.pi.utils.PickLocation;

public class PathNode extends Node {

    private double time;

    public PathNode(int identifier, int line, int column, double time) {
        super(identifier, 0, line, column);
        this.time = time;
    }

    public double getTime() {
        return time;
    }
}
