package ipleiria.estg.dei.ei.pi.model.search;

import ipleiria.estg.dei.ei.pi.model.State;
import ipleiria.estg.dei.ei.pi.utils.exceptions.NoSolutionFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
// S = The objects returned in the solution (e.g. when searching in a graph S is the node of the graph)
public abstract class GraphSearch<F extends NodeCollection, S extends State> {

    protected F frontier;
    protected HashSet<Integer> explored;
    // TODO protected boolean stopped;


    public GraphSearch(F frontier) {
        this.frontier = frontier;
        this.explored = new HashSet<>();
    }

    protected List<SearchNode<S>> graphSearch(SearchProblem<S> problem) throws NoSolutionFoundException {
        frontier.clear();
        explored.clear();
        frontier.add(new SearchNode<>(problem.getInitialState()));

        while (!frontier.isEmpty()) {
            SearchNode<S> searchNode = frontier.poll();

            if (problem.isGoal(searchNode.getState().getIdentifier())) {
                return computeSolution(searchNode);
            }

            explored.add(searchNode.getState().getIdentifier());
            addSuccessorsToFrontier(problem.getSuccessors(), searchNode, problem.getGoalState());
        }

        throw new NoSolutionFoundException("No solution found from " + problem.getInitialState().toString() + " to " + problem.getGoalState().toString());
    }

    public abstract void addSuccessorsToFrontier(List<S> successors, SearchNode<S> parent, S goalState);

    private List<SearchNode<S>> computeSolution(SearchNode<S> searchNode) {
        List<SearchNode<S>> solution = new ArrayList<>();

        while (searchNode != null) {
            solution.add(0, searchNode); // TODO add generic to SearchNode
            searchNode = searchNode.getParent();
        }

        return solution;
    }
}
