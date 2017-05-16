package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;
import asteroids.model.representation.Entity;

public class GetXExpression extends UnaryExpression<Double, Entity> {
	
	public GetXExpression(Expression<? extends Entity> entityExpression) {
		super(entityExpression);
	}
	
	public GetXExpression(UnkownTypeExpression<?> entityExpression) {
		super(entityExpression.convertToType(Entity.class));
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return evaluateSubExpression(executor).getPosition().getxComponent();
	}
}
