package ipleiria.estg.dei.ei.pi.model.experiments;

import ipleiria.estg.dei.ei.pi.gui.ExperimentsFrameController;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.picking.Graph;
import ipleiria.estg.dei.ei.pi.model.picking.Node;
import ipleiria.estg.dei.ei.pi.model.picking.PickingGraph;
import javafx.fxml.FXML;

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
    @FXML
    protected ExperimentsFrameController experimentsController;

    public ExperimentsFactory(ExperimentsFrameController experimentsFrameController){
        this.experimentsController = experimentsFrameController;
        readParametersValues();
        readStatisticsValues();
    }

    public abstract GeneticAlgorithm generateGAInstance(int seed);

    protected abstract Experiment<ExperimentsFactory, GAProblem>  buildExperiment(PickingGraph pickingGraph);


    private void readParametersValues() {
        parameters = new HashMap<>();

        String[] values = new String[1];
        values[0]= experimentsController.getnRunsArea().getText();
        Parameter parameter = new Parameter("Runs",values);
        parameters.put("Runs",parameter);

        addParameter("Population size",experimentsController.getParameters().get("popSizeArea").getParameters());
        addParameter("Max generations",experimentsController.getParameters().get("generationsArea").getParameters());
        addParameter("Selection",experimentsController.getParameters().get("selectionMethodArea").getParameters());
        addParameter("Tournament size",experimentsController.getParameters().get("tournamentSizeArea").getParameters());
        addParameter("Selective pressure",experimentsController.getParameters().get("selectivePressureArea").getParameters());
        addParameter("Recombination",experimentsController.getParameters().get("recombinationMethodArea").getParameters());
        addParameter("Recombination probability",experimentsController.getParameters().get("recombinationProbArea").getParameters());
        addParameter("Mutation",experimentsController.getParameters().get("mutationMethodArea").getParameters());
        addParameter("Mutation probability",experimentsController.getParameters().get("mutationProbArea").getParameters());
        addParameter("Collisions handling",experimentsController.getParameters().get("collisionsHandlingArea").getParameters());
        addParameter("Weight limitation",experimentsController.getParameters().get("weightLimitationArea").getParameters());
        addParameter("Time weight",experimentsController.getParameters().get("timeWeightsArea").getParameters());
        addParameter("Collisions weight",experimentsController.getParameters().get("collisionWeightsArea").getParameters());
        addParameter("NumAgents",experimentsController.getParameters().get("numberAgentsArea").getParameters());
        addParameter("NumPicks",experimentsController.getParameters().get("numberPicksArea").getParameters());


    }

    public void indicesManaging(int i){
        String key;
        String[] keys= parameters.keySet().toArray(new String[0]);
        key=keys[i];

        parameters.get(key).activeValueIndex++;
        if (i != 0 && parameters.get(key).activeValueIndex >= parameters.get(key).getNumberOfValues()) {
            parameters.get(key).activeValueIndex = 0;
            indicesManaging(--i);
        }
    }

    public boolean hasMoreExperiments(){
        return parameters.get("Runs").activeValueIndex < parameters.get("Runs").getNumberOfValues();
    }

    public Experiment<ExperimentsFactory, GAProblem> nextExperiment(PickingGraph pickingGraph) {
        if (hasMoreExperiments()) {
            Experiment<ExperimentsFactory, GAProblem> experiment = buildExperiment(pickingGraph);
            indicesManaging(parameters.keySet().size() - 1);
            return experiment;
        }
        return null;
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
        addParameter("Statistics",experimentsController.getParameters().get("statisticsArea").getParameters());
    }

    protected String getParameterValue(String parameterName){
        if(parameters.get(parameterName)!=null){
            return parameters.get(parameterName).getActiveValue();
        }
        return null;
    }

    public HashMap<String, Parameter> getParameters() {
        return parameters;
    }


}
