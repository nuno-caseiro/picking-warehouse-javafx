package ipleiria.estg.dei.ei.pi.controller;

import com.google.gson.JsonParser;
import ipleiria.estg.dei.ei.pi.gui.MainFrameController;
import ipleiria.estg.dei.ei.pi.model.experiments.Experiment;
import ipleiria.estg.dei.ei.pi.model.experiments.ExperimentsFactory;
import ipleiria.estg.dei.ei.pi.model.experiments.PickingExperimentsFactory;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.Mutation;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.MutationInsert;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.MutationInversion;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.MutationScramble;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.recombination.*;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.selectionMethods.RankBased;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.selectionMethods.SelectionMethod;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.selectionMethods.Tournament;
import ipleiria.estg.dei.ei.pi.model.picking.*;
import ipleiria.estg.dei.ei.pi.model.search.AStarSearch;
import ipleiria.estg.dei.ei.pi.utils.CollisionsHandling;
import ipleiria.estg.dei.ei.pi.utils.PickLocation;
import ipleiria.estg.dei.ei.pi.utils.WeightLimitation;
import ipleiria.estg.dei.ei.pi.utils.exceptions.InvalidNodeException;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Controller {

    private Environment<PickingIndividual> environment;
    private MainFrameController mainFrame;
    private SwingWorker<Void, Void> worker;
    private SwingWorker<Void, Void> workerExperiments;

    public Controller(MainFrameController mainFrameController) {
        this.environment = new Environment<>();
        this.mainFrame = mainFrameController;
        initController();
    }

    public void initController() {
        this.mainFrame.getLoadLayoutButton().setOnAction(e -> loadWarehouseLayout());
        this.mainFrame.getLoadPicksButton().setOnAction(e -> loadPicks());
        this.mainFrame.getRunGaButton().setOnAction(e -> runGA());
        this.mainFrame.getStopGAButton().setOnAction(e -> stopGa());
        this.mainFrame.getSimulationButton().setOnAction(e->simulate());
        this.mainFrame.getExperimentsFrameController().getRunExperimentsButton().setOnAction(e->runExperiments());
        this.mainFrame.manageButtons(false,true,true,true,true,true,true);
    }

    public void stopGa(){
        worker.cancel(true);
        environment.stopGA();
    }

    private void runExperiments() {
        workerExperiments = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try{
                    mainFrame.getExperimentsFrameController().getProgressBar().setProgress(0);
                    mainFrame.getExperimentsFrameController().setRunsProgress(0);
                    ExperimentsFactory experimentsFactory = new PickingExperimentsFactory(mainFrame.getExperimentsFrameController(),JsonParser.parseReader(new FileReader("src/ipleiria/estg/dei/ei/pi/dataSets/WarehouseLayout.json")).getAsJsonObject());
                    mainFrame.getExperimentsFrameController().setAllRuns(experimentsFactory.getCountAllRuns());
                    while (experimentsFactory.hasMoreExperiments()){
                        Experiment<ExperimentsFactory, GAProblem> experiment = experimentsFactory.nextExperiment();
                        experiment.run();
                    }
                }catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            protected void done() {
                super.done();
                System.out.println("done experiments");
            }
        };
        workerExperiments.execute();
    }


    private void simulate() {
        worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mainFrame.manageButtons(false,false,true,true,false,false,false);
                            mainFrame.getSimulationFrameController().start(environment.getBestInRun());
                            mainFrame.getSlider().setMax(mainFrame.getSimulationFrameController().getTimeline().getTotalDuration().toMillis());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;

            }

            @Override
            protected void done() {
                super.done();
                mainFrame.manageButtons(false,false,false,true,false,false,false);

            }
        };

        worker.execute();
    }

    private void loadWarehouseLayout() {
        this.environment.addEnvironmentListener(this.mainFrame.getSimulationFrameController());
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("src/ipleiria/estg/dei/ei/pi/dataSets/warehouseLayout"));
            File selectedFile = fileChooser.showOpenDialog(Window.getWindows().get(0));
            if(selectedFile!=null){
                this.environment.loadWarehouseFile(JsonParser.parseReader(new FileReader(selectedFile.getAbsolutePath())).getAsJsonObject());
            }

            this.mainFrame.manageButtons(false,false,true,true,true,true,true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadPicks() {
        try {
            if (this.environment.getJsonLayout() != null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("src/ipleiria/estg/dei/ei/pi/dataSets/picks"));
                File selectedFile = fileChooser.showOpenDialog(Window.getWindows().get(0));
                if(selectedFile!=null){
                    this.environment.loadGraph(JsonParser.parseReader(new FileReader(selectedFile.getAbsolutePath())).getAsJsonObject());
                }

                this.mainFrame.manageButtons(false,false,false,true,true,true,true);
            }
        } catch (InvalidNodeException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void runGA() {

         GeneticAlgorithm<PickingIndividual, PickingGAProblem> geneticAlgorithm = new GeneticAlgorithm<>(new PickingIndividual.PickingIndividualFactory(),
                getSelectionMethod(),
                getRecombinationMethod(),
                getMutationMethod(),
                mainFrame.getGaFrameController().getPopSizeField(), mainFrame.getGaFrameController().getGenerationsField(), new Random(mainFrame.getGaFrameController().getSeedGaField()));

        geneticAlgorithm.addGAListener(mainFrame.getGaFrameController());
        environment.setGeneticAlgorithm(geneticAlgorithm);
        worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try{
                    mainFrame.getGaFrameController().getBestInRunArea().setText("");
                    mainFrame.manageButtons(true,true,true,false,true,true,true);
                    mainFrame.getGaFrameController().getSeriesBestIndividual().getData().clear();
                    mainFrame.getGaFrameController().getSeriesAverageFitness().getData().clear();
                    PickingIndividual individual = geneticAlgorithm.run(new PickingGAProblem(environment.getGraph(), new AStarSearch<>(new PickingManhattanDistance()), mainFrame.getGaFrameController().getWeightLimitationValue(), mainFrame.getGaFrameController().getCollisionsHandlingValue(),mainFrame.getGaFrameController().getTimeWeightField(),mainFrame.getGaFrameController().getCollisionWeightField()));
                    environment.setBestInRun(individual);
                    System.out.println(individual.getFitness());
                    System.out.println(individual.getNumberOfCollisions());
                    System.out.println(individual.getNumberTimesOffload());
                    System.out.println(Arrays.toString(individual.getGenome()));

                }catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            protected void done() {
                super.done();
                mainFrame.manageButtons(false,false,false,true,false,true,true);

            }
        };
        worker.execute();
    }


    private SelectionMethod<PickingIndividual, PickingGAProblem> getSelectionMethod() {
        switch (mainFrame.getGaFrameController().selectionMethodFieldSelection.getValue()) {
            case Tournament:
                return new Tournament<>(mainFrame.getGaFrameController().getPopSizeField(),mainFrame.getGaFrameController().getTournamentSizeField());
            case Rank:
                return new RankBased<>(mainFrame.getGaFrameController().getPopSizeField(),mainFrame.getGaFrameController().getSelectivePressureField());
        }
        return null;
    }


    private Recombination<PickingIndividual, PickingGAProblem> getRecombinationMethod() {

        switch (mainFrame.getGaFrameController().recombinationMethodField.getValue()) {
            case PMX:
                return new RecombinationPartialMapped<>(mainFrame.getGaFrameController().getRecombinationProbField());
            case OX:
                return new RecombinationOX<>(mainFrame.getGaFrameController().getRecombinationProbField());
            case OX1:
                return new RecombinationOX1<>(mainFrame.getGaFrameController().getRecombinationProbField());
            case CX:
                return new RecombinationCX<>(mainFrame.getGaFrameController().getRecombinationProbField());
        }
        return null;

    }

    private Mutation<PickingIndividual, PickingGAProblem> getMutationMethod(){
        switch (mainFrame.getGaFrameController().mutationMethodField.getValue()) {
            case Insert:
                return new MutationInsert<>(mainFrame.getGaFrameController().getMutationProbField());
            case Inversion:
                return new MutationInversion<>(mainFrame.getGaFrameController().getMutationProbField());
            case Scramble:
                return new MutationScramble<>(mainFrame.getGaFrameController().getMutationProbField());
        }
        return null;
    }




}
