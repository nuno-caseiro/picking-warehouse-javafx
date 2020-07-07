package ipleiria.estg.dei.ei.pi.model.experiments;

import ipleiria.estg.dei.ei.pi.gui.ExperimentsFrameController;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class ExperimentsFactory {
    protected int numRuns;
    protected HashMap<String, Parameter> parameters;
    protected Parameter[] orderedParametersVector;
    protected List<String> statisticsNames;
    protected List<ExperimentListener> statistics;
    protected ExperimentsFrameController experimentsController;

    public ExperimentsFactory(ExperimentsFrameController experimentsFrameController){
        this.experimentsController = experimentsFrameController;
        readParametersValues();
        readStatisticsValues();
    }

    public abstract GeneticAlgorithm generateGAInstance(int seed);

    protected abstract Experiment buildExperiment();


    private void readParametersValues() {
        parameters = new HashMap<>();

        String[] values = new String[1];
        values[0]= experimentsController.getnRrunsArea().getText();
        Parameter parameter = new Parameter("Runs",values);
        parameters.put("Runs",parameter);

        /*addParameter("Population size",experimentsController.getPopSizeArea().getText());
        addParameter("Max generations",experimentParametersPanel.getMaxGenerations());
        addParameter("Selection",experimentParametersPanel.getSelectionMethods());
        addParameter("Tournament size",experimentParametersPanel.getTournamentSizeValues());
        addParameter("Selective pressure",experimentParametersPanel.getSelectivePressureValues());
        addParameter("Recombination",experimentParametersPanel.getRecombinationMethods());
        addParameter("Recombination probability",experimentParametersPanel.getRecombinationProbabilities());
        addParameter("Mutation",experimentParametersPanel.getMutationMethods());
        addParameter("Mutation probability",experimentParametersPanel.getMutationProbabilities());
        addParameter("Time weight",experimentParametersPanel.getTimeWeightValues());
        addParameter("Collisions weight",experimentParametersPanel.getCollisionsWeightsValues());
        addParameter("NumAgents",experimentParametersPanel.getNumberAgentsValues());
        addParameter("NumPicks",experimentParametersPanel.getNumberPicksValues());*/


    }

    public void addParameter(String keyName,List<String> listToAdd){
        String[] values= new String[listToAdd.size()];
        for (int i = 0; i < listToAdd.size(); i++) {
            values[i]=listToAdd.get(i);
        }
        Parameter parameter= new Parameter(keyName,values);
        parameters.put(keyName,parameter);
        //countAllRuns*=listToAdd.size();
    }

    private void readStatisticsValues() {
    }

}
