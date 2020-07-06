package ipleiria.estg.dei.ei.pi.model.picking;

import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.*;
import ipleiria.estg.dei.ei.pi.utils.exceptions.NoSolutionFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PickingIndividual extends IntVectorIndividual<PickingGAProblem> {

    private List<PickingAgentPath> paths;
    private double time;
    private int numberTimesOffload;

    private double weightOnTopOfRestrictionPick;
    private double restrictionPickCapacity;
    private double weightOnAgent;

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

    public PickingIndividual(PickingIndividual pickingIndividual) {
        super(pickingIndividual);
        this.paths = pickingIndividual.paths;
        this.time = pickingIndividual.time;
    }

    public List<PickingAgentPath> getPaths() {
        return paths;
    }

    public double getTime() {
        return time;
    }

    @Override
    public void computeFitness() {
        this.paths = new ArrayList<>();
        this.numberTimesOffload = 0;

        List<PickingPick> picks = this.problem.getPicks();
        List<PickingAgent> agents = this.problem.getAgents();
        Node offloadArea = this.problem.getOffloadArea();

        PickingAgentPath pickingAgentPath;
        int i = 0;
        for (PickingAgent agent : agents) {
            pickingAgentPath = new PickingAgentPath();
            pickingAgentPath.addAgentInitialPosition(agent);

            if (i >= this.genome.length || this.genome[i] < 0) { // WHEN THE FIRST ELEMENT OF THE GENOME IS NEGATIVE OR THERE ARE 2 CONSECUTIVE NEGATIVE ELEMENTS IN THE GENOME
                computePath(pickingAgentPath, agent, offloadArea);
                this.paths.add(pickingAgentPath);
                i++;
                continue;
            }

            computePath(pickingAgentPath, agent, picks.get(this.genome[i] - 1));

            this.weightOnTopOfRestrictionPick = 0;
            this.weightOnAgent = picks.get(this.genome[i] - 1).getWeight();
            this.restrictionPickCapacity = picks.get(this.genome[i] - 1).getCapacity();

            while (i < (this.genome.length - 1) && this.genome[i + 1] > 0) {
                this.weightOnAgent += picks.get(this.genome[i + 1] - 1).getWeight();
                handleWeightRestrictions(pickingAgentPath, picks.get(this.genome[i] - 1), picks.get(this.genome[i + 1] - 1), offloadArea, agent);
                i++;
            }

            computePath(pickingAgentPath, picks.get(this.genome[i] - 1), offloadArea);
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

    private void handleWeightRestrictions(PickingAgentPath agentPath, PickingPick previousPick, PickingPick nextPick, Node offloadArea, PickingAgent agent) {
        switch (this.problem.getWeightLimitation()) {
            case Picks:
                pickCapacity(agentPath, previousPick, nextPick, offloadArea);
                break;
            case Agents:
                agentCapacity(agentPath, previousPick, nextPick, offloadArea, agent);
                break;
            case Both:
                agentCapacity(agentPath, previousPick, nextPick, offloadArea, agent);
                pickCapacity(agentPath, previousPick, nextPick, offloadArea);
                break;
            default:
                computePath(agentPath, previousPick, nextPick);
                break;
        }
    }

    private void pickCapacity(PickingAgentPath agentPath, PickingPick previousPick, PickingPick nextPick, Node offloadArea) {
        if ((this.weightOnTopOfRestrictionPick + nextPick.getWeight()) > this.restrictionPickCapacity) {
            this.numberTimesOffload++;
            computePath(agentPath, previousPick, offloadArea);
            computePath(agentPath, offloadArea, nextPick);

            this.weightOnAgent = nextPick.getWeight();
            this.weightOnTopOfRestrictionPick = 0;
            this.restrictionPickCapacity = nextPick.getCapacity();
        } else {
            this.weightOnTopOfRestrictionPick += nextPick.getWeight();

            if ((this.restrictionPickCapacity - this.weightOnTopOfRestrictionPick) > nextPick.getCapacity()) {
                this.restrictionPickCapacity = nextPick.getCapacity();
                this.weightOnTopOfRestrictionPick = 0;
            }

            computePath(agentPath, previousPick, nextPick);
        }
    }

    private void agentCapacity(PickingAgentPath agentPath, PickingPick previousPick, PickingPick nextPick, Node offloadArea, PickingAgent agent) {
        if (this.weightOnAgent > agent.getCapacity()) {
            this.numberTimesOffload++;
            computePath(agentPath, previousPick, offloadArea);
            computePath(agentPath, offloadArea, nextPick);

            this.weightOnAgent = nextPick.getWeight();
            this.weightOnTopOfRestrictionPick = 0;
            this.restrictionPickCapacity = nextPick.getCapacity();
        }
    }

    public void computePath(PickingAgentPath agentPath, Node initialNode, Node goalNode) {
        try {
            agentPath.addPath(this.problem.getSearchMethod().graphSearch(new PickingSearchProblem(initialNode, goalNode, this.problem.getGraph())), goalNode);
        } catch (NoSolutionFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /** returns 1 if this is better than individual (this.fitness < individual.getFitness()), returns -1 if this is worst than individual (this.fitness > individual.getFitness()), else returns 0 */
    @Override
    public int compareTo(Individual<? extends GAProblem> individual) {
        return Double.compare(individual.getFitness(), this.fitness);
    }

    @Override
    public PickingIndividual clone() {
        return new PickingIndividual(this);
    }

    public static class PickingIndividualFactory implements Factory<PickingIndividual, PickingGAProblem> {
        @Override
        public PickingIndividual newIndividual(PickingGAProblem problem) {
            return new PickingIndividual(problem);
        }
    }
}
