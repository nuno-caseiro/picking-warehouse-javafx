package ipleiria.estg.dei.ei.pi.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GaFrameController implements Initializable {

    @FXML
    public GridPane parametersGrid;
    public TextField seedGaField;
    public TextField popSizeField;
    public TextField generationsField;
    public TextField tournamentSizeField;
    public TextField selectivePressureField;
    public TextField recombinationProbField;
    public TextField mutationProbField;
    public TextField agentsCapacityField;
    public ChoiceBox selectionMethodFieldselection;
    public ChoiceBox recombinationMethodField;
    public ChoiceBox mutationMethodField;
    public ChoiceBox collisionsHandlingFieldcollisions;
    public ChoiceBox weightLimitationField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
