package nqueens.genetic;

import java.util.*;
import java.util.stream.Collectors;

public interface FitnessFunction<T> extends Comparator<Genome<T>> {

    int calculate(Genome<T> individual);
    default int calculate(Population<T> population) {
    	return calculate(getFittest(population));
    }
    
    // implementing comparator as fitness carries a connotation of what is better that is strongly coupled with the comparison of comparators
    @Override
    default int compare(Genome<T> individual1, Genome<T> individual2) {  
        int fitness1 = calculate(individual1);
        int fitness2 = calculate(individual2);

        // By default, assume that higher values are better.
        // Override this method in implementations where lower values are better.
        return Integer.compare(fitness2, fitness1);
    }

    default Genome<T> getFittest(Population<T> population) {
        Genome<T> fittest = population.getIndividual(0);
        for (Genome<T> individual : population.getIndividuals()) {
            if (calculate(individual) < calculate(fittest)) {
                fittest = individual;
            }
        }
        return fittest;
    }

    default double getAverageFitness(Population<T> population) {
        return population.getIndividuals().stream()
                .mapToDouble(this::calculate)
                .average()
                .orElse(0);
    }

    default double getFitnessStandardDeviation(Population<T> population) {
        double average = getAverageFitness(population);
        double variance = population.getIndividuals().stream()
                .mapToDouble(individual -> Math.pow(this.calculate(individual) - average, 2))
                .average()
                .orElse(0);
        return Math.sqrt(variance);
    }

    default Map<Integer, Long> getFitnessDistribution(Population<T> population) {
        return population.getIndividuals().stream()
                .collect(Collectors.groupingBy(this::calculate, Collectors.counting()));
    }

    default Pair<Integer, Integer> getBestAndWorstFitness(Population<T> population) {
        int best = population.getIndividuals().stream()
                .mapToInt(this::calculate)
                .max()
                .orElse(Integer.MIN_VALUE);
        int worst = population.getIndividuals().stream()
                .mapToInt(this::calculate)
                .min()
                .orElse(Integer.MAX_VALUE);
        return new Pair<>(best, worst);
    }

    default Map<Double, Integer> getFitnessPercentiles(Population<T> population, double... percentiles) {
        List<Integer> fitnesses = population.getIndividuals().stream()
                .map(this::calculate)
                .sorted()
                .collect(Collectors.toList());
        Map<Double, Integer> percentileMap = new HashMap<>();
        for (double percentile : percentiles) {
            int index = (int) (percentile / 100 * fitnesses.size());
            percentileMap.put(percentile, fitnesses.get(index));
        }
        return percentileMap;
    }
}

