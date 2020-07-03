package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm;

public class PickingGAProblem extends GAProblem {

    private final int numberPicks;
    private final int numberAgent;

    public PickingGAProblem(int numberPicks, int numberAgent) {
        this.numberPicks = numberPicks;
        this.numberAgent = numberAgent;
    }

    public int getNumberPicks() {
        return numberPicks;
    }

    public int getNumberAgent() {
        return numberAgent;
    }
}
