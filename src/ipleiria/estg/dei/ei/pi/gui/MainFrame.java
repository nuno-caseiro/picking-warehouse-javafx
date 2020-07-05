package ipleiria.estg.dei.ei.pi.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFrame implements Initializable {

    @FXML
    public Button loadLayoutButton;

    @FXML
    public Button loadPicksButton;

    @FXML
    public Button runGaButton;

    @FXML
    public Button stopGAButton;

    @FXML
    public Button simulationButton;

    @FXML
    public Button startPauseButton;

    @FXML
    public Button stepForwardButton;

    @FXML
    public Button stepBackwordButton;

    @FXML
    public AnchorPane simulationFrame;

    @FXML
    public AnchorPane gaFrame;

    @FXML
    private Tab gaTab ;

    @FXML
    private Tab simulationTab;

    @FXML
    private Tab experimentsTab;

    @FXML
    private SimulationFrameController simulationFrameController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView loadWarehouseIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/loadWarehouseLayoutIcon.png"));
        ImageView loadPicksIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/loadPicksIcon.png"));

        ImageView gaTabIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/gaIcon.png"));
        ImageView simulationTabIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/simulationIcon.png"));
        ImageView experimentsTabIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/experimentsIcon.png"));

        ImageView runGaIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/gaRunIcon.png"));
        ImageView stopGaIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/stopGARunIcon.png"));
        ImageView simulationIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/simulationRunIcon.png"));

        ImageView pauseIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/pausePlayIcon.png"));
        ImageView stepForwardIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/nextIcon.png"));
        ImageView stepBackwardIcon = new ImageView(new Image("file:src/ipleiria/estg/dei/ei/pi/gui/assets/backIcon.png"));


        loadLayoutButton.setGraphic(loadWarehouseIcon);
        loadPicksButton.setGraphic(loadPicksIcon);

        gaTab.setGraphic(gaTabIcon);
        simulationTab.setGraphic(simulationTabIcon);
        experimentsTab.setGraphic(experimentsTabIcon);

        runGaButton.setGraphic(runGaIcon);
        stopGAButton.setGraphic(stopGaIcon);
        simulationButton.setGraphic(simulationIcon);

        startPauseButton.setGraphic(pauseIcon);
        stepForwardButton.setGraphic(stepForwardIcon);
        stepBackwordButton.setGraphic(stepBackwardIcon);






    }

    public void play() throws IOException {
        simulationFrameController.start();
    }
}
