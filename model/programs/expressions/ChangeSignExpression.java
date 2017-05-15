package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public class ChangeSignExpression extends UnaryExpression<Double, Double> {
	
	public ChangeSignExpression(Expression<? extends Double> subExpression) throws IllegalArgumentException {
		super(subExpression);
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return -evaluateSubExpression(executor); 
	}
}
