package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm;

import java.util.Random;

public class GeneticAlgorithm<I extends Individual<? extends GAProblem>, P extends GAProblem> {

    public static Random random;
    private Population<I, P> population;
//    private final SelectionMethod<I, P> selection;
//    private final Recombination<I, P> recombination;
//    private final Mutation<I, P> mutation;
    private final int populationSize;
    private final int maxGenerations;
    private int t;
    private boolean stopped;
    private I bestInRun;


}
