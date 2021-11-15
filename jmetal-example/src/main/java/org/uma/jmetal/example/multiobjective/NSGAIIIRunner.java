package org.uma.jmetal.example.multiobjective;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIII;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.NSGAIIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaiii.util.ReferencePoint;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.lab.visualization.plot.PlotFront;
import org.uma.jmetal.lab.visualization.plot.impl.Plot2D;
import org.uma.jmetal.lab.visualization.plot.impl.Plot3D;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.errorchecking.JMetalException;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.legacy.front.impl.ArrayFront;

import java.util.List;

/**
 * Class to configure and run the NSGA-III algorithm
 */
public class NSGAIIIRunner extends AbstractAlgorithmRunner {
    /**
     * @param args Command line arguments.
     * @throws java.io.IOException
     * @throws SecurityException
     * @throws ClassNotFoundException Usage: three options -
     *                                org.uma.jmetal.runner.multiobjective.nsgaii.NSGAIIIRunner -
     *                                org.uma.jmetal.runner.multiobjective.nsgaii.NSGAIIIRunner problemName -
     *                                org.uma.jmetal.runner.multiobjective.nsgaii.NSGAIIIRunner problemName paretoFrontFile
     */
    public static void main(String[] args) throws JMetalException {
        Problem<DoubleSolution> problem;
        Algorithm<List<DoubleSolution>> algorithm;
        CrossoverOperator<DoubleSolution> crossover;
        MutationOperator<DoubleSolution> mutation;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;

        String problemName = "org.uma.jmetal.problem.multiobjective.dtlz.DTLZ4_2D";

        problem = ProblemUtils.loadProblem(problemName);

        double crossoverProbability = 0.9;
        double crossoverDistributionIndex = 30.0;
        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        double mutationDistributionIndex = 20.0;
        mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);

        selection = new BinaryTournamentSelection<DoubleSolution>();

        algorithm =
                new NSGAIIIBuilder<>(problem)
                        .setCrossoverOperator(crossover)
                        .setMutationOperator(mutation)
                        .setSelectionOperator(selection)
                        .setMaxIterations(10000)
                        .setNumberOfDivisions(12)
                        .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

        List<DoubleSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        new SolutionListOutput(population)
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.csv", ","))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.csv", ","))
                .print();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        JMetalLogger.logger.info("Objectives values have been written to file FUN.csv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.csv");

        if (algorithm instanceof NSGAIII) {
            List<ReferencePoint<DoubleSolution>> referencePointsCopy = ((NSGAIII<DoubleSolution>) algorithm).getReferencePointsCopy();
            double[][] matrix = new double[referencePointsCopy.size()][];
            for (int i = 0; i < referencePointsCopy.size(); i++) {
                ReferencePoint<DoubleSolution> referencePoint = referencePointsCopy.get(i);
                double[] value = new double[referencePoint.position.size()];
                int j = 0;
                for (Double aDouble : referencePoint.position) {
                    value[j++] = aDouble;
                }
                matrix[i] = value;
            }
            PlotFront plot1 = new Plot2D(matrix, "referencePointsCopy" + " (NSGA-III)");
            plot1.plot();
        }
        PlotFront plot = new Plot2D(new ArrayFront(population).getMatrix(), problem.getName() + " (NSGA-III)");
        plot.plot();
        //PlotFront plot = new PlotSmile(new ArrayFront(population).getMatrix(), problem.getName() + " (NSGA-III)") ;
        //plot.plot();
    }
}
