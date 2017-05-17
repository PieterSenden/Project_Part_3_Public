package asteroids.facade;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import asteroids.model.representation.*;
import asteroids.model.programs.Program;
import asteroids.part2.CollisionListener;
import asteroids.part3.facade.IFacade;
import asteroids.part3.programs.IProgramFactory;
import asteroids.util.ModelException;

public class Facade implements IFacade {

	@Override
	public double getTimeCollisionBoundary(Object object) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getPositionCollisionBoundary(Object object) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTimeCollisionEntity(Object entity1, Object entity2) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getPositionCollisionEntity(Object entity1, Object entity2) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbStudentsInTeam() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction,
			double mass) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void terminateShip(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTerminatedShip(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getShipMass(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public World getShipWorld(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShipThrusterActive(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setThrusterActive(Ship ship, boolean active) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getShipAcceleration(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void terminateBullet(Bullet bullet) throws ModelException {
		int i = 1 + 1;
		
	}

	@Override
	public boolean isTerminatedBullet(Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double[] getBulletPosition(Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getBulletVelocity(Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getBulletRadius(Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBulletMass(Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public World getBulletWorld(Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ship getBulletShip(Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ship getBulletSource(Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World createWorld(double width, double height) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void terminateWorld(World world) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTerminatedWorld(World world) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double[] getWorldSize(World world) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	// VANAF HIER NAAR BENEDEN IMPLEMENTEREN
	@Override
	public Set<? extends Ship> getWorldShips(World world) throws ModelException {
		try {
			return world.getSpecificEntities(Ship.class);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public Set<? extends Bullet> getWorldBullets(World world) throws ModelException {
		try {
			return world.getSpecificEntities(Bullet.class);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void addShipToWorld(World world, Ship ship) throws ModelException {
		try {
			world.addEntity(ship);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
		
	}

	@Override
	public void removeShipFromWorld(World world, Ship ship) throws ModelException {
		try {
			world.removeEntity(ship);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
		
	}

	@Override
	public void addBulletToWorld(World world, Bullet bullet) throws ModelException {
		try {
			world.addEntity(bullet);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException {
		try {
			world.removeEntity(bullet);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
		
	}

	@Override
	public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException {
		try {
			return ship.getMagazine();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public int getNbBulletsOnShip(Ship ship) throws ModelException {
		try {
			return ship.getNbOfBulletsInMagazine();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException {
		try {
			ship.loadBullet(bullet);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException {
		try {
			ship.loadBullets((Bullet[])bullets.toArray());
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireBullet(Ship ship) throws ModelException {
		try {
			ship.fireBullet();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
		
	}

	@Override
	public double getTimeNextCollision(World world) throws ModelException {
		try {
			return world.getTimeToFirstCollision();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double[] getPositionNextCollision(World world) throws ModelException {
		try {
			return world.getPositionFirstCollision().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException {
		try {
			world.evolve(dt, collisionListener);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public Object getEntityAt(World world, double x, double y) throws ModelException {
		try {
			return world.getEntityAt(new Position(x, y));
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public Set<? extends Object> getEntities(World world) throws ModelException {
		try {
			return world.getEntities();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public Ship createShip() throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius,
			double orientation) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getShipPosition(Ship ship) throws ModelException {
		try {
			return ship.getPosition().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double[] getShipVelocity(Ship ship) throws ModelException {
		try {
			return ship.getVelocity().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double getShipRadius(Ship ship) throws ModelException {
		try {
			return ship.getRadius();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double getShipOrientation(Ship ship) throws ModelException {
		try {
			return ship.getOrientation();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void turn(Ship ship, double angle) throws ModelException {
		try {
			ship.turn(angle);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double getDistanceBetween(Ship ship1, Ship ship2)
			throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getTimeToCollision(Ship ship1, Ship ship2)
			throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getCollisionPosition(Ship ship1, Ship ship2)
			throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends Asteroid> getWorldAsteroids(World world) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAsteroidToWorld(World world, Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAsteroidFromWorld(World world, Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<? extends Planetoid> getWorldPlanetoids(World world) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlanetoidToWorld(World world, Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePlanetoidFromWorld(World world, Planetoid planetoid)
			throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void terminateAsteroid(Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTerminatedAsteroid(Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double[] getAsteroidPosition(Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getAsteroidVelocity(Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getAsteroidRadius(Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAsteroidMass(Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public World getAsteroidWorld(Asteroid asteroid) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Planetoid createPlanetoid(double x, double y, double xVelocity, double yVelocity, double radius,
			double totalTraveledDistance) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void terminatePlanetoid(Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTerminatedPlanetoid(Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double[] getPlanetoidPosition(Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getPlanetoidVelocity(Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPlanetoidRadius(Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPlanetoidMass(Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPlanetoidTotalTraveledDistance(Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public World getPlanetoidWorld(Planetoid planetoid) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Program getShipProgram(Ship ship) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadProgramOnShip(Ship ship, Program program) throws ModelException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Object> executeProgram(Ship ship, double dt) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProgramFactory<?, ?, ?, ? extends Program> createProgramFactory() throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

}
