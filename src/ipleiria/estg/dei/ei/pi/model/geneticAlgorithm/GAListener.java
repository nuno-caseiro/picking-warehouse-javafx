package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm;

public interface GAListener<I extends Individual<? extends GAProblem>, P extends GAProblem> {

    void generationEnded(GeneticAlgorithm<I, P> geneticAlgorithm);

    void runEnded(GeneticAlgorithm<I, P> geneticAlgorithm);
}
