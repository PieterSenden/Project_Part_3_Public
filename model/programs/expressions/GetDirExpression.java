package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public class GetDirExpression extends Expression<Double> {
		
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return executor.getShip().getOrientation();
	}
	
}
