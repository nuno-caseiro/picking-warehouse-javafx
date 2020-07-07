package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.recombination;


import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Population;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.GeneticOperator;
import ipleiria.estg.dei.ei.pi.utils.exceptions.ValueNotFoundException;

public abstract class Recombination<I extends Individual<? extends GAProblem>, P extends GAProblem> extends GeneticOperator {

    public Recombination(double probability) {
        super(probability);
    }

    public void run(Population<I, P> population) throws ValueNotFoundException {
        int populationSize = population.getSize();
        for (int i = 0; i < populationSize; i += 2) {
            if (GeneticAlgorithm.random.nextDouble() < getProbability()) {
                recombine(population.getIndividual(i), population.getIndividual(i + 1));
            }
        }
    }

    public abstract void recombine(I ind1, I ind2) throws ValueNotFoundException;
}