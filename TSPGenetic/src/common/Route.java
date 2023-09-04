package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Route implements Genome<City> {
    private List<City> cities;

    public Route() {
        this.cities = new ArrayList<>();
    }

    public Route(int size) {
        this.cities = new ArrayList<>(Collections.nCopies(size, null));
    }

    public Route(List<City> cities) {
        this.cities = new ArrayList<>(cities);
    }

    // Copy constructor
    public Route(Route other) {
        this.cities = new ArrayList<>(other.cities);
    }

    public boolean isValidSolution() {
    	CityManager debug = CityManager.getInstance();
        if (getSize() != CityManager.getInstance().getCities().size()) {
            return false; // Route length must match city catalog size
        }

        Set<City> uniqueCities = new HashSet<>();
        for (int i = 0; i < getSize(); i++) {
            City city = getCity(i);
            if (uniqueCities.contains(city)) {
                return false; // City is met twice
            }
            uniqueCities.add(city);
        }

        return true; // No cities are missing, and no city is met twice
    }

    @Override
    public int getSize() {
        return cities.size();
    }

    @Override
    public City getGene(int index) {
        return cities.get(index);
    }
    public City getCity(int index) {
        return getGene(index);
    }

    @Override
    public void setGene(int index, City city) {
        cities.set(index, city);
    }
    public void setCity(int index, City city) {
        setGene(index, city);
    }

    @Override
    public Genome<City> clone() {
        return new Route(this);
    }

    // Additional method to shuffle the cities for initialization
    public void shuffle() {
        Collections.shuffle(cities);
    }
    
    public Route generateRandomRoute() {
        Set<City> citySet = CityManager.getInstance().getCities();
        cities = new ArrayList<>(citySet);
        Collections.shuffle(cities);
        return this;
    }

	public List<City> getCities() {
		return this.cities;
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
