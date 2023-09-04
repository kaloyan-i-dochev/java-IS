package nqueens.genetic.strategies;

import nqueens.genetic.Genome;

public abstract class MutationStrategy<T> extends GeneticStrategy {
    public abstract void mutate(Genome<T> individual);
}
