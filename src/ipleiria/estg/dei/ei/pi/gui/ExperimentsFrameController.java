package ipleiria.estg.dei.ei.pi.gui;

import ipleiria.estg.dei.ei.pi.model.experiments.ParameterGUI;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.*;

public class ExperimentsFrameController implements Initializable {

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

    private String actualParameterField;
    private ParameterGUI actualParameterGUI;
    private HashMap<String,ParameterGUI> parameters;
    private HashMap<String,Object> availableParameters;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.prefHeightProperty().bind(editingParametersPane.heightProperty());
        addButton.prefWidthProperty().bind(editingParametersPane.prefWidthProperty());
        removeButton.prefHeightProperty().bind(editingParametersPane.heightProperty());
        removeButton.prefWidthProperty().bind(editingParametersPane.widthProperty());
        editingParametersPane.setVisible(false);
        parameters= new HashMap<>();

        availableParameters= new HashMap<>();
        List<String> values = new LinkedList<>();
        values.add("Tournament");
        values.add("Rank");
        availableParameters.put("Selection",values);
        values = new LinkedList<>();
        values.add("PMX");
        values.add("OX");
        values.add("OX1");
        values.add("CX");
        availableParameters.put("Recombination",values);
        values = new LinkedList<>();
        values.add("Insert");
        values.add("Inversion");
        values.add("Scramble");
        availableParameters.put("Mutation",values);
        values = new LinkedList<>();
        values.add("Type 1");
        values.add("Type 2");
        values.add("Type 3");
        availableParameters.put("CollisionsHandling",values);
        values = new LinkedList<>();
        values.add("Picks");
        values.add("Agent");
        values.add("Both");
        availableParameters.put("WeightLimitations",values);
        values = new LinkedList<>();
        values.add("StatisticBestAverage");
        availableParameters.put("Statistics",values);


        parameters.put("nrRunsArea",new ParameterGUI("nrRunsArea",nrRunsArea,intExpInput));
        parameters.put("popSizeArea",new ParameterGUI("population size",popSizeArea,intExpInput));
        parameters.put("generationsArea",new ParameterGUI("# of generations",generationsArea,intExpInput));
        parameters.put("selectionMethodArea",new ParameterGUI("selection method",selectionMethodArea,selectExpInput));
        parameters.put("tournamentSizeArea",new ParameterGUI("tournament size",tournamentSizeArea,intExpInput));
        parameters.put("selectivePressureArea",new ParameterGUI("selective pressure",selectivePressureArea,decimalExpInput));
        parameters.put("recombinationMethodArea",new ParameterGUI("recombination method",recombinationMethodArea,selectExpInput));
        parameters.put("recombinationProbArea",new ParameterGUI("recombination probability",recombinationProbArea,decimalExpInput));
        parameters.put("mutationMethodArea",new ParameterGUI("mutation method",mutationMethodArea,selectExpInput));
        parameters.put("mutationProbArea",new ParameterGUI("mutation probability",mutationProbArea,decimalExpInput));
        parameters.put("collisionsHandlingArea",new ParameterGUI("collisions handling",collisionsHandlingArea,selectExpInput));
        parameters.put("weightLimitationArea",new ParameterGUI("weight limitation",weightLimitationArea,selectExpInput));
        parameters.put("timeWeightsArea",new ParameterGUI("time weight",timeWeightsArea,intExpInput));
        parameters.put("collisionWeightsArea",new ParameterGUI("collisions weight",collisionWeightsArea,intExpInput));
        parameters.put("numberAgentsArea",new ParameterGUI("number of agents",numberAgentsArea,intExpInput));
        parameters.put("numberPicksArea",new ParameterGUI("number of picks",numberPicksArea,intExpInput));
        parameters.put("statisticsArea",new ParameterGUI("statistics",statisticsArea,selectExpInput));



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

    public TextArea getPopSizeArea() {
        return popSizeArea;
    }

    public TextArea getGenerationsArea() {
        return generationsArea;
    }

    public TextArea getSelectionMethodArea() {
        return selectionMethodArea;
    }

    public TextArea getTournamentSizeArea() {
        return tournamentSizeArea;
    }

    public TextArea getSelectivePressureArea() {
        return selectivePressureArea;
    }

    public TextArea getRecombinationMethodArea() {
        return recombinationMethodArea;
    }

    public TextArea getRecombinationProbArea() {
        return recombinationProbArea;
    }

    public TextArea getMutationMethodArea() {
        return mutationMethodArea;
    }

    public TextArea getMutationProbArea() {
        return mutationProbArea;
    }

    public TextArea getCollisionsHandlingArea() {
        return collisionsHandlingArea;
    }

    public TextArea getWeightLimitationArea() {
        return weightLimitationArea;
    }

    public TextArea getNumberAgentsArea() {
        return numberAgentsArea;
    }

    public TextArea getNumberPicksArea() {
        return numberPicksArea;
    }

    public TextArea getnRunsArea() {
        return nrRunsArea;
    }

    public TextArea getTimeWeightsArea() {
        return timeWeightsArea;
    }

    public TextArea getCollisionWeightsArea() {
        return collisionWeightsArea;
    }

    public TextArea getBestIndividualExperimentsArea() {
        return bestIndividualExperimentsArea;
    }

    public TextField getIntExpInput() {
        return intExpInput;
    }

    public TextField getDecimalExpInput() {
        return decimalExpInput;
    }
}
