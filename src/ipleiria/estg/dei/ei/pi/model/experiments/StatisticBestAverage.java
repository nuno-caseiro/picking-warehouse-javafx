package ipleiria.estg.dei.ei.pi.model.experiments;


import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GAProblem;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.pi.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.pi.utils.FileOperations;
import ipleiria.estg.dei.ei.pi.utils.Maths;


import java.io.File;
import java.util.Arrays;

public class StatisticBestAverage<I extends Individual<GAProblem>,P extends GAProblem> implements GAListener<I,P>  {

    private final double[] values;
    private final double[] valuesWoCollisions;
    private int run;
    private final double[] allRunsCollisions;
    private double numberTimesOffload;

    public StatisticBestAverage(int numRuns, String experimentHeader) {
        run=0;
        values = new double[numRuns];
        valuesWoCollisions = new double[numRuns];
        allRunsCollisions = new double[numRuns];
        numberTimesOffload = 0;
        File file = new File("statistic_average_fitness_1.xls");
        if(!file.exists()){
            FileOperations.appendToTextFile("statistic_average_fitness_1.xls", experimentHeader + "AverageFitness:" + "\t" + "AverageFitnessStdDev:" + "\t" + "AverageTime:" + "\t" + "AverageTimeStdDev:" + "\t" + "CollisionsAverage" + "\t" + "CollisionsAverageStdDev" +"\t"+"#Offload"+"\r\n");
        }
    }


    @Override
    public void generationEnded(GeneticAlgorithm<I,P> e) {

    }

    @Override
    public void runEnded(GeneticAlgorithm<I,P> geneticAlgorithm) {
        values[run++]=geneticAlgorithm.getBestInRun().getFitness();
        //valuesWoCollisions[run]=geneticAlgorithm.getBestInRun().getTime();
        //allRunsCollisions[run++]=geneticAlgorithm.getBestInRun().getNumberOfCollisions();
        //this.numberTimesOffload += geneticAlgorithm.getBestInRun().getNumberTimesOffload();
        //TODO
    }


    @Override
    public void experimentEnded(ExperimentEvent e) {
        System.out.println("experiment ended");
        double average = Maths.average(values);
        double stdDeviation= Maths.standardDeviation(values,average);
        double collisionsAverage = Maths.average(allRunsCollisions);
        double collisionStdDeviation = Maths.standardDeviation(allRunsCollisions,collisionsAverage);

     //   double averageWoCollisions = Maths.average(valuesWoCollisions);
     //   double stdDeviationWoCollisions = Maths.standardDeviation(valuesWoCollisions,averageWoCollisions);

//TODO
        //int nrAgents = Environment.getInstance().getNumberOfAgents();
        //int nrPicks = Environment.getInstance().getNumberOfPicks();
      //  double numTimesOffload = numberTimesOffload/(this.values.length * nrAgents);

        //FileOperations.appendToTextFile("statistic_average_fitness_1.xls", e.getSource().getExperimentValues() + average +"\t" + stdDeviation + "\t" + averageWoCollisions + "\t"+ stdDeviationWoCollisions  +"\t" + collisionsAverage + "\t" + collisionStdDeviation +"\t"+ nrAgents + "\t"+ nrPicks + "\t"+ numTimesOffload  +"\r\n");
        FileOperations.appendToTextFile("statistic_average_fitness_1.xls", e.getSource().getExperimentValues() + average +"\t" + stdDeviation +"\r\n");
        Arrays.fill(values,0);
        Arrays.fill(valuesWoCollisions,0);
        Arrays.fill(allRunsCollisions,0);
        this.run=0;
        this.numberTimesOffload = 0;
    }




}
