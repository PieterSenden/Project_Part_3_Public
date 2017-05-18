package asteroids.model.programs;

import java.util.List;

import javax.print.attribute.standard.PrinterStateReasons;

import asteroids.model.programs.expressions.*;
import asteroids.model.programs.statements.*;
import asteroids.model.representation.*;
import asteroids.model.representation.Entity;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;

@SuppressWarnings("all")
public class ProgramFactory implements IProgramFactory<Expression, Statement, Function, Program> {

	@Override
	public Program createProgram(List<Function> functions, Statement main) {
		return new Program(main, functions);
	}

	@Override
	public Function createFunctionDefinition(String functionName, Statement body, SourceLocation sourceLocation) {
		return new Function(functionName, body);
	}

	@Override
	public Statement createAssignmentStatement(String variableName, Expression value,
			SourceLocation sourceLocation) {
		return new AssignmentStatement(variableName, value);
	}

	@Override
	public Statement createWhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		return new WhileStatement(condition, body);
	}

	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation) {
		return new BreakStatement();
	}

	@Override
	public Statement createReturnStatement(Expression value, SourceLocation sourceLocation) {
		return new ReturnStatement(value);
	}

	@Override
	public Statement createIfStatement(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		return new IfThenElseStatement(condition, ifBody, elseBody);
	}

	@Override
	public Statement createPrintStatement(Expression value, SourceLocation sourceLocation) {
		return new PrintStatement(value);
	}

	@Override
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		return new SequenceStatement((Statement[])statements.toArray());
	}

	@Override
	public Expression createReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		return new ReadVariableExpression<>(variableName);
	}

	@Override
	public Expression createReadParameterExpression(String parameterName, SourceLocation sourceLocation) {
		return new ReadParameterExpression<>(parameterName);
	}

	@Override
	public Expression createFunctionCallExpression(String functionName, List<Expression> actualArgs,
			SourceLocation sourceLocation) {
		return new FunctionCallExpression<>(functionName, (Expression[])actualArgs.toArray());
	}

	@Override
	public Expression<Double> createChangeSignExpression(Expression expression, SourceLocation sourceLocation) {
		return new ChangeSignExpression(expression);
	}

	@Override
	public Expression<Boolean> createNotExpression(Expression expression, SourceLocation sourceLocation) {
		return new NotExpression(expression);
	}

	@Override
	public Expression<Double> createDoubleLiteralExpression(double value, SourceLocation location) {
		return new ConstantExpression(value);
	}

	@Override
	public Expression<Entity> createNullExpression(SourceLocation location) {
		return new NullExpression();
	}

	@Override
	public Expression<Entity> createSelfExpression(SourceLocation location) {
		return new SelfExpression();
	}

	@Override
	public Expression<Entity> createShipExpression(SourceLocation location) {
		return new NearestEntityExpression(Ship.class);
	}

	@Override
	public Expression<Entity> createAsteroidExpression(SourceLocation location) {
		return new NearestEntityExpression(Asteroid.class);
	}

	@Override
	public Expression<Entity> createPlanetoidExpression(SourceLocation location) {
		return new NearestEntityExpression(Planetoid.class);
	}

	@Override
	public Expression<Entity> createBulletExpression(SourceLocation location) {
		return new NearestEntityExpression(Bullet.class);
	}

	@Override
	public Expression<Entity> createPlanetExpression(SourceLocation location) {
		return new NearestEntityExpression(MinorPlanet.class);
	}

	@Override
	public Expression<Entity> createAnyExpression(SourceLocation location) {
		return new AnyExpression();
	}

	@Override
	public Expression createGetXExpression(Expression e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetYExpression(Expression e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetVXExpression(Expression e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetVYExpression(Expression e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetRadiusExpression(Expression e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createLessThanExpression(Expression e1, Expression e2, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createEqualityExpression(Expression e1, Expression e2, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createAdditionExpression(Expression e1, Expression e2, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createMultiplicationExpression(Expression e1, Expression e2, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createSqrtExpression(Expression e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetDirectionExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createThrustOnStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createThrustOffStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createFireStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createTurnStatement(Expression angle, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSkipStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

}
