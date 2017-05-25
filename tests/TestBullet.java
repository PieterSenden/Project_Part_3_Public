package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestBullet {
	
	private static final double EPSILON = 0.0001;
	private static Bullet bullet, loadedBullet, bulletHorizontalBoundary, bulletVerticalBoundary, bulletMiddle1, bulletMiddle2,
					enemyBullet, bulletInAnotherWorld;
	private static Ship ship;
	private static World world, anotherWorld;
	
	
	@Before
	public void setUp() {
		bullet = new Bullet(0, 0, 0, 0, Bullet.MINIMAL_RADIUS, 10e20);
		
		world = new World(1000, 1000);
		bulletHorizontalBoundary = new Bullet(3 * Bullet.MINIMAL_RADIUS, Bullet.MINIMAL_RADIUS, 0, -3, Bullet.MINIMAL_RADIUS);
		bulletVerticalBoundary = new Bullet(Bullet.MINIMAL_RADIUS, 3 * Bullet.MINIMAL_RADIUS, -3, 0, Bullet.MINIMAL_RADIUS);
		bulletMiddle1 = new Bullet(10 * Bullet.MINIMAL_RADIUS, 10 * Bullet.MINIMAL_RADIUS, 5, 0, Bullet.MINIMAL_RADIUS);
		bulletMiddle2 = new Bullet(12 * Bullet.MINIMAL_RADIUS, 10 * Bullet.MINIMAL_RADIUS, -5, 0, Bullet.MINIMAL_RADIUS);
		world.addEntity(bulletHorizontalBoundary);
		world.addEntity(bulletVerticalBoundary);
		world.addEntity(bulletMiddle1);
		world.addEntity(bulletMiddle2);
		
		ship = new Ship(world.getWidth() - 2 * Ship.MINIMAL_RADIUS, Ship.MINIMAL_RADIUS, Ship.MINIMAL_RADIUS);
		loadedBullet = new Bullet(world.getWidth() - 2 * Ship.MINIMAL_RADIUS, Ship.MINIMAL_RADIUS, 0, 0, Bullet.MINIMAL_RADIUS);
		ship.loadBullet(loadedBullet);
		world.addEntity(ship);
		
		enemyBullet = new Bullet(world.getWidth() - 3 * Ship.MINIMAL_RADIUS - Bullet.MINIMAL_RADIUS, Ship.MINIMAL_RADIUS,
																								1, 0, Bullet.MINIMAL_RADIUS);
		world.addEntity(enemyBullet);
		
		anotherWorld = new World(100, 100);
		bulletInAnotherWorld = new Bullet(Bullet.MINIMAL_RADIUS, Bullet.MINIMAL_RADIUS, 0, 0, Bullet.MINIMAL_RADIUS);
		anotherWorld.addEntity(bulletInAnotherWorld);
	}
	
	@Test
	public void mostExtendedConstructor_LegalCase() {
		bullet = new Bullet(1, 2, 3, 4, Bullet.MINIMAL_RADIUS, 10e20);
		assertEquals(bullet.getPosition().getxComponent(), 1, EPSILON);
		assertEquals(bullet.getPosition().getyComponent(), 2, EPSILON);
		assertEquals(bullet.getVelocity().getxComponent(), 3, EPSILON);
		assertEquals(bullet.getVelocity().getyComponent(), 4, EPSILON);
		assertEquals(bullet.getRadius(), Bullet.MINIMAL_RADIUS, EPSILON);
		assertEquals(bullet.getDensity(), 7.8e12, EPSILON);
	}
	
	@Test(expected = IllegalComponentException.class)
	public void mostExtendedConstructor_IllegalComponent() throws Exception {
		bullet = new Bullet(Double.NaN, 2, 3, 4, 15, 10e20);
	}
	
	@Test(expected = IllegalRadiusException.class)
	public void mostExtendedConstructor_IllegalRadius() throws Exception {
		bullet = new Bullet(1, 2, 3, 4, 0, 10e20);
	}
	
	@Test
	public void terminate() {
		loadedBullet.terminate();
		assertTrue(loadedBullet.isTerminated());
		assertNull(loadedBullet.getContainingShip());
		assertFalse(ship.hasAsBullet(loadedBullet));
	}
	
	@Test
	public void bounceOfBoundary_HorizontalBoundary() {
		assert bulletHorizontalBoundary.apparentlyCollidesWithHorizontalBoundary();
		int oldNbOfBounces = bulletHorizontalBoundary.getNbOfBounces();
		bulletHorizontalBoundary.bounceOffBoundary();
		assertEquals(bulletHorizontalBoundary.getVelocity().getxComponent(), 0, EPSILON);
		assertEquals(bulletHorizontalBoundary.getVelocity().getyComponent(), 3, EPSILON);
		assertEquals(bulletHorizontalBoundary.getNbOfBounces(), oldNbOfBounces + 1);
	}
	
	@Test
	public void bounceOfBoundary_VerticalBoundary() {
		assert bulletVerticalBoundary.apparentlyCollidesWithVerticalBoundary();
		int oldNbOfBounces = bulletVerticalBoundary.getNbOfBounces();
		bulletVerticalBoundary.bounceOffBoundary();
		assertEquals(bulletVerticalBoundary.getVelocity().getxComponent(), 3, EPSILON);
		assertEquals(bulletVerticalBoundary.getVelocity().getyComponent(), 0, EPSILON);
		assertEquals(bulletVerticalBoundary.getNbOfBounces(), oldNbOfBounces + 1);
		
	}
	
	@Test(expected = TerminatedException.class)
	public void bounceOfBoundary_TerminatedBulletCase() {
		bullet.terminate();
		bullet.bounceOffBoundary();
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void bounceOfBoundary_NonEffectiveWorldCase() {
		bullet.bounceOffBoundary();
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void bounceOfBoundary_NoCollisionCase() {
		bulletMiddle1.bounceOffBoundary();
	}
	
	@Test
	public void bounceOfBoundary_MaximalNumberOfBouncesReached() {
		for (int i = 1; i <= bulletHorizontalBoundary.getMaximalNbOfBounces(); i++) {
			bulletHorizontalBoundary.bounceOffBoundary();
			double timeToCollision = bulletHorizontalBoundary.getTimeToCollisionWithBoundary();
			bulletHorizontalBoundary.move(timeToCollision);
		}
		bulletHorizontalBoundary.bounceOffBoundary();
		assertTrue(bulletHorizontalBoundary.isTerminated());
	}
	
	@Test(expected = TerminatedException.class)
	public void resolveCollision_TerminatedBulletCase() {
		bullet.terminate();
		bullet.resolveCollision(loadedBullet);
	}
	
	@Test
	public void resolveCollision_WithBullet() {
		bulletMiddle1.resolveCollision(bulletMiddle2);
		assertTrue(bulletMiddle1.isTerminated());
		assertTrue(bulletMiddle2.isTerminated());
	}
	
	@Test
	public void resolveCollision_WithOwnShip() {
		ship.fireBullet();
		double timeToCollision = loadedBullet.getTimeToCollisionWithBoundary();
		loadedBullet.move(timeToCollision);
		loadedBullet.bounceOffBoundary();
		timeToCollision = Entity.getTimeToCollision(ship, loadedBullet);
		loadedBullet.move(timeToCollision);
		loadedBullet.resolveCollision(ship);
		assertTrue(ship.hasLoadedInMagazine(loadedBullet));
		assertEquals(loadedBullet.getContainingShip(), ship);
	}
	
	@Test
	public void resolveCollision_WithEnemyShip() {
		enemyBullet.resolveCollision(ship);
		assertTrue(enemyBullet.isTerminated());
		assertTrue(ship.isTerminated());
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_BulletNotInWorld() {
		bullet.resolveCollision(bullet);
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_NoCollisionCase() {
		bulletHorizontalBoundary.resolveCollision(bulletMiddle1);
	}
	
	@Test(expected = IllegalMethodCallException.class)
	public void resolveCollision_DifferentWorldsCase() {
		bulletHorizontalBoundary.resolveCollision(bulletInAnotherWorld);
	}
	
	
	@Test
	public void canBeRemovedFromWorld_BulletNotInWorld() {
		assertFalse(bullet.canBeRemovedFromWorld());
	}
	
	@Test
	public void canBeRemovedFromWorld_BulletCollidesWithOwnShip() {
		ship.fireBullet();
		double timeToCollision = loadedBullet.getTimeToCollisionWithBoundary();
		loadedBullet.move(timeToCollision);
		loadedBullet.bounceOffBoundary();
		timeToCollision = Entity.getTimeToCollision(ship, loadedBullet);
		loadedBullet.move(timeToCollision);
		assertTrue(loadedBullet.canBeRemovedFromWorld());
	}
	
	@Test
	public void canBeRemovedFromWorld_BulletNotAssociatedWithShip() {
		assertTrue(bulletHorizontalBoundary.canBeRemovedFromWorld());
	}
	
}
