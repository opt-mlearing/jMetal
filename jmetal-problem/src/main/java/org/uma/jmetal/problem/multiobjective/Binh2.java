package org.uma.jmetal.problem.multiobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.Arrays;
import java.util.List;

/**
 * Class representing problem Binh2
 */
@SuppressWarnings("serial")
public class Binh2 extends AbstractDoubleProblem {

    /**
     * Constructor Creates a default instance of the Binh2 problem
     */
    public Binh2() {
        setNumberOfVariables(2);
        setNumberOfObjectives(2);
        setNumberOfConstraints(2);
        setName("Binh2");
        /* 构造上下边界 */
        List<Double> lowerLimit = Arrays.asList(0.0, 0.0);
        List<Double> upperLimit = Arrays.asList(5.0, 3.0);
        /* 设置变量的边界 */
        setVariableBounds(lowerLimit, upperLimit);
    }

    /**
     * Evaluate() method
     */
    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double[] fx = new double[solution.objectives().length];
        double[] x = new double[getNumberOfVariables()];
        /* 变量赋值 */
        for (int i = 0; i < getNumberOfVariables(); i++) {
            x[i] = solution.variables().get(i);
        }
        /* 计算目标函数 */
        fx[0] = 4.0 * x[0] * x[0] + 4 * x[1] * x[1];
        fx[1] = (x[0] - 5.0) * (x[0] - 5.0) + (x[1] - 5.0) * (x[1] - 5.0);
        /* 直接在solution#objectives字段中回填目标的计算取值 */
        solution.objectives()[0] = fx[0];
        solution.objectives()[1] = fx[1];
        /* 估算约束评价值 */
        this.evaluateConstraints(solution);
        return solution;
    }

    /**
     * EvaluateConstraints() method
     */
    public void evaluateConstraints(DoubleSolution solution) {
        double x0 = solution.variables().get(0);
        double x1 = solution.variables().get(1);
        /* 约束值评价 */
        solution.constraints()[0] = -1.0 * (x0 - 5) * (x0 - 5) - x1 * x1 + 25.0;
        solution.constraints()[1] = (x0 - 8) * (x0 - 8) + (x1 + 3) * (x1 + 3) - 7.7;
    }
}
