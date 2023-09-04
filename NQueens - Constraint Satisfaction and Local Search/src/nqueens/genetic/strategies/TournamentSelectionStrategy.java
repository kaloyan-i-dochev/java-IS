package nqueens.genetic.strategies;

import nqueens.genetic.FitnessFunction;
import nqueens.genetic.Genome;
import nqueens.genetic.Population;

import java.util.Random;

public class TournamentSelectionStrategy<T> extends SelectionStrategy<T> {
    private int tournamentSize;
    private FitnessFunction<T> fitnessFunction;

    public TournamentSelectionStrategy(int tournamentSize, FitnessFunction<T> fitnessFunction) {
        this.tournamentSize = tournamentSize;
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public Population<T> select(Population<T> population) {
        Population<T> selected = new Population<>();
        Random random = new Random();

        // Repeat until we have selected enough individuals
        while (selected.getSize() < population.getSize()) {
            Population<T> tournament = new Population<>();

            // Randomly select tournamentSize individuals from the population
            for (int i = 0; i < tournamentSize; i++) {
                int randomId = random.nextInt(population.getSize());
                tournament.addIndividual(population.getIndividual(randomId));
            }

            Genome<T> fittest = fitnessFunction.getFittest(tournament);

            // Add the fittest individual to the selected population
            selected.addIndividual(fittest);
        }

        return selected;
    }
}

