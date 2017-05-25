package asteroids.tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestWorld {
	
	private static final double EPSILON = 0.0001;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	private static World testWorld, anotherWorld, terminatedWorld, evolvingWorld;
	private static Ship testShip, collidingShip, ship1, ship2;
	private static Bullet bulletOnShip, bulletInWorld;
	
	@Before
	public void setUp() throws Exception {
		testWorld = new World(1000, 1000);
		testShip = new Ship(testWorld.getWidth()/2, testWorld.getHeight()/2, Ship.MINIMAL_RADIUS);
		assert testWorld.canHaveAsEntity(testShip);
		testWorld.addEntity(testShip);
		collidingShip = new Ship(testWorld.getWidth()/2 - 2 * Ship.MINIMAL_RADIUS, testWorld.getHeight()/2, Ship.MINIMAL_RADIUS);
		assert testWorld.canHaveAsEntity(collidingShip);
		testWorld.addEntity(collidingShip);
		bulletOnShip = new Bullet(testWorld.getWidth()/2, testWorld.getHeight()/2, 0, 0, Bullet.MINIMAL_RADIUS);
		assert testShip.canHaveAsBullet(bulletOnShip);
		testShip.loadBullet(bulletOnShip);
		bulletInWorld = new Bullet(Bullet.MINIMAL_RADIUS + 1, Bullet.MINIMAL_RADIUS, 0, 0, Bullet.MINIMAL_RADIUS);
		assert testWorld.canHaveAsEntity(bulletInWorld);
		testWorld.addEntity(bulletInWorld);
		assert testWorld.hasProperEntities();
		
		anotherWorld = new World(World.getMaxHeight(), World.getMaxWidth());
		
		terminatedWorld = new World(World.getMaxHeight(), World.getMaxWidth());
		terminatedWorld.terminate();
		
		evolvingWorld = new World(1000, 1000);
		ship1 = new Ship(400, 500, 10, 0, 10, 0);
		ship2 = new Ship(600, 500, -10, 0, 10, 0);
		evolvingWorld.addEntity(ship1);
		evolvingWorld.addEntity(ship2);
		assert evolvingWorld.hasProperEntities();
	}
	
	@Test
	public void constructor_LegalCase() {
		World world = new World(World.getMaxHeight(), World.getMaxWidth());
		assertEquals(world.getHeight(), World.getMaxHeight(), EPSILON);
		assertEquals(world.getWidth(), World.getMaxWidth(), EPSILON);
	}
	
	@Test
	public void constructor_InvalidDimensions() {
		World world = new World(Double.POSITIVE_INFINITY, Double.NaN);
		assertEquals(world.getHeight(), World.getMaxHeight(), EPSILON);
		assertEquals(world.getWidth(), World.getMaxWidth(), EPSILON);
	}
	
	@Test
	public void terminate() {
		testWorld.terminate();
		assertTrue(testWorld.getEntities().isEmpty());
		assertTrue(testWorld.getOccupiedPositions().isEmpty());
		assertTrue(testWorld.isTerminated());
	}
	
	@Test
	public void boundariesSurround_TrueCase() {
		if ((2 * Bullet.MINIMAL_RADIUS > testWorld.getHeight()) || (2 * Bullet.MINIMAL_RADIUS > testWorld.getWidth()))
			//No entity will fit into a world.
			return;
		assertTrue(testWorld.boundariesSurround(bulletOnShip));
	}
	
	@Test
	public void boundariesSurround_FalseCase() {
		Bullet testBullet = new Bullet(0, 0, 0, 0, Bullet.MINIMAL_RADIUS);
		assertFalse(testWorld.boundariesSurround(testBullet));
	}
	
	@Test
	public void getOccupiedPositions() {
		if (testWorld.hasWithinBoundaries(bulletInWorld.getPosition()))
			assertTrue(testWorld.getOccupiedPositions().contains(bulletInWorld.getPosition()));
		if (testWorld.hasWithinBoundaries(testShip.getPosition()))
			assertTrue(testWorld.getOccupiedPositions().contains(testShip.getPosition()));
		assertFalse(testWorld.getOccupiedPositions().contains(new Position(-1, -1)));
		assertFalse(testWorld.getOccupiedPositions().contains(new Position(World.getMaxHeight(), World.getMaxWidth())));
	}
	
	@Test
	public void getEntities() {
		for (Position position: testWorld.getOccupiedPositions()) {
			assertTrue(testWorld.getEntities().contains(testWorld.getEntityAt(position)));
		}
	}
	
	@Test
	public void getShips() {
		assertTrue(testWorld.getSpecificEntities(Ship.class).contains(testShip));
		assertFalse(testWorld.getSpecificEntities(Ship.class).contains(bulletInWorld));
	}
	
	@Test
	public void getBullets() {
		assertFalse(testWorld.getSpecificEntities(Bullet.class).contains(testShip));
		assertTrue(testWorld.getSpecificEntities(Bullet.class).contains(bulletInWorld));
	}
	
	@Test
	public void addEntity_LegalCase() {
		Bullet bulletToAdd = new Bullet(testWorld.getWidth() - Bullet.MINIMAL_RADIUS, testWorld.getHeight() - Bullet.MINIMAL_RADIUS,
																									0, 0, Bullet.MINIMAL_RADIUS);
		if (!testWorld.canHaveAsEntity(bulletToAdd) || Entity.overlap(bulletToAdd, testShip))
			//The dimensions of the testWorld are not big enough to add another entity.
			return;
		testWorld.addEntity(bulletToAdd);
		assertTrue(testWorld.getEntityAt(bulletToAdd.getPosition()) == bulletToAdd);
		assertEquals(bulletToAdd.getWorld(), testWorld);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addEntity_CannotHaveAsEntity() {
		testWorld.addEntity(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addEntity_EntityAlreadyHasWorld() {
		Ship otherShip = new Ship(anotherWorld.getWidth(), anotherWorld.getHeight(), Ship.MINIMAL_RADIUS);
		anotherWorld.addEntity(otherShip);
		testWorld.addEntity(otherShip);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addEntity_AlreadyHasEntity() {
		testWorld.addEntity(testShip);
	}
	
	@Test(expected=OverlapException.class)
	public void addEntity_Overlap() {
		assert testWorld.canHaveAsEntity(bulletOnShip);
		testWorld.addEntity(bulletOnShip);
	}
	
	@Test
	public void removeEntity_LegalCase() {
		testWorld.removeEntity(bulletInWorld);
		assertNull(testWorld.getEntityAt(bulletInWorld.getPosition()));
		assertNull(bulletInWorld.getWorld());
	}
	
	@Test(expected=NullPointerException.class)
	public void removeEntity_NullEntity() {
		testWorld.removeEntity(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void removeEntity_EntityNotInWorld() {
		assert !testWorld.hasAsEntity(bulletOnShip);
		testWorld.removeEntity(bulletOnShip);
	}
	
	@Test(expected=IllegalMethodCallException.class)
	public void removeEntity_EntityNotRemovable() {
		testShip.fireBullet();
		testWorld.removeEntity(testShip);
	}
	
	@Test
	public void updatePosition_LegalCase() {
		testWorld.updatePosition(testShip);
		assertEquals(testWorld.getEntityAt(testShip.getPosition()), testShip);
		for (Position position: testWorld.getOccupiedPositions()) {
			//Due to the class invariants in World, we know that for any Position pos that is not in testWorld.getOccupiedPositions(),
			// testWorld.getEntityAt(pos) == null. Therefore, it suffices to check the positions in testWorld.getOccupiedPositions().
			if (!position.equals(testShip.getPosition()))
				assertNotEquals(testWorld.getEntityAt(position), testShip);
		}
	}
	
	@Test(expected=TerminatedException.class)
	public void updatePosition_TerminatedWorld() {
		terminatedWorld.updatePosition(testShip);
	}
	
	@Test(expected=IllegalMethodCallException.class)
	public void updatePosition_EntityNotInWorld() {
		testWorld.updatePosition(bulletOnShip);
	}
	
	@Test
	public void getTimeToFirstCollision_LegalCase() {
		double time = evolvingWorld.getTimeToFirstCollision();
		assertEquals(time, 9, EPSILON);
		assertTrue(Entity.collideAfterMove(ship1, ship2, time));
		assertFalse(Entity.collideAfterMove(ship1, ship2, time - EPSILON));
	}
	
	@Test(expected=IllegalMethodCallException.class)
	public void getTimeToFirstCollision_NoEntities() {
		anotherWorld.getTimeToFirstCollision();
	}
	
	@Test(expected=TerminatedException.class)
	public void getTimeToFirstCollision_TerminatedWorld() {
		terminatedWorld.getTimeToFirstCollision();
	}
	
	@Test
	public void getPositionFirstCollision_TwoEntitiesColliding() {
		assertEquals(evolvingWorld.getPositionFirstCollision(), Entity.getCollisionPosition(ship1, ship2));
	}
	
	@Test
	public void getPositionFirstCollision_EntityWithBoundary() {
		assertEquals(testWorld.getPositionFirstCollision(), bulletInWorld.getCollisionWithBoundaryPosition());
	}
	
	@Test(expected=IllegalMethodCallException.class)
	public void getPositionFirstCollision_NoEntities() {
		anotherWorld.getPositionFirstCollision();
	}
	
	@Test(expected=TerminatedException.class)
	public void getPositionFirstCollision_TerminatedWorld() {
		terminatedWorld.getPositionFirstCollision();
	}
	
	@Test
	public void getCollisions() {
		Set<Set<Entity>> collisionSet = testWorld.getCollisions();
		//Check whether each element of collisionSet denotes a proper collision.
		for (Set<Entity> collision: collisionSet) {
			if (collision.size() == 1)
				assertTrue(((Entity)collision.toArray()[0]).apparentlyCollidesWithBoundary());
			else if (collision.size() == 2) {
				Object[] collisionArray = collision.toArray();
				Entity entity1 = (Entity)collisionArray[0];
				Entity entity2 = (Entity)collisionArray[1];
				assertTrue(Entity.apparentlyCollide(entity1, entity2));
			}
			else
				fail("Illegal collision in getCollisions()");
		}
		//Check whether all collisions in the world are in the collisionSet.
		for (Entity entity: testWorld.getEntities()) {
			if (entity.apparentlyCollidesWithBoundary()) {
				Set<Entity> collision = new HashSet<>();
				collision.add(entity);
				assertTrue(collisionSet.contains(collision));
			}
			for (Entity other: testWorld.getEntities()) {
				if ((entity != other) && Entity.apparentlyCollide(entity, other)) {
					Set<Entity> collision = new HashSet<>();
					collision.add(entity);
					collision.add(other);
					assertTrue(collisionSet.contains(collision));
				}
			}
		}
	}
	
	@Test
	public void evolve_LegalCase() {
		evolvingWorld.evolve(5, null);
		assertEquals(ship1.getPosition().getxComponent(), 450, EPSILON);
		assertEquals(ship1.getPosition().getyComponent(), 500, EPSILON);
		assertEquals(ship1.getVelocity().getxComponent(), 10, EPSILON);
		assertEquals(ship1.getVelocity().getyComponent(), 0, EPSILON);
		assertEquals(ship2.getPosition().getxComponent(), 550, EPSILON);
		assertEquals(ship1.getPosition().getyComponent(), 500, EPSILON);
		assertEquals(ship2.getVelocity().getxComponent(), -10, EPSILON);
		assertEquals(ship2.getVelocity().getyComponent(), 0, EPSILON);
		
		evolvingWorld.evolve(4, null);
		assertEquals(ship1.getPosition().getxComponent(), 490, EPSILON);
		assertEquals(ship1.getPosition().getyComponent(), 500, EPSILON);
		assertEquals(ship2.getPosition().getxComponent(), 510, EPSILON);
		assertEquals(ship1.getPosition().getyComponent(), 500, EPSILON);
		//We do not test for the velocity here, because the ships must collide after 9 seconds and we don't exactly know whether the
		// collision is already resolved or it will be in the next instant of time.
		
		evolvingWorld.evolve(9, null);
		assertEquals(ship1.getPosition().getxComponent(), 400, EPSILON);
		assertEquals(ship1.getPosition().getyComponent(), 500, EPSILON);
		assertEquals(ship1.getVelocity().getxComponent(), -10, EPSILON);
		assertEquals(ship1.getVelocity().getyComponent(), 0, EPSILON);
		assertEquals(ship2.getPosition().getxComponent(), 600, EPSILON);
		assertEquals(ship1.getPosition().getyComponent(), 500, EPSILON);
		assertEquals(ship2.getVelocity().getxComponent(), 10, EPSILON);
		assertEquals(ship2.getVelocity().getyComponent(), 0, EPSILON);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void evolve_NegativeDuration() {
		evolvingWorld.evolve(-1, null);
	}
	
	@Test(expected=TerminatedException.class)
	public void evolve_TerminatedWorld() {
		terminatedWorld.evolve(1, null);
	}
}
