package asteroids.model.programs;

import be.kuleuven.cs.som.annotate.*;

public class IfThenElseStatement extends SingleExpressionStatement<Boolean> implements ComposedStatement {
	
	public IfThenElseStatement(Expression<Boolean> condition, Statement ifStatement, Statement elseStatement) throws IllegalArgumentException {
		super(condition);
		if (! canHaveAsIfStatement(ifStatement) || ! canHaveAsElseStatement(elseStatement))
			throw new IllegalArgumentException();
		this.ifStatement = ifStatement;
		this.elseStatement = elseStatement;
	}
	
	@Override
	public void execute() {
		if (evaluateExpression() || getExecutionPosition() == ExecutionPosition.IF) {
			setExecutionPosition(ExecutionPosition.IF);
			getIfStatement().execute();
			setExecutionPosition(ExecutionPosition.CONDITION);
		}
		else if (getElseStatement() != null || getExecutionPosition() == ExecutionPosition.ELSE) {
			setExecutionPosition(ExecutionPosition.ELSE);			
			getElseStatement().execute();
			setExecutionPosition(ExecutionPosition.CONDITION);
		}
	}
		
	public ExecutionPosition getExecutionPosition() {
		return this.executionPosition;
	}
	
	private void setExecutionPosition(ExecutionPosition executionPosition) {
		this.executionPosition = executionPosition;
	}
	
	@Value
	enum ExecutionPosition {
		CONDITION, IF, ELSE
	}
	private ExecutionPosition executionPosition = ExecutionPosition.CONDITION;
	
	
	@Override
	public boolean hasAsSubStatement(Statement statement) {
		if (hasAsEnclosedStatement(statement))
			return true;
		if (getElseStatement() instanceof ComposedStatement && ((ComposedStatement)getElseStatement()).hasAsSubStatement(statement))
			return true;
		if (getIfStatement() instanceof ComposedStatement && ((ComposedStatement)getIfStatement()).hasAsSubStatement(statement))
			return true;
		return false;
	}

	@Override
	public boolean hasAsEnclosedStatement(Statement statement) {
		return (getIfStatement() == statement) || (getElseStatement() == statement);
	}

	@Override
	public boolean hasProperEnclosedStatements() {
		if (! canHaveAsIfStatement(ifStatement) || ifStatement.getEnclosingStatement() != this)
			return false;
		if (! canHaveAsElseStatement(elseStatement) || elseStatement.getEnclosingStatement() != this)
			return false;
		return true;
	}
	
	@Override
	public boolean canHaveAsEnclosedStatement(Statement statement) {
		return canHaveAsIfStatement(statement) || canHaveAsElseStatement(statement);
	}
	
	public boolean canHaveAsIfStatement(Statement statement) {
		return ComposedStatement.super.canHaveAsEnclosedStatement(statement);
	}
	
	public boolean canHaveAsElseStatement(Statement statement) {
		return (statement == null) || ComposedStatement.super.canHaveAsEnclosedStatement(statement);
	}
	
	@Basic
	public Statement getIfStatement() {
		return this.ifStatement;
	}
	
	@Basic
	public Statement getElseStatement() {
		return this.elseStatement;
	}
	
	private final Statement ifStatement, elseStatement;	
}
