package ipleiria.estg.dei.ei.pi.model.picking;

import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.*;
import ipleiria.estg.dei.ei.pi.model.search.SearchNode;
import ipleiria.estg.dei.ei.pi.utils.CollisionsHandling;
import ipleiria.estg.dei.ei.pi.utils.EdgeDirection;
import ipleiria.estg.dei.ei.pi.utils.exceptions.NoSolutionFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PickingIndividual extends IntVectorIndividual<PickingGAProblem> {

    private List<PickingAgentPath> paths;
    private double time;
    private int numberTimesOffload;
    private int numberOfCollisions;

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
        this.numberTimesOffload = pickingIndividual.numberTimesOffload;
        this.numberOfCollisions = pickingIndividual.numberOfCollisions;
    }

    public List<PickingAgentPath> getPaths() {
        return paths;
    }

    public double getTime() {
        return time;
    }

    public int getNumberOfCollisions() {
        return numberOfCollisions;
    }

    public int getNumberTimesOffload() {
        return numberTimesOffload;
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


        detectAndPenalizeCollisions();
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
                picksAndAgentCapacity(agentPath, previousPick, nextPick, offloadArea, agent);
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
        } else {
            computePath(agentPath, previousPick, nextPick);
        }
    }

    private void picksAndAgentCapacity(PickingAgentPath agentPath, PickingPick previousPick, PickingPick nextPick, Node offloadArea, PickingAgent agent) {
        if ((this.weightOnTopOfRestrictionPick + nextPick.getWeight()) > this.restrictionPickCapacity || this.weightOnAgent > agent.getCapacity()) {
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

    public void computePath(PickingAgentPath agentPath, Node initialNode, Node goalNode) {
        try {
            if (this.problem.getPairs().containsKey(initialNode.getIdentifier() + "-" + goalNode.getIdentifier())) {
                agentPath.addPath(this.problem.getPairs().get(initialNode.getIdentifier() + "-" + goalNode.getIdentifier()), goalNode);
            } else {
                List<SearchNode<Node>> l = this.problem.getSearchMethod().graphSearch(new PickingSearchProblem(initialNode, goalNode, this.problem.getGraph()));
                l.remove(0);

                agentPath.addPath(l, goalNode);

                this.problem.getPairs().put(initialNode.getIdentifier() + "-" + goalNode.getIdentifier(), l);
            }
        } catch (NoSolutionFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void detectAndPenalizeCollisions() {
        this.numberOfCollisions = 0;

        for (PickingAgentPath agentPath : this.paths) {
            agentPath.populateNodePairsMap();
        }

        // NODE COLLISIONS
        for (int i = 0; i < this.paths.size() - 1; i++) {
            for (int j = i + 1; j < this.paths.size(); j++) {
                for (PathNode node : this.paths.get(i).getPath()) {
                    if (this.paths.get(j).getPath().containsNodeAtTime(node.getIdentifier(), node.getTime())) {
                        this.numberOfCollisions++;
                        handleNodeCollisions(node);
                    }
                }
            }
        }

        // AISLE COLLISIONS
        List<TimePair> pairs;
        for (int i = 0; i < this.paths.size() - 1; i++) {
            NodePathList path = this.paths.get(i).getPath();
            for (int j = i + 1; j < this.paths.size(); j++) {
                NodePathList path1 = this.paths.get(j).getPath();
                for (int k = 0; k < path.size() - 1; k++) {
                    if (isEdgeOneWay(path.get(k).getIdentifier(), path.get(k + 1).getIdentifier())) {
                        pairs = path1.getPair(path.get(k + 1), path.get(k));
                        if (pairs != null) {
                            for (TimePair timePair : pairs) {
                                if (rangesOverlap(path.get(k).getTime(), path.get(k + 1).getTime(), timePair.getNode1Time(), timePair.getNode2Time())) {
                                    this.numberOfCollisions++;
                                    handleAisleCollision(path.get(k), path.get(k + 1), timePair.getNode1Time());
                                }
                            }
                        }
                    }
                }
            }
        }

        if (this.problem.getCollisionsHandling() == CollisionsHandling.Type1) {
//            this.fitness = (this.fitness * this.environment.getTimeWeight()) + (this.numberOfCollisions * this.environment.getCollisionsWeight());
        }
    }

    private void handleNodeCollisions(PathNode node) {
        switch (this.problem.getCollisionsHandling()) {
            case Type2:
                this.fitness += handleType2NodeCollision(node);
                break;
            case Type3:
//                handleType3NodeCollision(node);
                break;
        }
    }

    private double handleType2NodeCollision(PathNode node) {
        if (this.problem.getGraph().getDecisionNodesMap().containsKey(node.getIdentifier())) {
            return 1;
        } else {
            Edge<Node> e = this.problem.getGraph().getAisleNodeEdge().get(node.getIdentifier());

            int distanceToNode1 = Math.abs(node.getLine() - e.getNode1().getLine()) + Math.abs(node.getColumn() - e.getNode1().getColumn());
            int distanceToNode2 = Math.abs(node.getLine() - e.getNode2().getLine()) + Math.abs(node.getColumn() - e.getNode2().getColumn());

            return Math.min(distanceToNode1, distanceToNode2) + 1;
        }
    }

//    private double handleType3NodeCollision(PathNode node){
//
//    }

    private void handleAisleCollision(PathNode node1, PathNode node2, double node2Time) {
        switch (this.problem.getCollisionsHandling()) {
            case Type2:
                this.fitness += handleType2AisleCollision(node1, node2, node2Time);
                break;
            case Type3:
//                handleType3AisleCollision();
                break;
        }
    }

    private double handleType2AisleCollision(PathNode node1, PathNode node2, double node2Time) {
        int n1Distance = 0;
        int n2Distance = 0;
        double timeDifference = Math.abs(node1.getTime() - node2Time);

        if (node1.getTime() < node2Time) {
            n1Distance += timeDifference;
        } else {
            n2Distance += timeDifference;
        }

        int distance = (int) ((Math.abs(node1.getLine() - node2.getLine()) + Math.abs(node1.getColumn() - node2.getColumn())) - timeDifference) / 2;
        n1Distance += distance;
        n2Distance += distance;


        if (!this.problem.getDecisionNodesMap().containsKey(node1.getIdentifier()) || !this.problem.getDecisionNodesMap().containsKey(node2.getIdentifier())) {
            Edge<Node> e;
            if (!this.problem.getDecisionNodesMap().containsKey(node1.getIdentifier())) {
                e = this.problem.getAisleNodeEdge().get(node1.getIdentifier());
            } else {
                e = this.problem.getAisleNodeEdge().get(node2.getIdentifier());
            }

            n1Distance += Math.min((Math.abs(node1.getLine() - e.getNode1().getLine()) + Math.abs(node1.getColumn() - e.getNode1().getColumn())), (Math.abs(node1.getLine() - e.getNode2().getLine()) + Math.abs(node1.getColumn() - e.getNode2().getColumn())));
            n2Distance += Math.min((Math.abs(node2.getLine() - e.getNode1().getLine()) + Math.abs(node2.getColumn() - e.getNode1().getColumn())), (Math.abs(node2.getLine() - e.getNode2().getLine()) + Math.abs(node2.getColumn() - e.getNode2().getColumn())));
        }

        return Math.min(n1Distance, n2Distance) + 1;
    }

//    private double handleType3AisleCollision(){
//
//    }

    private boolean isEdgeOneWay(int node1, int node2) {
        if (this.problem.getGraph().getSubEdges().containsKey(node1 + "-" + node2)) {
            return this.problem.getGraph().getSubEdges().get(node1 + "-" + node2) == EdgeDirection.ONEWAY;
        }
        return this.problem.getGraph().getSubEdges().get(node2 + "-" + node1) == EdgeDirection.ONEWAY;
    }

    private boolean rangesOverlap(double x1, double x2, double y1, double y2) {
        return x1 < y2 && y1 < x2;
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
