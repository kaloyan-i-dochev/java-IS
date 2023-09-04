package nqueens.genetic.strategies;

import nqueens.genetic.FitnessFunction;
import nqueens.genetic.Population;

import java.util.stream.Collectors;

public class ElitismSelectionStrategy<T> extends SelectionStrategy<T> {
    private int eliteSize;
    private FitnessFunction<T> fitnessFunction;

    public ElitismSelectionStrategy(int eliteSize, FitnessFunction<T> fitnessFunction) {
        this.eliteSize = eliteSize;
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public Population<T> select(Population<T> population) {
        // Sort the population in descending order of fitness
    	var sortedPopulationList = population.getIndividuals().stream()
                .sorted(fitnessFunction)
                .collect(Collectors.toList());

    	Population<T> sortedPopulation = new Population<>(sortedPopulationList);
        // Create a new population with the 'eliteSize' fittest individuals
        Population<T> newPopulation = new Population<>();
        for (int i = 0; i < eliteSize && i < sortedPopulation.getSize(); i++) {
            newPopulation.addIndividual(sortedPopulation.getIndividual(i));
        }

        return newPopulation;
    }
}
