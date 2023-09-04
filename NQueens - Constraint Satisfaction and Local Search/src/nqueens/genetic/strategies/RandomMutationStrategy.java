package nqueens.genetic.strategies;

import nqueens.genetic.GeneGenerator;
import nqueens.genetic.Genome;
import java.util.Random;

public class RandomMutationStrategy<T> extends MutationStrategy<T> {
    private Random random = new Random();
    private GeneGenerator<T> geneGenerator;

    public RandomMutationStrategy(GeneGenerator<T> geneGenerator) {
        this.geneGenerator = geneGenerator;
    }

    @Override
    public void mutate(Genome<T> genome) {
        int geneIndex = random.nextInt(genome.getSize());
        genome.setGene(geneIndex, geneGenerator.generate());
    }
}