package common.tests;
import common.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CityManagerTest {

    @Test
    public void testAddCity() {
        CityManager manager = CityManager.getInstance();
        City city = new City(1, 2);
        manager.addCity(city);
        assertEquals(1, manager.getCities().size());
    }

    @Test
    public void testSingletonInstance() {
        CityManager manager1 = CityManager.getInstance();
        CityManager manager2 = CityManager.getInstance();
        assertEquals(manager1, manager2);
    }

    // Additional tests for other methods in the CityManager class
}
