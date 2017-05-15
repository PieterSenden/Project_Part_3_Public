package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class GetVyExpression extends UnaryExpression<Double, Entity> {
	
	public GetVyExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateSubExpression(executor).getVelocity().getyComponent();
	}
}