package common;

public class TSPFitnessFunction implements FitnessFunction<City>{

    @Override
    public int calculate(Genome<City> individual) {
        double totalDistance = 0;
        Route route = (Route) individual; // Assuming individual is of type Route

        for (int i = 0; i < route.getSize() - 1; i++) {
            City city1 = route.getGene(i);
            City city2 = route.getGene(i + 1);
            totalDistance += City.distanceTo(city1, city2); // Assuming static distanceTo method in City
        }

        // Adding distance from the last city back to the first to complete the loop
        totalDistance += City.distanceTo(route.getGene(route.getSize() - 1), route.getGene(0));

        // Return the total distance as an integer
        return (int) totalDistance;
    }

    @Override
    public int compare(Genome<City> individual1, Genome<City> individual2) {
        int fitness1 = calculate(individual1);
        int fitness2 = calculate(individual2);

        // Since lower values are better for TSP, we reverse the comparison
        return Integer.compare(fitness1, fitness2);
    }
}
