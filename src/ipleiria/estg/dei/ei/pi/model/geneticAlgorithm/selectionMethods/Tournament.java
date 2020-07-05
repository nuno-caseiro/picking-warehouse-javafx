package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.selectionMethods;


import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Population;

public class Tournament<I extends Individual<? extends GAProblem>, P extends GAProblem> extends SelectionMethod<I, P> {

    private int size;

    public Tournament(int populationSize) {
        this(populationSize, 2);
    }

    public Tournament(int populationSize, int size) {
        super(populationSize);
        this.size = size;
    }

    @Override
    public Population<I, P> run(Population<I, P> original) {
        Population<I, P> result = new Population<>(original.getSize());        

        for (int i = 0; i < this.populationSize; i++) {
            result.addIndividual(tournament(original));
        }
        return result;
    }

    private I tournament(Population<I, P> population) {
        I best = population.getIndividual(GeneticAlgorithm.random.nextInt(this.populationSize));

        for (int i = 1; i < this.size; i++) {
            I aux = population.getIndividual(GeneticAlgorithm.random.nextInt(this.populationSize));
            if (aux.compareTo(best) > 0) { //if aux is BETTER than best
                best = aux;
            }
        }
        return (I) best.clone();
    }
    
    @Override
    public String toString(){
        return "Tournament(" + this.size + ")";
    }    
}