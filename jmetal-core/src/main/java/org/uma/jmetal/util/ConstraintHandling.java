package org.uma.jmetal.util;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.errorchecking.Check;

import java.util.List;

public class ConstraintHandling {

    /**
     * 判断是否为合法解.
     *
     * @param solution
     * @param <S>
     * @return
     */
    public static <S extends Solution<?>> boolean isFeasible(S solution) {
        return numberOfViolatedConstraints(solution) == 0;
    }

    /**
     * Returns the number of constraints a solution violates.
     * 返回的是违法约束的次数.
     *
     * @param solution
     * @param <S>
     * @return
     */
    public static <S extends Solution<?>> int numberOfViolatedConstraints(S solution) {
        int result = 0;
        for (int i = 0; i < solution.constraints().length; i++) {
            if (solution.constraints()[i] < 0) {
                result++;
            }
        }
        return result;
    }

    /**
     * Returns the overall constraint violation degree of a solution.
     * 返回的是违反约束的程度.
     *
     * @param solution
     * @param <S>
     * @return
     */
    public static <S extends Solution<?>> double overallConstraintViolationDegree(S solution) {
        double overallConstraintViolation = 0.0;
        for (int i = 0; i < solution.constraints().length; i++) {
            if (solution.constraints()[i] < 0.0) {
                overallConstraintViolation += solution.constraints()[i];
            }
        }
        return overallConstraintViolation;
    }

    /**
     * Returns the ratio of feasible solutions in a solution list
     * 返回合法解在解集中的占比.
     *
     * @param solutions
     * @return
     */
    public static <S extends Solution<?>> double feasibilityRatio(List<S> solutions) {
        Check.collectionIsNotEmpty(solutions);
        double result = 0.0;
        for (Solution<?> solution : solutions) {
            if (isFeasible(solution)) {
                result += 1;
            }
        }
        return result / solutions.size();
    }

}
