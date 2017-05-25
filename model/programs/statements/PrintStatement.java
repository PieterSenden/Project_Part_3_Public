package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.Program;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;

/**
 * A class representing a print statement.
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 */
public class PrintStatement extends SingleExpressionStatement<Object> {
	
	public PrintStatement(Expression<Object> expression) {
		super(expression);
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException {
		if (! (getExecutable() instanceof Program))
			throw new IllegalMethodCallException();
		Object evaluation = evaluateExpression(executor);
		if (evaluation == null)
			System.out.println("null");
		else
			System.out.println(evaluation.toString());
		executor.addToPrintList(evaluation);
	}

}
