package ipleiria.estg.dei.ei.pi.controller;

import com.google.gson.JsonParser;
import ipleiria.estg.dei.ei.pi.gui.MainFrameController;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.Mutation;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.MutationInsert;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.MutationInversion;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.MutationScramble;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.recombination.Recombination;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.recombination.RecombinationOX;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.recombination.RecombinationOX1;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.recombination.RecombinationPartialMapped;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.selectionMethods.Tournament;
import ipleiria.estg.dei.ei.pi.model.picking.Environment;
import ipleiria.estg.dei.ei.pi.model.picking.PickingGAProblem;
import ipleiria.estg.dei.ei.pi.model.picking.PickingIndividual;
import ipleiria.estg.dei.ei.pi.model.picking.PickingManhattanDistance;
import ipleiria.estg.dei.ei.pi.model.search.AStarSearch;
import ipleiria.estg.dei.ei.pi.utils.WeightLimitation;
import ipleiria.estg.dei.ei.pi.utils.exceptions.InvalidNodeException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;

public class Controller {

    private Environment<PickingIndividual> environment;
    private MainFrameController mainFrame;

    public Controller(MainFrameController mainFrameController) {
        this.environment = new Environment<>();
        this.mainFrame = mainFrameController;
        initController();
    }

    public void initController() {
        this.mainFrame.getLoadLayoutButton().setOnAction(e -> loadWarehouseLayout());
        this.mainFrame.getLoadPicksButton().setOnAction(e -> loadPicks());
        this.mainFrame.getRunGaButton().setOnAction(e -> runGA());
    }

    private void loadWarehouseLayout() {
        try {
            this.environment.loadLayout(JsonParser.parseReader(new FileReader("src/ipleiria/estg/dei/ei/pi/dataSets/WarehouseLayout.json")).getAsJsonObject());
        } catch (InvalidNodeException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadPicks() {
        try {
            this.environment.loadPicks(JsonParser.parseReader(new FileReader("src/ipleiria/estg/dei/ei/pi/dataSets/PicksWeightCapacity.json")).getAsJsonObject());
        } catch (InvalidNodeException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void runGA() {
        mainFrame.getGaFrameController().getSeriesBestIndividual().getData().clear();
        mainFrame.getGaFrameController().getSeriesAverageFitness().getData().clear();

        GeneticAlgorithm<PickingIndividual, PickingGAProblem> geneticAlgorithm = new GeneticAlgorithm<>(new PickingIndividual.PickingIndividualFactory(),
                new Tournament<>(mainFrame.getGaFrameController().getPopSizeField(), mainFrame.getGaFrameController().getTournamentSizeField()),
                getRecombinationMethod(),
                getMutationMethod(),
                mainFrame.getGaFrameController().getPopSizeField(), mainFrame.getGaFrameController().getGenerationsField(), new Random(mainFrame.getGaFrameController().getSeedGaField()));

        geneticAlgorithm.addGAListener(mainFrame.getGaFrameController());

        PickingIndividual individual = geneticAlgorithm.run(new PickingGAProblem(this.environment.getGraph(), new AStarSearch<>(new PickingManhattanDistance()), WeightLimitation.Picks));

        System.out.println(individual.getFitness());
        System.out.println(Arrays.toString(individual.getGenome()));
    }

    private Recombination<PickingIndividual, PickingGAProblem> getRecombinationMethod() {

        switch (mainFrame.getGaFrameController().recombinationMethodField.getValue()) {
            case PMX:
                return new RecombinationPartialMapped<>(mainFrame.getGaFrameController().getRecombinationProbField());
            case OX:
                return new RecombinationOX<>(mainFrame.getGaFrameController().getRecombinationProbField());
            case OX1:
                return new RecombinationOX1<>(mainFrame.getGaFrameController().getRecombinationProbField());
            /*case CX:
                return new RecombinationCX<>(mainFrame.getGaFrameController().getRecombinationProbField());
                break;*/
            default:
                return null;
        }
    }

    private Mutation<PickingIndividual, PickingGAProblem> getMutationMethod(){
        switch (mainFrame.getGaFrameController().mutationMethodField.getValue()) {
            case Insert:
                return new MutationInsert<>(mainFrame.getGaFrameController().getMutationProbField());
            case Inversion:
                return new MutationInversion<>(mainFrame.getGaFrameController().getMutationProbField());
            case Scramble:
                return new MutationScramble<>(mainFrame.getGaFrameController().getMutationProbField());
            default:
                return null;
        }
    }


}
