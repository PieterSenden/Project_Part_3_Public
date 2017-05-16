package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class GetYExpression extends UnaryExpression<Double, Entity> {
	
	public GetYExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	public GetYExpression(UnknownTypeExpression<?> entityExpression) {
		super(entityExpression.convertToType(Entity.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateSubExpression(executor).getPosition().getyComponent();
	}
}
