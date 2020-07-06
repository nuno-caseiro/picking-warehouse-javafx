package ipleiria.estg.dei.ei.pi.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController implements Initializable {

    @FXML
    private Button loadLayoutButton;

    @FXML
    private Button loadPicksButton;

    @FXML
    private Button runGaButton;

    @FXML
    private Button stopGAButton;

    @FXML
    private Button simulationButton;

    @FXML
    private Button startPauseButton;

    @FXML
    private Button stepForwardButton;

    @FXML
    private Button stepBackwardButton;

    @FXML
    private StackPane simulationFrame;

    @FXML
    private AnchorPane gaFrame;

    @FXML
    private Slider slider;

    @FXML
    private Tab gaTab ;

    @FXML
    private Tab simulationTab;

    @FXML
    private Tab experimentsTab;

    @FXML
    private SimulationFrameController simulationFrameController;

    @FXML
    private GaFrameController gaFrameController;

    @FXML
    private ExperimentsFrameController experimentsFrameController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        simulationFrameController.init(this);


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
        stepBackwardButton.setGraphic(stepBackwardIcon);


    }

    public Button getRunGaButton() {
        return runGaButton;
    }

    public void play() {
        simulationFrameController.start();
        slider.setMax(simulationFrameController.st.getTotalDuration().toMillis());
    }


    public void playFromSlider(){
        simulationFrameController.startFromSlider(slider.getValue());
    }

    public Button getLoadLayoutButton() {
        return loadLayoutButton;
    }

    public Button getLoadPicksButton() {
        return loadPicksButton;
    }

    public Button getStopGAButton() {
        return stopGAButton;
    }

    public Button getSimulationButton() {
        return simulationButton;
    }

    public Button getStartPauseButton() {
        return startPauseButton;
    }

    public Button getStepForwardButton() {
        return stepForwardButton;
    }

    public Button getStepBackwardButton() {
        return stepBackwardButton;
    }

    public Slider getSlider() {
        return slider;
    }

    public GaFrameController getGaFrameController() {
        return gaFrameController;
    }
}
