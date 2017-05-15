package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public class MultiplicationExpression extends BinaryExpression<Double, Double, Double> {

	public MultiplicationExpression(Expression<? extends Double> leftSubExpression,
			Expression<? extends Double> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression);
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateLeftSubExpression(executor) * evaluateRightSubExpression(executor);
	}
	
}
