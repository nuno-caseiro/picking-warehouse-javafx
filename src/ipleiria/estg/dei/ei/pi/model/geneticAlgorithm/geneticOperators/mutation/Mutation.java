package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation;


import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Population;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.GeneticOperator;

public abstract class Mutation<I extends Individual<? extends GAProblem>, P extends GAProblem> extends GeneticOperator {
    
    public Mutation(double probability){
        super(probability);
    }

    public void run(Population<I, P> population) {
        int populationSize = population.getSize();
        for (int i = 0; i < populationSize; i++) {
            if (GeneticAlgorithm.random.nextDouble() < getProbability()) {
                mutate(population.getIndividual(i));
            }
        }
    }

    public abstract void mutate(I individual);
}
