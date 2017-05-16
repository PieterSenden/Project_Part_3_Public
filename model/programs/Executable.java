package asteroids.model.programs;

import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class Executable {
	
	public Executable(Statement bodyStatement) {
		if (! isValidBodyStatement(bodyStatement))
			throw new IllegalArgumentException();
		this.bodyStatement = bodyStatement;
		bodyStatement.setEric(this);
	}
	
	public abstract Program getProgram();
	
	@Basic
	public Statement getBodyStatement() {
		return this.bodyStatement;
	}
	
	public static boolean isValidBodyStatement(Statement bodyStatement) {
		return bodyStatement != null;
	}
	
	public boolean hasProperBodyStatement() {
		return isValidBodyStatement(getBodyStatement()) && getBodyStatement().getEric() == this;
	}
	
	public void executeBodyStatement(ProgramExecutor executor) {
		getBodyStatement().execute(executor);
	}
	
	private final Statement bodyStatement;
}
