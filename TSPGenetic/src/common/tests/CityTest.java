package common.tests;
import common.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CityTest {

    @Test
    public void testDistanceTo() {
        City city1 = new City(0, 0);
        City city2 = new City(3, 4);
        double distance = city1.distanceTo(city2);
        assertEquals(5.0, distance, 0.001);
    }

    @Test
    public void testStaticDistanceTo() {
        City city1 = new City(0, 0);
        City city2 = new City(3, 4);
        double distance = City.distanceTo(city1, city2);
        assertEquals(5.0, distance, 0.001);
    }

    // Additional tests for other methods in the City class
}
