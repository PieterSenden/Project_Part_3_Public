package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public class MultiplicationExpression extends BinaryExpression<Double, Double, Double> {

	public MultiplicationExpression(Expression<? extends Double> leftSubExpression,
			Expression<? extends Double> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression);
	}
	
	public MultiplicationExpression(Expression<? extends Double> leftSubExpression,
			UnknownTypeExpression<?> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression.convertToType(Double.class));
	}
	
	public MultiplicationExpression(UnknownTypeExpression<?> leftSubExpression,
			Expression<? extends Double> rightSubExpression) throws IllegalArgumentException {
		this(rightSubExpression, leftSubExpression);
	}
	
	public MultiplicationExpression(UnknownTypeExpression<?> leftSubExpression,
			UnknownTypeExpression<? extends Double> rightSubExpression) {
		super(leftSubExpression.convertToType(Double.class), rightSubExpression.convertToType(Double.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateLeftSubExpression(executor) * evaluateRightSubExpression(executor);
	}
	
}
