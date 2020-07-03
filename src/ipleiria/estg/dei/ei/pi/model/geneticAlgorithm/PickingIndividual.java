package ipleiria.estg.dei.ei.pi.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.pi.model.Node;
import ipleiria.estg.dei.ei.pi.model.PickNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PickingIndividual extends IntVectorIndividual<PickingGAProblem> {

    private List<PickingAgentPath> paths;
    private double time;

    public PickingIndividual(PickingGAProblem problem) {
        super(problem);

        int genomeSize = problem.getNumberPicks() + (problem.getNumberAgent() - 1);
        this.genome = new int[genomeSize];

        List<Integer> genes = new LinkedList<>(); // n < 0 -> divisions between paths for different agents | 1 ... x index of the picks in the picks list
        for (int i = 0; i < problem.getNumberAgent() - 1; i++) {
            genes.add(-1 - i);
        }

        for (int i = 0; i < problem.getNumberPicks(); i++) {
            genes.add(i + 1);
        }

        int randomIndex;
        for (int i = 0; i < genomeSize; i++) {
            randomIndex = GeneticAlgorithm.random.nextInt(genes.size());
            this.genome[i] = genes.get(randomIndex);
            genes.remove(randomIndex);
        }
    }

    @Override
    public void computeFitness() {
        this.paths = new ArrayList<>();
//        this.numberTimesOffload = 0;

        List<PickNode> picks = this.problem.getPicks();
        List<Node> agents = this.problem.getAgents();
        Node offloadArea = this.problem.getOffloadArea();

//        int weightOnTopOfRestrictionPick;
//        int restrictionPickCapacity;

        PickingAgentPath pickingAgentPath;
        int i = 0;
        for (Node agent : agents) {
            pickingAgentPath = new PickingAgentPath();
            pickingAgentPath.addAgentInitialPosition(agent);

            if (i >= this.genome.length || this.genome[i] < 0) { // WHEN THE FIRST ELEMENT OF THE GENOME IS NEGATIVE OR THERE ARE 2 CONSECUTIVE NEGATIVE ELEMENTS IN THE GENOME
//                computePath(agentPath, agent, this.environment.getNode(offloadArea));
                this.paths.add(pickingAgentPath);
                i++;
                continue;
            }

//            computePath(agentPath, agent, picks.get(this.genome[i] - 1));

//            weightOnTopOfRestrictionPick = 0;
//            restrictionPickCapacity = picks.get(this.genome[i] - 1).getCapacity();

            while (i < (this.genome.length - 1) && this.genome[i + 1] > 0) {

//                if ((weightOnTopOfRestrictionPick + picks.get(this.genome[i + 1] - 1).getWeight()) > restrictionPickCapacity) {
//                    this.numberTimesOffload++;
//
//                    computePath(agentPath, picks.get(this.genome[i] - 1), this.environment.getNode(offloadArea));
//                    computePath(agentPath, this.environment.getNode(offloadArea), picks.get(this.genome[i + 1] - 1));
//
//                    weightOnTopOfRestrictionPick = 0;
//                    restrictionPickCapacity = picks.get(this.genome[i + 1] - 1).getCapacity();
//                } else {
//                    weightOnTopOfRestrictionPick += picks.get(this.genome[i + 1] - 1).getWeight();
//
//                    if ((restrictionPickCapacity - weightOnTopOfRestrictionPick) > picks.get(this.genome[i + 1] - 1).getCapacity()) {
//                        restrictionPickCapacity = picks.get(this.genome[i + 1] - 1).getCapacity();
//                        weightOnTopOfRestrictionPick = 0;
//                    }
//
//                    computePath(agentPath, picks.get(this.genome[i] - 1), picks.get(this.genome[i + 1] - 1));
//                }

//                computePath(agentPath, picks.get(this.genome[i] - 1), picks.get(this.genome[i + 1] - 1));

                i++;
            }

//            computePath(agentPath, picks.get(this.genome[i] - 1), offloadArea);
            this.paths.add(pickingAgentPath);
            i = i + 2;
        }

        this.fitness = 0;
        for (PickingAgentPath path : this.paths) {
            if (this.fitness < path.getValue()) {
                this.fitness = path.getValue();
            }
        }
        this.time = this.fitness;


//        detectAndPenalizeCollisions();
    }

    public static class PickingIndividualFactory implements Factory<PickingIndividual, PickingGAProblem> {

        @Override
        public PickingIndividual newIndividual(PickingGAProblem problem) {
            return new PickingIndividual(problem);
        }
    }
}
