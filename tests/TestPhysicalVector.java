package asteroids.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.exceptions.*;
import asteroids.model.representation.*;

public class TestPhysicalVector {
	
	private static final double EPSILON = 0.0001; 
	private static Position position_34, position_56;
	
	@Before
	public void setUp() throws Exception {
		position_34 = new Position(3,4);
		position_56 = new Position(5, 6);
	}
	
	@Test
	public void Constructor_LegalCase() {
		Position myPosition = new Position(1,2);
		assertEquals(myPosition.getxComponent(), 1, 0.01);
		assertEquals(myPosition.getyComponent(), 2, 0.01);
	}
	
	@Test(expected=IllegalComponentException.class)
	public void testConstructor_IllegalCase() {
		new Position(Double.NaN,2);
	}
	
	@Test
	public void testIsValidComponent_ValidCase() {
		assertTrue(Position.isValidComponent(0));
	}
	
	@Test
	public void testIsValidComponent_InfiniteCase() {
		assertTrue(Position.isValidComponent(Double.NEGATIVE_INFINITY));
	}
	
	@Test
	public void testIsValidComponent_NaNCase() {
		assertFalse(Position.isValidComponent(Double.NaN));
	}
	
	@Test
	public void getAsArrayTest(){
		assertEquals(position_34.getAsArray()[0], 3, 0.01);
		assertEquals(position_34.getAsArray()[1], 4, 0.01);
	}
	
	@Test
	public void scalarProductWith_LegalCase() {
		assertEquals(position_34.scalarProductWith(position_56), 39, EPSILON);
	}
	
	@Test
	public void vectorPlus_LegalCase() {
		assertEquals(position_34.vectorPlus(position_56), new Position(8, 10));
	}
	
	@Test
	public void vectorMinus_LegalCase() {
		assertEquals(position_34.vectorMinus(position_56), new Position(-2, -2));
	}
	
	@Test
	public void vectorScalarMultiple_LegalCase() {
		assertEquals(position_34.scalarMultiple(2), new Position(6, 8));
	}
}
