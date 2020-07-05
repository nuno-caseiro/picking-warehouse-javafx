package ipleiria.estg.dei.ei.pi.model.picking;

import ipleiria.estg.dei.ei.pi.utils.PickLocation;

public class PickNode extends Node {

    private PickLocation pickLocation;
    private double wight;
    private double capacity;

    public PickNode(int identifier, double costFromPreviousState, int line, int column, PickLocation pickLocation, double wight, double capacity) {
        super(identifier, costFromPreviousState, line, column);
        this.pickLocation = pickLocation;
        this.wight = wight;
        this.capacity = capacity;
    }

    public PickLocation getPickLocation() {
        return pickLocation;
    }

    public double getWight() {
        return wight;
    }

    public double getCapacity() {
        return capacity;
    }
}
