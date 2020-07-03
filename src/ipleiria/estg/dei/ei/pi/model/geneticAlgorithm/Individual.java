package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm;

public abstract class Individual<P extends GAProblem> implements Comparable<Individual<? extends GAProblem>> {

    protected double fitness;
    protected P problem;

    public Individual(P problem) {
        this.problem = problem;
    }

    public double getFitness() {
        return fitness;
    }

    public abstract int getNumGenes();

    public abstract void computeFitness();

//    public abstract void swapGenes(Individual<P> other, int g);

    /** returns 1 if this is better than individual (this.fitness < individual.getFitness()), returns -1 if this is worst than individual (this.fitness > individual.getFitness()), else returns 0 */
    @Override
    public int compareTo(Individual<? extends GAProblem> individual) {
        return Double.compare(individual.getFitness(), this.fitness);
    }
}
