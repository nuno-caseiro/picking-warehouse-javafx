package ipleiria.estg.dei.ei.pi.gui;

import ipleiria.estg.dei.ei.pi.model.experiments.ExperimentEvent;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.picking.PickingGAProblem;
import ipleiria.estg.dei.ei.pi.model.picking.PickingIndividual;
import ipleiria.estg.dei.ei.pi.utils.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
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
    public ChoiceBox<CollisionsHandling> collisionsHandlingFieldCollisions;
    @FXML
    public ChoiceBox<WeightLimitation> weightLimitationField;
    @FXML
    public LineChart<Number,Number> gaChart;
    @FXML
    public TextField timeWeightField;
    @FXML
    public TextField collisionWeightField;
    @FXML
    public TextArea bestInRunArea;

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

        collisionsHandlingFieldCollisions.getItems().addAll(CollisionsHandling.values());
        collisionsHandlingFieldCollisions.setValue(collisionsHandlingFieldCollisions.getItems().get(0));

        seriesBestIndividual = new XYChart.Series<>();
        seriesAverageFitness = new XYChart.Series<>();
        seriesAverageFitness.setName("Average");
        seriesBestIndividual.setName("Best");
        gaChart.getData().add(seriesBestIndividual);
        gaChart.getData().add(seriesAverageFitness);

        collisionsHandlingFieldCollisions.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(t1.intValue()!=0){
                    timeWeightField.setDisable(true);
                    collisionWeightField.setDisable(true);
                }else{
                    timeWeightField.setDisable(false);
                    collisionWeightField.setDisable(false);
                }
            }
        });

    }

    public int getTimeWeightField() {
        return Integer.parseInt(timeWeightField.getText());
    }

    public int getCollisionWeightField() {
        return Integer.parseInt(collisionWeightField.getText());
    }

    public CollisionsHandling getCollisionsHandlingValue(){
        return CollisionsHandling.valueOf(collisionsHandlingFieldCollisions.getSelectionModel().getSelectedItem().toString());
    }

    public WeightLimitation getWeightLimitationValue(){
        return WeightLimitation.valueOf(weightLimitationField.getSelectionModel().getSelectedItem().toString());
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

    public ChoiceBox<CollisionsHandling> getCollisionsHandlingFieldCollisions() {
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

    public TextArea getBestInRunArea() {
        return bestInRunArea;
    }

    @Override
    public void generationEnded(GeneticAlgorithm<PickingIndividual, PickingGAProblem> geneticAlgorithm) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                seriesBestIndividual.getData().add(new XYChart.Data<>(geneticAlgorithm.getT(),geneticAlgorithm.getBestInRun().getFitness()));
                seriesAverageFitness.getData().add(new XYChart.Data<>(geneticAlgorithm.getT(),geneticAlgorithm.getPopulation().getAverageFitness()));

            }
        });
    }

    @Override
    public void runEnded(GeneticAlgorithm<PickingIndividual, PickingGAProblem> geneticAlgorithm) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            bestInRunArea.setText(geneticAlgorithm.getBestInRun().toString());

            }
        });
    }

    @Override
    public void experimentEnded(ExperimentEvent experimentEvent) {

    }
}
