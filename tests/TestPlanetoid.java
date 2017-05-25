package asteroids.tests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestPlanetoid {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	private static final double EPSILON = 0.0001;
	private static Bullet collidingBullet;
	private static Planetoid largePlanetoid, smallPlanetoid;
	private static Asteroid collidingAsteroid;
	private static Ship collidingShip;
	private static World world;
	

	@Before
	public void setUp() throws Exception {
		largePlanetoid = new Planetoid(500, 500, 10, 10, 40);
		smallPlanetoid = new Planetoid(300, 300, 10, 10, 10);
		world = new World(5000, 5000);
		collidingShip = new Ship(540, 530, -10, -10, 10, 0);
		collidingAsteroid = new Asteroid(340, 330, -10, -10, 40);
		collidingBullet = new Bullet(340, 330, -100, -100, 40);
	}
	
	@Test
	public void mostExtendedConstructor() {
		Planetoid planetoid = new Planetoid(200, 300, 40, 50, 10, 10e6);
		assertEquals(planetoid.getPosition(), new Position(200, 300));
		assertEquals(planetoid.getVelocity(), new Velocity(40, 50));
		assertEquals(planetoid.getRadius(), 10, EPSILON);
		assertEquals(planetoid.getTotalTravelledDistance(), 10e6, EPSILON);
		assertEquals(planetoid.getMass(), 10 * 10 * 10 * 4.0 / 3 * Math.PI * Planetoid.MINIMAL_DENSITY, EPSILON);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shrink_NegativeAmount() {
		largePlanetoid.shrink(-1);
	}
	
	@Test
	public void shrink_ValidAmount() {
		largePlanetoid.shrink(10);
		assertEquals(largePlanetoid.getRadius(), 30, EPSILON);
	}
	
	@Test
	public void shrink_TooLargeAmount() {
		largePlanetoid.shrink(40);
		assertTrue(largePlanetoid.isTerminated());
	}
	
	@Test
	public void move_LegalCase() {
		largePlanetoid.move(1);
		assertEquals(largePlanetoid.getPosition(), new Position(510, 510));
		assertEquals(largePlanetoid.getRadius(), 40 - Planetoid.SHRINK_FACTOR * largePlanetoid.getSpeed() * 1, EPSILON);
	}
	
	@Test
	public void terminate_SmallPlanetoid() {
		world.addEntity(smallPlanetoid);
		smallPlanetoid.terminate();
		assertEquals(0, world.getEntities().size());
		assertTrue(smallPlanetoid.isTerminated());
	}
	
	@Test
	public void terminate_LargePlanetoid() {
		world.addEntity(largePlanetoid);
		largePlanetoid.terminate();
		assertEquals(2, world.getEntities().size());
		assertTrue(largePlanetoid.isTerminated());
		Asteroid[] asteroidsInWorld = world.getSpecificEntities(Asteroid.class).toArray(new Asteroid[] {});
		assertEquals(asteroidsInWorld[0].getRadius(), largePlanetoid.getRadius() / 2, EPSILON);
		assertEquals(asteroidsInWorld[1].getRadius(), largePlanetoid.getRadius() / 2, EPSILON);
		assertEquals(asteroidsInWorld[0].getSpeed(), largePlanetoid.getSpeed() * 1.5, EPSILON);
		assertEquals(asteroidsInWorld[1].getSpeed(), largePlanetoid.getSpeed() * 1.5, EPSILON);
		assertEquals(Position.getDistanceBetween(asteroidsInWorld[0].getPosition(), largePlanetoid.getPosition()), largePlanetoid.getRadius() / 2, EPSILON);
		assertEquals(Position.getDistanceBetween(asteroidsInWorld[1].getPosition(), largePlanetoid.getPosition()), largePlanetoid.getRadius() / 2, EPSILON);
		assertEquals(largePlanetoid.getPosition(), (asteroidsInWorld[0].getPosition().scalarMultiple(0.5)).vectorPlus(asteroidsInWorld[1].getPosition().scalarMultiple(0.5)));
	}
	
	@Test
	public void resolveCollision_WithShip() {
		world.addEntity(collidingShip);
		world.addEntity(largePlanetoid);
		assert Entity.apparentlyCollide(largePlanetoid, collidingShip);
		largePlanetoid.resolveCollision(collidingShip);
		
		assertEquals(largePlanetoid.getPosition(), new Position(500, 500));
		assertEquals(largePlanetoid.getVelocity(), new Velocity(10, 10));
		assertEquals(collidingShip.getVelocity(), new Velocity(-10, -10));
		assertTrue(collidingShip.isTerminated() || world.hasAsEntity(collidingShip));
	}
	
	@Test
	public void resolveCollision_WithAsteroid() {
		world.addEntity(collidingAsteroid);
		world.addEntity(smallPlanetoid);
		assert Entity.apparentlyCollide(smallPlanetoid, collidingAsteroid);
		smallPlanetoid.resolveCollision(collidingAsteroid);
		Velocity velocityAsteroidAfterCollision = collidingAsteroid.getVelocity();
		Velocity velocityPlanetoidAfterCollision = smallPlanetoid.getVelocity();
		
		assertEquals(velocityAsteroidAfterCollision.getxComponent(), -9.759076, EPSILON);
		assertEquals(velocityAsteroidAfterCollision.getyComponent(), -9.819307, EPSILON);
		assertEquals(velocityPlanetoidAfterCollision.getxComponent(), -34.559076, EPSILON);
		assertEquals(velocityPlanetoidAfterCollision.getyComponent(), -23.419307, EPSILON);
	}
	
	@Test
	public void resolveCollision_WithBullet() {
		world.addEntity(collidingBullet);
		world.addEntity(smallPlanetoid);
		smallPlanetoid.resolveCollision(collidingBullet);
		assertTrue(smallPlanetoid.isTerminated());
		assertTrue(collidingBullet.isTerminated());
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_DifferentWorlds() {
		world.addEntity(collidingAsteroid);
		smallPlanetoid.resolveCollision(collidingAsteroid);
	}
	
	@Test(expected = TerminatedException.class)
	public void resolveCollision_TerminatedCase() {
		world.addEntity(collidingAsteroid);
		world.addEntity(smallPlanetoid);
		smallPlanetoid.terminate();
		smallPlanetoid.resolveCollision(collidingAsteroid);
	}
}