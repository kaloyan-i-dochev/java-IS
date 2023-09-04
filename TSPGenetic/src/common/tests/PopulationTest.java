package common.tests;
import common.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PopulationTest {

    @Test
    public void testAddIndividual() {
        Population<City> population = new Population<>();
        Route route = new Route(); // Assuming Route is implemented
        population.addIndividual(route);
        assertEquals(1, population.getSize());
    }

    @Test
    public void testGetIndividual() {
        Population<City> population = new Population<>();
        Route route = new Route(); // Assuming Route is implemented
        population.addIndividual(route);
        assertEquals(route, population.getIndividual(0));
    }

    // Additional tests for other methods in the Population class
}

