package nqueens.genetic;

public interface GeneticAlgorithmRunner<T> {
    Population<T> run(Population<T> initialPopulation, int maxGenerations);
}

