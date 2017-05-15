package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public class LessThanExpression extends BinaryExpression<Boolean, Double, Double> {

	public LessThanExpression(Expression<? extends Double> leftSubExpression,
			Expression<? extends Double> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression);
	}

	@Override
	public Boolean evaluate(ProgramExecutor executor) {
		return evaluateLeftSubExpression(executor) < evaluateRightSubExpression(executor);
	}

}
