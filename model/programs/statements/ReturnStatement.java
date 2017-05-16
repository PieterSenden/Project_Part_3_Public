package asteroids.model.programs.statements;

import asteroids.model.exceptions.programExceptions.ReturnException;
import asteroids.model.programs.ProgramExecutor;
import asteroids.model.programs.expressions.Expression;
import be.kuleuven.cs.som.annotate.*;

public class ReturnStatement extends SingleExpressionStatement<Object> {
	@Raw
	public ReturnStatement(Expression<? extends Object> valueToReturn) {
		super(valueToReturn);
	}
	
	@Override
	public void execute(ProgramExecutor executor) {
		throw new ReturnException(evaluateExpression(executor));
	}
	
}
