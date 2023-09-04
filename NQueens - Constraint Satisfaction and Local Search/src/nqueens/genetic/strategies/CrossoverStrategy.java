package nqueens.genetic.strategies;

import nqueens.genetic.Genome;
import nqueens.genetic.Pair;

public abstract class CrossoverStrategy<T> extends GeneticStrategy {
    public abstract Pair<Genome<T>, Genome<T>> crossover(Genome<T> parent1, Genome<T> parent2);
}