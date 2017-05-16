package asteroids.model.programs.statements;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.programs.Program;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;

public class PrintStatement extends SingleExpressionStatement<Object> {
	
	public PrintStatement(Expression<Object> expression) {
		super(expression);
	}
	
	@Override
	public void execute(ProgramExecutor executor) throws IllegalMethodCallException {
		if (! (getExecutable() instanceof Program))
			throw new IllegalMethodCallException();
		Object evaluation = getExpression().evaluate(executor);
		System.out.println(evaluation.toString());
		executor.addToPrintList(evaluation);
	}

}
