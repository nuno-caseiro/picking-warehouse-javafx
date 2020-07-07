package ipleiria.estg.dei.ei.pi.gui;

import ipleiria.estg.dei.ei.pi.model.experiments.ExperimentEvent;
import ipleiria.estg.dei.ei.pi.model.experiments.ParameterGUI;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.utils.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.*;

public class ExperimentsFrameController implements Initializable, GAListener {

    @FXML
    public TextArea popSizeArea;
    @FXML
    public TextArea generationsArea;
    @FXML
    public Button removeButton;
    @FXML
    public Button addButton;
    @FXML
    public GridPane editingParametersPane;
    @FXML
    public TextArea selectionMethodArea;
    @FXML
    public TextArea tournamentSizeArea;
    @FXML
    public TextArea selectivePressureArea;
    @FXML
    public TextArea recombinationMethodArea;
    @FXML
    public TextArea recombinationProbArea;
    @FXML
    public TextArea mutationMethodArea;
    @FXML
    public TextArea mutationProbArea;
    @FXML
    public TextArea collisionsHandlingArea;
    @FXML
    public TextArea weightLimitationArea;
    @FXML
    public TextArea numberAgentsArea;
    @FXML
    public TextArea numberPicksArea;
    @FXML
    public TextArea nrRunsArea;
    @FXML
    public TextArea timeWeightsArea;
    @FXML
    public TextArea collisionWeightsArea;
    @FXML
    public TextArea bestIndividualExperimentsArea;
    @FXML
    public ListView<String> selectExpInput;
    @FXML
    public TextField intExpInput;
    @FXML
    public TextField decimalExpInput;
    @FXML
    public Button runExperimentsButton;
    @FXML
    public ListView<String> actualParameters;
    @FXML
    public Label labelEditingParameter;
    @FXML
    public TextArea statisticsArea;
    @FXML
    public ProgressBar progressBar;

    private int runsProgress;
    private double allRuns;
    private String actualParameterField;
    private ParameterGUI actualParameterGUI;
    private HashMap<String,ParameterGUI> parameters;
    private HashMap<String,Object> availableParameters;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runsProgress=0;
        addButton.prefHeightProperty().bind(editingParametersPane.heightProperty());
        addButton.prefWidthProperty().bind(editingParametersPane.prefWidthProperty());
        removeButton.prefHeightProperty().bind(editingParametersPane.heightProperty());
        removeButton.prefWidthProperty().bind(editingParametersPane.widthProperty());
        editingParametersPane.setVisible(false);
        parameters= new HashMap<>();

        availableParameters= new HashMap<>();
        List<String> values = new LinkedList<>();
        for (SelectionMethod value : SelectionMethod.values()) {
            values.add(value.toString());
        }
        availableParameters.put("Selection",values);
        values = new LinkedList<>();
        for (RecombinationMethod value : RecombinationMethod.values()) {
            values.add(value.toString());
        }
        availableParameters.put("Recombination",values);
        values = new LinkedList<>();
        for (MutationMethod value : MutationMethod.values()) {
            values.add(value.toString());
        }
        availableParameters.put("Mutation",values);
        values = new LinkedList<>();
        for (CollisionsHandling value : CollisionsHandling.values()) {
            values.add(value.toString());
        }
        availableParameters.put("CollisionsHandling",values);
        values = new LinkedList<>();
        for (WeightLimitation value : WeightLimitation.values()) {
            values.add(value.toString());
        }
        availableParameters.put("WeightLimitations",values);
        values = new LinkedList<>();
        for (Statistics value : Statistics.values()) {
            values.add(value.toString());
        }
        availableParameters.put("Statistics",values);


        parameters.put("nrRunsArea",new ParameterGUI("nrRunsArea",nrRunsArea,intExpInput,"100"));
        parameters.put("popSizeArea",new ParameterGUI("population size",popSizeArea,intExpInput,"100"));
        parameters.put("generationsArea",new ParameterGUI("# of generations",generationsArea,intExpInput,"100"));
        parameters.put("selectionMethodArea",new ParameterGUI("selection method",selectionMethodArea,selectExpInput,"Tournament"));
        parameters.put("tournamentSizeArea",new ParameterGUI("tournament size",tournamentSizeArea,intExpInput,"4"));
        parameters.put("selectivePressureArea",new ParameterGUI("selective pressure",selectivePressureArea,decimalExpInput,"2"));
        parameters.put("recombinationMethodArea",new ParameterGUI("recombination method",recombinationMethodArea,selectExpInput,"PMX"));
        parameters.put("recombinationProbArea",new ParameterGUI("recombination probability",recombinationProbArea,decimalExpInput,"0.7"));
        parameters.put("mutationMethodArea",new ParameterGUI("mutation method",mutationMethodArea,selectExpInput,"Insert"));
        parameters.put("mutationProbArea",new ParameterGUI("mutation probability",mutationProbArea,decimalExpInput,"0.1"));
        parameters.put("collisionsHandlingArea",new ParameterGUI("collisions handling",collisionsHandlingArea,selectExpInput,"Type2"));
        parameters.put("weightLimitationArea",new ParameterGUI("weight limitation",weightLimitationArea,selectExpInput,"Both"));
        parameters.put("timeWeightsArea",new ParameterGUI("time weight",timeWeightsArea,intExpInput,"1"));
        parameters.put("collisionWeightsArea",new ParameterGUI("collisions weight",collisionWeightsArea,intExpInput,"1"));
        parameters.put("numberAgentsArea",new ParameterGUI("number of agents",numberAgentsArea,intExpInput,"3"));
        parameters.put("numberPicksArea",new ParameterGUI("number of picks",numberPicksArea,intExpInput,"45"));
        parameters.put("statisticsArea",new ParameterGUI("statistics",statisticsArea,selectExpInput,"StatisticBestAverage"));




        selectExpInput.setViewOrder(-1);
    }

    public void showEditParameters(Event event){
        editingParametersPane.setVisible(true);
        actualParameterField=((Control)event.getSource()).getId();
        actualParameterGUI= parameters.get(actualParameterField);
        actualParameters.getItems().clear();
        selectExpInput.getItems().clear();
        labelEditingParameter.setText("Editing "+ actualParameterGUI.getId());
        if(actualParameterGUI.getControl().getId().equals(selectExpInput.getId())) {
            selectExpInput.setViewOrder(-1);
            intExpInput.setViewOrder(0);
            decimalExpInput.setViewOrder(0);
            switch (actualParameterField){
                case "selectionMethodArea":
                    selectExpInput.getItems().addAll((Collection<? extends String>) availableParameters.get("Selection"));
                    break;
                case "recombinationMethodArea":
                    selectExpInput.getItems().addAll((Collection<? extends String>) availableParameters.get("Recombination"));
                    break;
                case "mutationMethodArea":
                    selectExpInput.getItems().addAll((Collection<? extends String>) availableParameters.get("Mutation"));
                    break;
                case "collisionsHandlingArea":
                    selectExpInput.getItems().addAll((Collection<? extends String>) availableParameters.get("CollisionsHandling"));
                    break;
                case "weightLimitationArea":
                    selectExpInput.getItems().addAll((Collection<? extends String>) availableParameters.get("WeightLimitations"));
                    break;
                case "statisticsArea":
                    selectExpInput.getItems().addAll((Collection<? extends String>) availableParameters.get("Statistics"));
                    break;
            }
            actualParameters.getItems().addAll(actualParameterGUI.getParameters());
        }

        if(actualParameterGUI.getControl().getId().equals(intExpInput.getId())) {
            selectExpInput.setViewOrder(0);
            intExpInput.setViewOrder(-1);
            decimalExpInput.setViewOrder(0);
            actualParameters.getItems().addAll(actualParameterGUI.getParameters());
        }
        if(actualParameterGUI.getControl().getId().equals(decimalExpInput.getId())) {
            selectExpInput.setViewOrder(0);
            intExpInput.setViewOrder(0);
            decimalExpInput.setViewOrder(-1);
            actualParameters.getItems().addAll(actualParameterGUI.getParameters());
        }
    }

    public void add(){
        if(actualParameterGUI.getControl().getId().equals(selectExpInput.getId())){
            if(!actualParameterGUI.getParameters().contains(selectExpInput.getSelectionModel().getSelectedItem()) && selectExpInput.getSelectionModel().getSelectedItem()!=null){
                actualParameterGUI.getParameters().add(selectExpInput.getSelectionModel().getSelectedItem());
            }
            updateActualItems();
        }
        if(actualParameterGUI.getControl().getId().equals(intExpInput.getId())) {
            if(!actualParameterGUI.getParameters().contains(intExpInput.getText().trim()) && !intExpInput.getText().trim().isEmpty()){
                actualParameterGUI.getParameters().add(intExpInput.getText().trim());
            }
            updateActualItems();
        }
        if(actualParameterGUI.getControl().getId().equals(decimalExpInput.getId())) {
            if(!actualParameterGUI.getParameters().contains(decimalExpInput.getText().trim()) && !decimalExpInput.getText().trim().isEmpty() ){
                actualParameterGUI.getParameters().add(decimalExpInput.getText().trim());
            }
            updateActualItems();
        }
        intExpInput.clear();
        decimalExpInput.clear();
        actualParameterGUI.getTextArea().setText(listsToString(actualParameterGUI.getParameters()));
    }

    public void remove(){
        actualParameterGUI.getParameters().remove(actualParameters.getSelectionModel().getSelectedItem());
        updateActualItems();
        actualParameterGUI.getTextArea().setText(listsToString(actualParameterGUI.getParameters()));
    }

    public void updateActualItems(){
        actualParameters.getItems().clear();
        if(actualParameterGUI.getParameters().size()!=0){
        actualParameters.getItems().addAll(actualParameterGUI.getParameters());
        }
    }


    public HashMap<String, ParameterGUI> getParameters() {
        return parameters;
    }

    private String listsToString(List<String> list){
        StringBuilder sb = new StringBuilder();
        int i=0;
        for (String o : list) {
            sb.append(o);
            if(list.size()>1 && i!=list.size()-1){
                sb.append(", ");
            }
            i++;
        }
        return sb.toString();
    }

    public Button getRunExperimentsButton() {
        return runExperimentsButton;
    }


    public TextArea getNrRunsArea() {
        return nrRunsArea;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public int getRunsProgress() {
        return runsProgress;
    }

    public void setRunsProgress(int runsProgress) {
        this.runsProgress = runsProgress;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setAllRuns(double allRuns) {
        this.allRuns = allRuns;
    }

    @Override
    public void generationEnded(GeneticAlgorithm geneticAlgorithm) {

    }

    @Override
    public void runEnded(GeneticAlgorithm geneticAlgorithm) {
        runsProgress++;
        progressBar.setProgress(runsProgress/allRuns);
    }

    @Override
    public void experimentEnded(ExperimentEvent experimentEvent) {

    }
}
