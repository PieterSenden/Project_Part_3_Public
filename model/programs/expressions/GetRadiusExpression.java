package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class GetRadiusExpression extends UnaryExpression<Double, Entity> {
	
	public GetRadiusExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	public GetRadiusExpression(UnkownTypeExpression<?> entityExpression) {
		super(entityExpression.convertToType(Entity.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateSubExpression(executor).getRadius();
	}
	
}
