package asteroids.model.programs;

import asteroids.model.exceptions.programExceptions.NoReturnException;
import asteroids.model.exceptions.programExceptions.ReturnException;
import asteroids.model.programs.statements.Statement;
import be.kuleuven.cs.som.annotate.*;

public class Function extends Executable {

	public Function(String name, Statement bodyStatement) throws IllegalArgumentException {
		super(bodyStatement);
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	
	public Object evaluate(ProgramExecutor executor, Object[] arguments) throws IllegalArgumentException {
		if (executor == null || arguments == null)
			throw new IllegalArgumentException();
		executor.getParameterContainer().createNewScope(arguments);
		executor.addExecutionScope();
		executor.getVariableContainer().createNewScope();
		try {
			executeBodyStatement(executor);
			throw new NoReturnException(); 
		}
		catch (ReturnException exc) {
			return exc.getReturnValue();
		}
		finally {
			executor.getParameterContainer().deleteCurrentScope();
			executor.removeExecutionScope();
			executor.getVariableContainer().deleteCurrentScope();
		}
	}
	
	@Basic @Override
	public Program getProgram() {
		return this.program;
	}
	
	public static boolean isValidProgram(Program program) {
		return program != null;
	}
	
	public boolean hasProperProgram() {
		return isValidProgram(getProgram()) && getProgram().getFunctionWithName(getName()) == this;
	}
	
	void setProgram(Program program) throws IllegalArgumentException {
		if (! isValidProgram(program) || (program.getFunctionWithName(getName()) != this && program.getFunctionWithName(getName()) != null)) 
			throw new IllegalArgumentException();
		this.program = program;			
	}
	
	private Program program;
	
	public String getName() {
		return this.name;
	}
	
	public static boolean isValidName(String name) {
		return (name != null) && (name != "");
	}
	
	private final String name;
}
