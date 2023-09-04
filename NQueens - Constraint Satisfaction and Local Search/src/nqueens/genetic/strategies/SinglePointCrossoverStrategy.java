package nqueens.genetic.strategies;

import nqueens.genetic.Genome;
import nqueens.genetic.Pair;

import java.util.Random;

public class SinglePointCrossoverStrategy<T> extends CrossoverStrategy<T> {
    private Random random = new Random();

    @Override
    public Pair<Genome<T>, Genome<T>> crossover(Genome<T> parent1, Genome<T> parent2) {
        Genome<T> child1 = parent1.clone(); // Assuming Genome has a clone method
        Genome<T> child2 = parent2.clone(); // Clone parent2 as well

        // Choose a random crossover point
        int crossoverPoint = random.nextInt(parent1.getSize());

        // Swap genes between child1 and child2 after the crossover point
        for (int i = crossoverPoint; i < parent1.getSize(); i++) {
            T temp = child1.getGene(i);
            child1.setGene(i, child2.getGene(i));
            child2.setGene(i, temp);
        }

        return new Pair<>(child1, child2);
    }
}
