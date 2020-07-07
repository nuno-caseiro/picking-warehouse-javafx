package ipleiria.estg.dei.ei.pi.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

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
    public TextArea nRrunsArea;
    @FXML
    public TextArea timeWeightsArea;
    @FXML
    public TextArea colisionWeightsArea;
    @FXML
    public TextArea bestIndividualExperimentsArea;
    @FXML
    public ListView selectExpInput;
    @FXML
    public TextField intExpInput;
    @FXML
    public TextField decimalExpInput;
    @FXML
    public Button runExperimentsButton;
    @FXML
    public ListView atualParameters;

    private List<String> populationSizes;
    private List<String> maxGenerations;
    private List<String> selectionMethods;
    private List<String> tournamentSizeValues;
    private List<String> selectivePressureValues;
    private List<String> recombinationMethods;
    private List<String> recombinationProbabilities;
    private List<String> mutationMethods;
    private List<String> mutationProbabilities;
    private List<String> collisionsHandling;
    private List<String> weightLimitations;
    private List<String> timeWeightValues;
    private List<String> collisionsWeightsValues;
    private List<String> numberAgentsValues;
    private List<String> numberPicksValues;

    private HashMap<String,Object> availableParameters;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.prefHeightProperty().bind(editingParametersPane.heightProperty());
        addButton.prefWidthProperty().bind(editingParametersPane.prefWidthProperty());
        removeButton.prefHeightProperty().bind(editingParametersPane.heightProperty());
        removeButton.prefWidthProperty().bind(editingParametersPane.widthProperty());

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
        values.add("DX");
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
        values.add("Type 1");
        values.add("Type 2");


        populationSizes = new LinkedList<>();
        maxGenerations = new LinkedList<>();
        selectionMethods = new LinkedList<>();
        tournamentSizeValues = new LinkedList<>();
        selectivePressureValues = new LinkedList<>();
        recombinationMethods= new LinkedList<>();
        recombinationProbabilities = new LinkedList<>();
        mutationMethods= new LinkedList<>();
        mutationProbabilities= new LinkedList<>();
        collisionsHandling= new LinkedList<>();
        weightLimitations= new LinkedList<>();
        timeWeightValues= new LinkedList<>();
        collisionsHandling= new LinkedList<>();
        collisionsWeightsValues= new LinkedList<>();
        numberAgentsValues= new LinkedList<>();
        numberPicksValues= new LinkedList<>();
    }

    public void showEditParameters(){

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

    public TextArea getnRrunsArea() {
        return nRrunsArea;
    }

    public TextArea getTimeWeightsArea() {
        return timeWeightsArea;
    }

    public TextArea getColisionWeightsArea() {
        return colisionWeightsArea;
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
