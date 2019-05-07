package org.uma.jmetal.algorithm.impl;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

import java.util.List;

/**
 * Abstract class representing a local search algorithm
 * @param <S> Solution
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public abstract class AbstractLocalSearch<S extends Solution<?>>  implements Algorithm<S>{
  private Problem<S> problem ;
  public Problem<S> getProblem() {
    return problem ;
  }

  private MutationOperator<S> mutationOperator ;

  public MutationOperator<S> getMutationOperator() {
    return mutationOperator;
  }

  public AbstractLocalSearch(Problem<S> problem) {
    this.problem = problem ;
  }

  protected S bestSolution ;

  protected abstract void initProgress();

  protected abstract void updateProgress();

  protected abstract boolean isStoppingConditionReached();

  protected abstract S improve(S solution) ;

  @Override public abstract S getResult();

  @Override public void run() {
    initProgress();
    while (!isStoppingConditionReached()) {
      bestSolution = improve(bestSolution) ;

      updateProgress();
    }
  }

  public abstract long getComputingTime() ;

  public abstract int getNumberOfEvaluations() ;
}
