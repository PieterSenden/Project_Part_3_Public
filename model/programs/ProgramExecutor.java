package asteroids.model.programs;

import asteroids.model.representation.Ship;
import be.kuleuven.cs.som.annotate.Basic;

public class ProgramExecutor {
	
	@Basic
	public Ship getShip() {
		return this.ship;
	}
	
	private Ship ship;
	
	//TODO Evt stack van lists omwille van statements in function calls
	public int getExecutionStackDepth() {
		//TODO
		return 0;
	}
	
	public void setExecutionPositionAt(int depth, int position) {
		//TODO
	}
	
	public int getExecutionPositionAt(int depth) {
		//TODO
		return 0;
	}
	
	public void removeExecutionPosition() {
		//TODO
	}
	
	public double getRemainingTime() {
		//TODO
		return 0;
	}
	
	public void decreaseRemainingTime(double timeToDecrease) {
		//TODO
	}
	
	public void addToPrintList(Object item) {
		//TODO
	}
	
	public VariableContainer getVariableContainer() {
		//TODO
		return null;
	}
	
	@Basic
	public Program getProgram() {
		return this.program;
	}
	
	public static boolean isValidProgram(Program program) {
		return program != null;
	}
	
	private Program program;
	
}
