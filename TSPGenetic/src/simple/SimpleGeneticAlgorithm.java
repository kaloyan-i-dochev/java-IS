package simple;

import common.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

public class SimpleGeneticAlgorithm {
    TSPFitnessFunction fitnessFunction = new TSPFitnessFunction();

    private static final int DEFAULT_MAX_GENERATIONS = 1000;
    private static final int DEFAULT_POPULATION_SIZE = 100;
    private static final double MUTATION_RATE = 0.01;
    private static final int TOURNAMENT_SIZE = 5;

    private final Random random = new Random();

    public Population<City> run(Population<City> initialPopulation, Integer maxGenerations) {
        int cityCount = CityManager.getInstance().getSize();
    	Population<City> population;
        if(initialPopulation != null) {
        	population = initialPopulation;
        }
        else {
        	population = generateInitialPopulation(cityCount);
        }
        
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
        Route best = null;
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            Route individual = (Route) population.getIndividual(random.nextInt(population.getSize()));
            if (best == null || fitnessFunction.compare(individual, best) < 0) {
                best = individual;
            }
        }
        return best;
    }

    private Route crossover(Route parent1, Route parent2) {
        Route child = new Route(parent1.getSize());
        int crossoverPoint = random.nextInt(parent1.getSize());

        // Copy genes from parent1 up to the crossover point
        for (int i = 0; i < crossoverPoint; i++) {
            child.setGene(i, parent1.getGene(i));
        }

        // Fill remaining genes from parent2 without duplicates
        int index = 0;
        for (int i = crossoverPoint; i < child.getSize(); i++) {
            City city = parent2.getGene(index++);
            while (child.getCities().contains(city)) {
                city = parent2.getGene(index++);
            }
            child.setGene(i, city);
        }

        return child;
    }

    private void mutate(Genome<City> individual) {
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
