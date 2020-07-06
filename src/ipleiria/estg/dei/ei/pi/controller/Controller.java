package ipleiria.estg.dei.ei.pi.controller;

import com.google.gson.JsonParser;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.mutation.MutationInsert;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.geneticOperators.recombination.RecombinationOX;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.selectionMethods.Tournament;
import ipleiria.estg.dei.ei.pi.model.picking.Environment;
import ipleiria.estg.dei.ei.pi.model.picking.PickingGAProblem;
import ipleiria.estg.dei.ei.pi.model.picking.PickingIndividual;
import ipleiria.estg.dei.ei.pi.model.picking.PickingManhattanDistance;
import ipleiria.estg.dei.ei.pi.model.search.AStarSearch;
import ipleiria.estg.dei.ei.pi.utils.exceptions.InvalidNodeException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;

public class Controller {

    private Environment<PickingIndividual> environment;

    public Controller() {
        this.environment = new Environment<>();

        run();
    }

    private void run() {
        try {
            GeneticAlgorithm<PickingIndividual, PickingGAProblem> geneticAlgorithm = new GeneticAlgorithm<>(new PickingIndividual.PickingIndividualFactory(),
                                                                                                            new Tournament<>(100),
                                                                                                            new RecombinationOX<>(0.7),
                                                                                                            new MutationInsert<>(0.1),
                                                                                                            100, 100, new Random(1));

            this.environment.loadLayout(JsonParser.parseReader(new FileReader("src/ipleiria/estg/dei/ei/pi/dataSets/WarehouseLayout.json")).getAsJsonObject());
            this.environment.loadPicks(JsonParser.parseReader(new FileReader("src/ipleiria/estg/dei/ei/pi/dataSets/PicksWeightCapacity.json")).getAsJsonObject());

            PickingIndividual individual = geneticAlgorithm.run(new PickingGAProblem(this.environment.getGraph(), new AStarSearch<>(new PickingManhattanDistance())));

            System.out.println(individual.getFitness());
            System.out.println(Arrays.toString(individual.getGenome()));

        } catch (InvalidNodeException | FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
