package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.pi.utils.exceptions.ValueNotFoundException;

import java.util.Arrays;

public abstract class IntVectorIndividual<P extends GAProblem> extends Individual<P> {

    protected int[] genome;

    public IntVectorIndividual(P problem) {
        super(problem);
    }

    @Override
    public int getNumGenes() {
        return this.genome.length;
    }

    public int getIndexOf(int value) throws ValueNotFoundException {
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value) {
                return i;
            }
        }

        throw new ValueNotFoundException("Could not find " + value + " in " + Arrays.toString(this.genome));
    }

    public int getGene(int index) {
        return genome[index];
    }

    public void setGene(int index, int newValue) {
        genome[index] = newValue;
    }

    public void swapGenes(IntVectorIndividual<P> other, int index) {
        int aux = this.genome[index];
        this.genome[index] = other.genome[index];
        other.genome[index] = aux;
    }
}
