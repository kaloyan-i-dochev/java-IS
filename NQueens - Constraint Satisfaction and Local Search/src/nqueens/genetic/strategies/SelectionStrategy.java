package nqueens.genetic.strategies;

import nqueens.genetic.Population;

public abstract class SelectionStrategy<T> extends GeneticStrategy {
    public abstract Population<T> select(Population<T> population);
}
