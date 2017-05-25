package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.IllegalMethodCallException;
import asteroids.model.exceptions.TerminatedException;
import asteroids.model.representation.*;

public class TestAsteroid {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	private static final double EPSILON = 0.0001;
	private static Bullet collidingBullet;
	private static Asteroid largeAsteroid, smallAsteroid;
	private static Planetoid collidingPlanetoid;
	private static Ship collidingShip;
	private static World world;
	

	@Before
	public void setUp() throws Exception {
		largeAsteroid = new Asteroid(500, 500, 10, 10, 40);
		smallAsteroid = new Asteroid(300, 300, 10, 10, 10);
		world = new World(5000, 5000);
		collidingShip = new Ship(540, 530, -10, -10, 10, 0);
		collidingPlanetoid = new Planetoid(340, 330, -10, -10, 40);
		collidingBullet = new Bullet(340, 330, -100, -100, 40);
	}

	@Test
	public void Constructor() {
		Asteroid newAsteroid = new Asteroid(1, 2, 3, 4, Asteroid.MINIMAL_RADIUS);
		assertEquals(1, newAsteroid.getPosition().getxComponent(), EPSILON);
		assertEquals(2, newAsteroid.getPosition().getyComponent(), EPSILON);
		assertEquals(3, newAsteroid.getVelocity().getxComponent(), EPSILON);
		assertEquals(4, newAsteroid.getVelocity().getyComponent(), EPSILON);
		assertEquals(Asteroid.MINIMAL_RADIUS, newAsteroid.getRadius(), EPSILON);
		assertEquals(Asteroid.MINIMAL_DENSITY, newAsteroid.getMinimalDensity(), EPSILON);
		assertEquals(Asteroid.MINIMAL_RADIUS, newAsteroid.getMinimalRadius(), EPSILON);
	}
	
	@Test
	public void testAsteroidPositionVelocityDouble() {
		Asteroid newAsteroid = new Asteroid(new Position(1, 2), new Velocity(3, 4), Asteroid.MINIMAL_RADIUS);
		assertEquals(new Position(1, 2), newAsteroid.getPosition());
		assertEquals(new Velocity(3, 4), newAsteroid.getVelocity());
		assertEquals(Asteroid.MINIMAL_RADIUS, newAsteroid.getRadius(), EPSILON);
	}
	
	@Test
	public void testCanHaveAsDensity() {
		assertTrue(largeAsteroid.canHaveAsDensity(Asteroid.MINIMAL_DENSITY));
		assertFalse(largeAsteroid.canHaveAsDensity(Asteroid.MINIMAL_DENSITY + 1));
	}
	
	@Test
	public void testCanBeRemovedFromWorld() {
		world.addEntity(largeAsteroid);
		assertTrue(largeAsteroid.canBeRemovedFromWorld());
		assertFalse(smallAsteroid.canBeRemovedFromWorld());
	}
	
	@Test
	public void testCanHaveAsRadius() {
		assertTrue(largeAsteroid.canHaveAsRadius(40));
		assertFalse(largeAsteroid.canHaveAsRadius(41));
	}

	@Test
	public void resolveCollision_WithShip() {
		world.addEntity(collidingShip);
		world.addEntity(largeAsteroid);
		assert Entity.apparentlyCollide(largeAsteroid, collidingShip);
		largeAsteroid.resolveCollision(collidingShip);
		
		assertEquals(largeAsteroid.getPosition(), new Position(500, 500));
		assertEquals(largeAsteroid.getVelocity(), new Velocity(10, 10));
		assertTrue(collidingShip .isTerminated());
	}
	
	@Test
	public void resolveCollision_WithPlanetoid() {
		world.addEntity(collidingPlanetoid);
		world.addEntity(smallAsteroid);
		assert Entity.apparentlyCollide(smallAsteroid, collidingPlanetoid);
		smallAsteroid.resolveCollision(collidingPlanetoid);
		Velocity velocityPlanetoidAfterCollision = collidingPlanetoid.getVelocity();
		Velocity velocityAsteroidAfterCollision = smallAsteroid.getVelocity();
		
		assertEquals(velocityPlanetoidAfterCollision.getxComponent(), -8.064495, EPSILON);
		assertEquals(velocityPlanetoidAfterCollision.getyComponent(), -8.548371, EPSILON);
		assertEquals(velocityAsteroidAfterCollision.getxComponent(), -32.864495, EPSILON);
		assertEquals(velocityAsteroidAfterCollision.getyComponent(), -22.148371, EPSILON);
	}
	
	@Test
	public void resolveCollision_WithBullet() {
		world.addEntity(collidingBullet);
		world.addEntity(smallAsteroid);
		smallAsteroid.resolveCollision(collidingBullet);
		assertTrue(smallAsteroid.isTerminated());
		assertTrue(collidingBullet.isTerminated());
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_DifferentWorlds() {
		world.addEntity(collidingPlanetoid);
		smallAsteroid.resolveCollision(collidingPlanetoid);
	}
	
	@Test(expected = TerminatedException.class)
	public void resolveCollision_TerminatedCase() {
		world.addEntity(collidingPlanetoid);
		world.addEntity(smallAsteroid);
		smallAsteroid.terminate();
		smallAsteroid.resolveCollision(collidingPlanetoid);
	}
}
