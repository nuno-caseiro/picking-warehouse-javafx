package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation;


import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.IntVectorIndividual;
import ipleiria.estg.dei.ei.pi.utils.exceptions.ValueNotFoundException;

import java.util.LinkedList;
import java.util.Random;

public class MutationScramble<I extends IntVectorIndividual<? extends GAProblem>, P extends GAProblem> extends Mutation<I, P> {

    public MutationScramble(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I individual, Random random) {
        int cut1 = random.nextInt(individual.getNumGenes());
        int cut2;

        do {
            cut2 = random.nextInt(individual.getNumGenes());
        } while (cut1 == cut2);

        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i <= (cut2 - cut1); i++) {
            list.add(individual.getGene(cut1 + i));
        }

        int num1;
        int num2;
        for(int i = 0; i < list.size(); i++){
            do{
                num1 = random.nextInt(individual.getNumGenes()) + 1;
            } while(!list.contains(num1));

            do{
                num2 = random.nextInt(individual.getNumGenes()) + 1;
            } while(!list.contains(num2) || num2 == num1);

            try {
                int auxNum2 = individual.getIndexOf(num2);
                individual.setGene(individual.getIndexOf(num1), num2);
                individual.setGene(auxNum2, num1);
            } catch (ValueNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return "Scramble";
    }
}