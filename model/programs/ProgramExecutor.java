package asteroids.model.programs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.programExceptions.HoldException;
import asteroids.model.representation.Ship;
import be.kuleuven.cs.som.annotate.*;

/**
 * 
 * @author Joris Ceulemans & Pieter Senden
 * @version 3.0
 * 
 * @invar	| hasProperShip()
 * @invar	| isValidProgram(getProgram())
 * @invar	| isValidExecutionStack(getExecutionStack())
 *
 */
public class ProgramExecutor {
	
	@Raw
	public ProgramExecutor(Program program) throws IllegalArgumentException {
		if (!isValidProgram(program))
			throw new IllegalArgumentException();
		this.program = program;
		this.executionStack.push(new ArrayList<Integer>());
	}
	
	@Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	public void terminate() {
		if (!isTerminated()) {
			isTerminated = true;
			getShip().setProgramExecutor(null);
			setShip(null);
		}
	}
	
	private boolean isTerminated;
	
	private void reset() {
		resetExecutionStack();
		resetRemainingExecutionTime();
		resetPrintList();
		resetVariableContainer();
		resetParameterContainer(); // In theory, this is not necessary, but it is a safety measure.
	}
	
	@Basic
	public Ship getShip() {
		return this.ship;
	}
	
	public boolean canHaveAsShip(Ship ship) {
		return isTerminated() ? (ship == null) : (ship != null && !ship.isTerminated());
	}
	
	public boolean hasProperShip() {
		return canHaveAsShip(getShip()) && (getShip().getProgramExecutor() == this);
	}
	
	public void setShip(Ship ship) throws IllegalMethodCallException, IllegalArgumentException {
		if (!canHaveAsShip(ship))
			throw new IllegalArgumentException();
		if ((ship != null) && (ship.getProgramExecutor() != this))
			throw new IllegalMethodCallException();
		this.ship = ship;
	}
	
	private Ship ship;
	
	
	public int getCurrentExecutionListLength() {
		return getExecutionStack().peek().size();
	}
	
	public void setExecutionPositionAt(int depth, int position) throws IllegalArgumentException {
		if (depth < 0) 
			throw new IllegalArgumentException();
		List<Integer> currentExecutionScope = getExecutionStack().peek();
		if (currentExecutionScope.size() < depth)
			throw new IllegalArgumentException();
		if (currentExecutionScope.size() == depth)
			currentExecutionScope.add(position);
		else
			currentExecutionScope.set(depth, position);
	}
	
	public int getExecutionPositionAt(int depth) throws IndexOutOfBoundsException {
		return getExecutionStack().peek().get(depth);
	}
	
	public boolean isValidExecutionStack(Stack<List<Integer>> executionStack) {
		if ((executionStack == null) || (executionStack.size() == 0))
			return false;
		for (List<Integer> list : executionStack) {
			if (list == null)
				return false;
		}
		return true;
	}
	
	public void removeExecutionPosition() throws IllegalMethodCallException {
		List<Integer> currentExecutionScope = getExecutionStack().peek();
		if (currentExecutionScope.size() == 0)
			throw new IllegalMethodCallException();
		currentExecutionScope.remove(currentExecutionScope.size() - 1);
	}
	
	public void addExecutionScope() {
		getExecutionStack().push(new ArrayList<Integer>());
	}
	
	public void removeExecutionScope() throws IllegalMethodCallException {
		if (getExecutionStack().size() <= 1)
			throw new IllegalMethodCallException();
		getExecutionStack().pop();
	}
	
	private void resetExecutionStack() {
		executionStack = new Stack<>();
		executionStack.push(new ArrayList<>());
	}
	@Basic @Model
	private Stack<List<Integer>> getExecutionStack() {
		return this.executionStack;
	}
	
	private Stack<List<Integer>> executionStack = new Stack<>();
	
	@Basic @Raw
	public double getRemainingExecutionTime() {
		return remainingExecutionTime;
	}
	
	public static boolean isValidRemainingExecutionTime(double duration) {
		return (duration >= 0) && Double.isFinite(duration);
	}
	
	private void setRemainingExecutionTime(double newRemainingTime) throws IllegalArgumentException {
		if (!isValidRemainingExecutionTime(newRemainingTime))
			throw new IllegalArgumentException();
		this.remainingExecutionTime = newRemainingTime;
	}
	
	private void increaseRemainingExecutionTime(double timeToIncrease) throws IllegalArgumentException {
		if (timeToIncrease < 0)
			throw new IllegalArgumentException();
		setRemainingExecutionTime(getRemainingExecutionTime() + timeToIncrease);
	}
	
	public void decreaseRemainingTime(double timeToDecrease) throws IllegalArgumentException {
		if (timeToDecrease < 0)
			throw new IllegalArgumentException();
		setRemainingExecutionTime(getRemainingExecutionTime() - timeToDecrease);
	}
	
	private void resetRemainingExecutionTime() {
		setRemainingExecutionTime(0);
	}
	
	private double remainingExecutionTime;
	
	@Basic
	public List<Object> getPrintList() {
		return new ArrayList<>(printList);
	}
	
	public void addToPrintList(Object item) {
		printList.add(item);
	}
	
	private void resetPrintList() {
		printList = new ArrayList<>();
	}
	
	private List<Object> printList = new ArrayList<>();
	
	public VariableContainer getVariableContainer() {
		return this.variableContainer;
	}
	
	private void resetVariableContainer() {
		getVariableContainer().reset();
	}
	
	private final VariableContainer variableContainer = new VariableContainer(this);
	
	public ParameterContainer getParameterContainer() {
		return this.parameterContainer;
	}
	
	private void resetParameterContainer() {
		this.parameterContainer = new ParameterContainer();
	}
	
	private ParameterContainer parameterContainer = new ParameterContainer();
	
	@Basic
	public Program getProgram() {
		return this.program;
	}
	
	public static boolean isValidProgram(Program program) {
		return program != null;
	}
	
	public List<Object> executeProgram(double duration) {
		increaseRemainingExecutionTime(duration);
		if (isProgramFinished())
			this.reset();
		try {
			setProgramFinished(false);
			program.executeBodyStatement(this);
			setProgramFinished(true);
			return getPrintList();
		}
		catch (HoldException exc) {
			//The program did not terminate, but an action had to be executed for which not enough time was available.
			return null;
		}
	}
	
	private final Program program;
	
	@Basic @Raw
	public boolean isProgramFinished() {
		return programFinished;
	}
	
	private void setProgramFinished(boolean flag) {
		programFinished = flag;
	}
	
	/**
	 * A variable registering whether the program this executor is executing is finished or not. In this way, it is possible to restart a program
	 * that is finished.
	 */
	private boolean programFinished;
}