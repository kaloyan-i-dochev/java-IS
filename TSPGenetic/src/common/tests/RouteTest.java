package common.tests;
import common.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class RouteTest {

    @Before
    public void setUp() {
        // Clear existing cities and add 16 new cities to CityManager
        CityManager.getInstance().clearCities();
        for (int i = 0; i < 16; i++) {
            City city = new City(i * 10, i * 10);
            CityManager.getInstance().addCity(city);
        }
    }

    @Test
    public void testRandomRouteGeneration() {
        Route route1 = new Route();
        Route route2 = new Route();
        route1.generateRandomRoute();
        route2.generateRandomRoute();
        assertNotEquals(route1, route2);
    }

    @Test
    public void testAlleleUniqueness() {
        Route route = new Route();
        route.generateRandomRoute();
        // Check that each city appears exactly once in the route
        Set<City> uniqueCities = new HashSet<>(route.getCities()); 
        assertEquals(CityManager.getInstance().getCities().size(), uniqueCities.size());
    }

    @Test
    public void testIsValidSolution() {
        Route route = new Route();
        route.generateRandomRoute();
        assertTrue(route.isValidSolution()); // Expecting a valid solution

        // Modify route to create an invalid solution
        route.setGene(0, route.getGene(1)); // Duplicate city
        assertFalse(route.isValidSolution()); // Expecting an invalid solution
    }

    // Additional tests for other methods in the Route class
}

