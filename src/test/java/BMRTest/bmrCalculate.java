package test.java.BMRTest;

import Model.Profile.UserProfile;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class bmrCalculate {

	@Test
	public void test1() {
		UserProfile profile = new UserProfile("test", "test", "test", "test", 30, "male", 175.00, 70, null, false);
		double expectedBMR = 1648.75;
		System.out.println(profile.calculateBMR());
		assertTrue(expectedBMR == profile.calculateBMR());

	}

	@Test
	public void test2() {
		UserProfile profile = new UserProfile("test", "test", "test", "test", 25, "female", 160.00, 60, null, false);
		double expectedBMR = 1314.00;
		System.out.println(profile.calculateBMR());
		assertTrue(expectedBMR == profile.calculateBMR());

	}

	@Test
	public void test3() {
		UserProfile profile = new UserProfile("test", "test", "test", "test", 40, "male", 180, 75, null, false);
		double expectedBMR = 1680.00;
		System.out.println(profile.calculateBMR());
		assertTrue(expectedBMR == profile.calculateBMR());

	}

	@Test
	public void test4() {
		UserProfile profile = new UserProfile("test", "test", "test", "test", 38, "female", 175.00, 60, null, false);
		double expectedBMR = 1342.75;
		System.out.println(profile.calculateBMR());
		assertTrue(expectedBMR == profile.calculateBMR());

	}

	@Test
	public void test5() {
		UserProfile profile = new UserProfile("test", "test", "test", "test", 20, "male", 185.00, 75, null, false);
		double expectedBMR = 1811.25;
		System.out.println(profile.calculateBMR());
		assertTrue(expectedBMR == profile.calculateBMR());
	}

}
