package ipleiria.estg.dei.ei.pi.gui;

import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.picking.PickingGAProblem;
import ipleiria.estg.dei.ei.pi.model.picking.PickingIndividual;
import ipleiria.estg.dei.ei.pi.utils.MutationMethod;
import ipleiria.estg.dei.ei.pi.utils.RecombinationMethod;
import ipleiria.estg.dei.ei.pi.utils.SelectionMethod;
import ipleiria.estg.dei.ei.pi.utils.WeightLimitation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GaFrameController implements Initializable, GAListener<PickingIndividual, PickingGAProblem> {

    @FXML
    public GridPane parametersGrid;
    @FXML
    public TextField seedGaField;
    @FXML
    public TextField popSizeField;
    @FXML
    public TextField generationsField;
    @FXML
    public TextField tournamentSizeField;
    @FXML
    public TextField selectivePressureField;
    @FXML
    public TextField recombinationProbField;
    @FXML
    public TextField mutationProbField;
    @FXML
    public TextField agentsCapacityField;
    @FXML
    public ChoiceBox<SelectionMethod> selectionMethodFieldSelection;
    @FXML
    public ChoiceBox<RecombinationMethod> recombinationMethodField;
    @FXML
    public ChoiceBox<MutationMethod> mutationMethodField;
    @FXML
    public ChoiceBox<String> collisionsHandlingFieldCollisions;
    @FXML
    public ChoiceBox<WeightLimitation> weightLimitationField;
    @FXML
    public LineChart<Number,Number> gaChart;

    private XYChart.Series<Number,Number> seriesBestIndividual;
    private XYChart.Series<Number,Number> seriesAverageFitness;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectionMethodFieldSelection.getItems().addAll(SelectionMethod.values());
        selectionMethodFieldSelection.setValue(selectionMethodFieldSelection.getItems().get(0));

        recombinationMethodField.getItems().addAll(RecombinationMethod.values());
        recombinationMethodField.setValue(recombinationMethodField.getItems().get(0));

        mutationMethodField.getItems().addAll(MutationMethod.values());
        mutationMethodField.setValue(mutationMethodField.getItems().get(0));


        weightLimitationField.getItems().addAll(WeightLimitation.values());
        weightLimitationField.setValue(weightLimitationField.getItems().get(0));

        seriesBestIndividual = new XYChart.Series<>();
        seriesAverageFitness = new XYChart.Series<>();
        gaChart.getData().add(seriesBestIndividual);
        gaChart.getData().add(seriesAverageFitness);

    }

    public int getSeedGaField() {
        return Integer.parseInt(seedGaField.getText());
    }

    public int getPopSizeField() {
        return Integer.parseInt(popSizeField.getText());
    }

    public int getGenerationsField() {
        return Integer.parseInt(generationsField.getText());
    }

    public int getTournamentSizeField() {
        return Integer.parseInt(tournamentSizeField.getText());
    }

    public double getSelectivePressureField() {
        return Double.parseDouble(selectivePressureField.getText());
    }

    public double getRecombinationProbField() {
        return Double.parseDouble(recombinationProbField.getText());
    }

    public double getMutationProbField() {
        return Double.parseDouble(mutationProbField.getText());
    }

    public int getAgentsCapacityField() {
        return Integer.parseInt(agentsCapacityField.getText());
    }

    public SelectionMethod getSelectionMethodFieldSelection() {
        return selectionMethodFieldSelection.getValue();
    }

    public RecombinationMethod getRecombinationMethodField() {
        return recombinationMethodField.getValue();
    }

    public MutationMethod getMutationMethodField() {
        return mutationMethodField.getValue();
    }

    public ChoiceBox<String> getCollisionsHandlingFieldCollisions() {
        return collisionsHandlingFieldCollisions;
    }

    public ChoiceBox<WeightLimitation> getWeightLimitationField() {
        return weightLimitationField;
    }

    public XYChart.Series<Number, Number> getSeriesBestIndividual() {
        return seriesBestIndividual;
    }

    public XYChart.Series<Number, Number> getSeriesAverageFitness() {
        return seriesAverageFitness;
    }

    @Override
    public void generationEnded(GeneticAlgorithm<PickingIndividual, PickingGAProblem> geneticAlgorithm) {
        this.seriesBestIndividual.getData().add(new XYChart.Data<>(geneticAlgorithm.getT(),geneticAlgorithm.getBestInRun().getFitness()));
        this.seriesAverageFitness.getData().add(new XYChart.Data<>(geneticAlgorithm.getT(),geneticAlgorithm.getPopulation().getAverageFitness()));
    }

    @Override
    public void runEnded(GeneticAlgorithm<PickingIndividual, PickingGAProblem> geneticAlgorithm) {

    }
}
