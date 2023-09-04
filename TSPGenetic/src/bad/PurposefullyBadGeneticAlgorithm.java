package bad;

import common.City;
import common.CityManager;
import common.Genome;
import common.Population;
import common.Route;

import java.util.Random;

public class PurposefullyBadGeneticAlgorithm { //We trained it wrong as a joke

    private static final int DEFAULT_MAX_GENERATIONS = 1000;
    private static final int DEFAULT_POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.01;

    private final Random random = new Random();

    public Population<City> run(int cityCount, Population<City> initialPopulation, Integer maxGenerations) {
        // Initialize cities
        for (int i = 0; i < cityCount; i++) {
            City city = new City(random.nextInt(100), random.nextInt(100));
            CityManager.getInstance().addCity(city);
        }

        // Initialize population
        Population<City> population = initialPopulation != null ? initialPopulation : generateInitialPopulation(cityCount);

        // Set max generations
        int maxGen = maxGenerations != null ? maxGenerations : DEFAULT_MAX_GENERATIONS;

        // Main GA loop
        for (int generation = 0; generation < maxGen; generation++) {
            Population<City> newPopulation = new Population<>();

            // Selection and crossover
            for (int i = 0; i < population.getSize(); i++) {
                Route parent1 = selectParent(population);
                Route parent2 = selectParent(population);
                Route child = crossover(parent1, parent2);
                newPopulation.addIndividual(child);
            }

            // Mutation
            for (int i = 0; i < newPopulation.getSize(); i++) {
                mutate(newPopulation.getIndividual(i));
            }

            population = newPopulation;
        }

        return population;
    }

    private Population<City> generateInitialPopulation(int cityCount) {
        Population<City> population = new Population<>();
        for (int i = 0; i < DEFAULT_POPULATION_SIZE; i++) {
            Route route = new Route();
            route.generateRandomRoute(); // Assuming this method is implemented in Route
            population.addIndividual(route);
        }
        return population;
    }

    private Route selectParent(Population<City> population) {
        // Implement a selection strategy, e.g., Roulette Wheel Selection or Tournament Selection
        // For simplicity, we'll just return a random individual
        return (Route) population.getIndividual(random.nextInt(population.getSize()));
    }

    private Route crossover(Route parent1, Route parent2) {
        // Implement a crossover strategy, e.g., One-Point, Two-Point, PMX
        // For simplicity, we'll just return one of the parents
        return random.nextBoolean() ? parent1 : parent2;
    }

    private void mutate(Genome<City> individual) {
        // Implement a mutation strategy, e.g., Swap Mutation
        // For simplicity, we'll just swap two random cities with a certain probability
        if (random.nextDouble() < MUTATION_RATE) {
            int index1 = random.nextInt(individual.getSize());
            int index2 = random.nextInt(individual.getSize());
            City city1 = individual.getGene(index1);
            City city2 = individual.getGene(index2);
            individual.setGene(index1, city2);
            individual.setGene(index2, city1);
        }
    }
}
