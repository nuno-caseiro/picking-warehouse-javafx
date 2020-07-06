package ipleiria.estg.dei.ei.pi.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.prefHeightProperty().bind(editingParametersPane.heightProperty());
        addButton.prefWidthProperty().bind(editingParametersPane.prefWidthProperty());
        removeButton.prefHeightProperty().bind(editingParametersPane.heightProperty());
        removeButton.prefWidthProperty().bind(editingParametersPane.widthProperty());

    }
}
