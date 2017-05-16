package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class GetVxExpression extends UnaryExpression<Double, Entity> {
	
	public GetVxExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	public GetVxExpression(UnkownTypeExpression<?> entityExpression) {
		super(entityExpression.convertToType(Entity.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateSubExpression(executor).getVelocity().getxComponent();
	}
}
