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
	
	/**
	 * Return the shortest time in which the given entity will collide with the
	 * boundaries of its world.
	 */
	@Override
	public double getTimeCollisionBoundary(Object object) throws ModelException {
		try {
			return ((Entity)object).getTimeToCollisionWithBoundary();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the first position at which the given entity will collide with the
	 * boundaries of its world.
	 */
	@Override
	public double[] getPositionCollisionBoundary(Object object) throws ModelException {
		try {
			return ((Entity)object).getCollisionWithBoundaryPosition().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the shortest time in which the first entity will collide with the
	 * second entity.
	 */
	@Override
	public double getTimeCollisionEntity(Object entity1, Object entity2) throws ModelException {
		try {
			return Entity.getTimeToCollision((Entity)entity1, (Entity)entity2);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the first position at which the first entity will collide with the
	 * second entity.
	 */
	@Override
	public double[] getPositionCollisionEntity(Object entity1, Object entity2) throws ModelException {
		try {
			return Entity.getCollisionPosition((Entity)entity1, (Entity)entity2).getAsArray();
		}
		catch (NullPointerException exc) {
			return null;
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the number of students in your team (used to adapt the tests for
	 * single-student groups).
	 * 
	 * @return 1 or 2
	 */
	@Override
	public int getNbStudentsInTeam() {
		return 2;
	}
	
	/**
	 * Create a new non-null ship with the given position, velocity, radius,
	 * direction and mass.
	 * 
	 * The thruster of the new ship is initially inactive. The ship is not
	 * located in a world.
	 */
	@Override
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction,
			double mass) throws ModelException {
		try {
			return new Ship(x, y, xVelocity, yVelocity, radius, direction, mass);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Terminate <code>ship</code>.
	 */
	@Override
	public void terminateShip(Ship ship) throws ModelException {
		try {
			ship.terminate();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Check whether <code>ship</code> is terminated.
	 */
	@Override
	public boolean isTerminatedShip(Ship ship) throws ModelException {
		try {
			return ship.isTerminated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the total mass of <code>ship</code> (i.e., including bullets
	 * loaded onto the ship).
	 */
	@Override
	public double getShipMass(Ship ship) throws ModelException {
		try {
			return ship.getTotalMass();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the world of <code>ship</code>.
	 */
	@Override
	public World getShipWorld(Ship ship) throws ModelException {
		try {
			return ship.getWorld();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return whether <code>ship</code>'s thruster is active.
	 */
	@Override
	public boolean isShipThrusterActive(Ship ship) throws ModelException {
		try {
			return ship.hasThrusterActivated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Enables or disables <code>ship</code>'s thruster depending on the value
	 * of the parameter <code>active</code>.
	 */
	@Override
	public void setThrusterActive(Ship ship, boolean active) throws ModelException {
		try {
			ship.setThrust(active);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the acceleration of <code>ship</code>.
	 */
	@Override
	public double getShipAcceleration(Ship ship) throws ModelException {
		try {
			return ship.getAcceleration();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Create a new non-null bullet with the given position, velocity and
	 * radius,
	 * 
	 * The bullet is not located in a world nor loaded on a ship.
	 */
	@Override
	public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException {
		try {
			return new Bullet(x, y, xVelocity, yVelocity, radius);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Terminate <code>bullet</code>.
	 */
	@Override
	public void terminateBullet(Bullet bullet) throws ModelException {
		try {
			bullet.terminate();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Check whether <code>bullet</code> is terminated.
	 */
	@Override
	public boolean isTerminatedBullet(Bullet bullet) throws ModelException {
		try {
			return bullet.isTerminated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the position of <code>bullet</code> as an array containing the
	 * x-coordinate, followed by the y-coordinate.
	 */
	@Override
	public double[] getBulletPosition(Bullet bullet) throws ModelException {
		try {
			return bullet.getPosition().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the velocity of <code>bullet</code> as an array containing the
	 * velocity along the X-axis, followed by the velocity along the Y-axis.
	 */
	@Override
	public double[] getBulletVelocity(Bullet bullet) throws ModelException {
		try {
			return bullet.getVelocity().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the radius of <code>bullet</code>.
	 */
	@Override
	public double getBulletRadius(Bullet bullet) throws ModelException {
		try {
			return bullet.getRadius();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the mass of <code>bullet</code>.
	 */
	@Override
	public double getBulletMass(Bullet bullet) throws ModelException {
		try {
			return bullet.getMass();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the world in which <code>bullet</code> is positioned.
	 * 
	 * This method must return null if a bullet is not positioned in a world, or
	 * if it is positioned on a ship.
	 */
	@Override
	public World getBulletWorld(Bullet bullet) throws ModelException {
		try {
			return bullet.getWorld();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the ship in which <code>bullet</code> is positioned.
	 * 
	 * This method must return null if a bullet is not positioned on a ship.
	 */
	@Override
	public Ship getBulletShip(Bullet bullet) throws ModelException {
		try {
			if (bullet.getShip() != null && bullet.getShip().hasLoadedInMagazine(bullet))
				return bullet.getShip();
			return null;
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the ship that fired <code>bullet</code>.
	 */
	@Override
	public Ship getBulletSource(Bullet bullet) throws ModelException {
		try {
			if (bullet.getWorld() != null)
				return bullet.getShip();
			return null;
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Create a new world with the given <code>width</code> and
	 * <code>height</code>.
	 */
	@Override
	public World createWorld(double width, double height) throws ModelException {
		try {
			return new World(height, width);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Terminate <code>world</code>.
	 */
	@Override
	public void terminateWorld(World world) throws ModelException {
		try {
			world.terminate();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Check whether <code>world</code> is terminated.
	 */
	@Override
	public boolean isTerminatedWorld(World world) throws ModelException {
		try {
			return world.isTerminated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return the size of <code>world</code> as an array containing the width,
	 * followed by the height.
	 */
	@Override
	public double[] getWorldSize(World world) throws ModelException {
		try {
			return world.getDimensions();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}
	
	/**
	 * Return all ships located within <code>world</code>.
	 */
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
		try {
			ship.removeBullet(bullet);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
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

	@Override @Deprecated
	public Ship createShip() throws ModelException {
		return null;
	}

	@Override @Deprecated
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius,
			double orientation) throws ModelException {
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
		
		return 0;
	}

	@Override
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException {
		try {
			return Entity.overlap(ship1, ship2);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double getTimeToCollision(Ship ship1, Ship ship2)
			throws ModelException {
		return getTimeCollisionEntity(ship1, ship2);
	}

	@Override
	public double[] getCollisionPosition(Ship ship1, Ship ship2)
			throws ModelException {
		return getPositionCollisionEntity(ship1, ship2);
	}

	@Override
	public Set<? extends Asteroid> getWorldAsteroids(World world) throws ModelException {
		try {
			return world.getSpecificEntities(Asteroid.class)
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void addAsteroidToWorld(World world, Asteroid asteroid) throws ModelException {
		try {
			world.addEntity(asteroid);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
		
	}

	@Override
	public void removeAsteroidFromWorld(World world, Asteroid asteroid) throws ModelException {
		try {
			world.removeEntity(asteroid);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public Set<? extends Planetoid> getWorldPlanetoids(World world) throws ModelException {
		try {
			world.getSpecificEntities(Planetoid.class);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void addPlanetoidToWorld(World world, Planetoid planetoid) throws ModelException {
		try {
			world.addEntity(planetoid);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}		
	}

	@Override
	public void removePlanetoidFromWorld(World world, Planetoid planetoid)
			throws ModelException {
		try {
			world.removeEntity(planetoid);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}		
	}

	@Override
	public Asteroid createAsteroid(double x, double y, double xVelocity, double yVelocity, double radius)
			throws ModelException {
		try {
			return new Asteroid(x, y, xVelocity, yVelocity, radius);
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public void terminateAsteroid(Asteroid asteroid) throws ModelException {
		try {
			asteroid.terminate();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}		
	}

	@Override
	public boolean isTerminatedAsteroid(Asteroid asteroid) throws ModelException {
		try {
			return asteroid.isTerminated();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double[] getAsteroidPosition(Asteroid asteroid) throws ModelException {
		try {
			return asteroid.getPosition().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double[] getAsteroidVelocity(Asteroid asteroid) throws ModelException {
		try {
			return asteroid.getVelocity().getAsArray();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double getAsteroidRadius(Asteroid asteroid) throws ModelException {
		try {
			return asteroid.getRadius();
		}
		catch (RuntimeException exc) {
			throw new ModelException(exc);
		}
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
