package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public class EqualityExpression extends BinaryExpression<Boolean, Object, Object> {
	
	public EqualityExpression(Expression<? extends Object> leftSubExpression,
			Expression<? extends Object> rightSubExpression) throws IllegalArgumentException {
		super(leftSubExpression, rightSubExpression);
	}

	@Override
	public Boolean evaluate(ProgramExecutor executor) {
		if (evaluateLeftSubExpression(executor).getClass() != evaluateRightSubExpression(executor).getClass())
			return false;
		return evaluateLeftSubExpression(executor) == evaluateRightSubExpression(executor);
	}

}
