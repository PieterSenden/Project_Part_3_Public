package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class GetVxExpression extends UnaryExpression<Double, Entity> {
	
	public GetVxExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateSubExpression(executor).getVelocity().getxComponent();
	}
}
