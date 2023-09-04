package common;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CityManager {
    private static final CityManager instance = new CityManager();
    private static Random random = new Random();
    private final Set<City> cities;

    private CityManager() {
        cities = new HashSet<>();
    }

    public static CityManager getInstance() {
        return instance;
    }

    public City generateCity(int xLimit, int yLimit) {
        return new City(random.nextInt(xLimit), random.nextInt(yLimit), String.valueOf(cities.size()));
    }

    public void addCity(City city) {
        cities.add(city);
    }
    public void addCity(String name, int x, int y) {
    	addCity(new City(x, y, name));
    }
    public void addCity(int x, int y) {
    	addCity(new City(x, y, String.valueOf(cities.size())));
    }

    public void addCities(List<City> cityList) {
        cities.addAll(cityList);
    }

    public Set<City> getCities() {
        return Collections.unmodifiableSet(cities);
    }

    public int getSize() {
        return cities.size();
    }

	public void clearCities() {
		cities.clear();
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    for (City city : cities) {
	        sb.append("City: ").append(city.getName())
	          .append(" (").append(city.getX())
	          .append(", ").append(city.getY()).append(")\n");
	    }
	    return sb.toString();
	}
}
