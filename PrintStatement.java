package asteroids.model.programs;

import asteroids.model.exceptions.IllegalMethodCallException;

public class PrintStatement extends SingleExpressionStatement<Object> {
	
	public PrintStatement(Expression<Object> expression) {
		super(expression);
	}
	
	@Override
	public void execute() throws IllegalMethodCallException {
		if (! (getExecutable() instanceof Program))
			throw new IllegalMethodCallException();
		Object evaluation = getExpression().evaluate();
		System.out.println(evaluation.toString());
		((Program)getExecutable()).addToPrintList(evaluation);
	}

}
