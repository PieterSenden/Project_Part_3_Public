package asteroids.model.programs.expressions;

import asteroids.model.programs.ProgramExecutor;

public class ConstantExpression extends Expression<Double> {
	
	public ConstantExpression(double value) throws IllegalArgumentException {
		if (! isValidValue(value))
			throw new IllegalArgumentException();
		this.value = value;
	}
	
	@Override
	public Double evaluate(ProgramExecutor executor) {
		return value;
	}
	
	public static boolean isValidValue(double value) {
		return Double.isFinite(value); 
	}
	
	private final double value;
	
}
