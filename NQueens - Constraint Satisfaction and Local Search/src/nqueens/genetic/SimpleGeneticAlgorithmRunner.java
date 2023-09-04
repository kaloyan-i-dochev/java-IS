package nqueens.genetic;

import nqueens.genetic.strategies.CrossoverStrategy;
import nqueens.genetic.strategies.MutationStrategy;
import nqueens.genetic.strategies.SelectionStrategy;

public class SimpleGeneticAlgorithmRunner<T> implements GeneticAlgorithmRunner<T> {
	private SelectionStrategy<T> selectionStrategy;
	private CrossoverStrategy<T> crossoverStrategy;
	private MutationStrategy<T> mutationStrategy;
	private FitnessFunction<T> fitnessFunction;
    private TerminationCondition<T> terminationCondition;

	public SimpleGeneticAlgorithmRunner(SelectionStrategy<T> selectionStrategy, CrossoverStrategy<T> crossoverStrategy,
			MutationStrategy<T> mutationStrategy, FitnessFunction<T> fitnessFunction, TerminationCondition<T> terminationCondition) {
		this.selectionStrategy = selectionStrategy;
		this.crossoverStrategy = crossoverStrategy;
		this.mutationStrategy = mutationStrategy;
		this.fitnessFunction = fitnessFunction;
        this.terminationCondition = terminationCondition;
	}

	public Population<T> run(Population<T> initialPopulation, int maxGenerations) {
		// Initialize population
		Population<T> population = new Population<>(initialPopulation);

		// Evolve population over generations
		for (int generation = 0; generation < maxGenerations; generation++) {
			// If termination condition is met, stop the evolution process
	        if (terminationCondition != null && terminationCondition.shouldTerminate(population, generation, fitnessFunction)) {
	            break;
	        }
	        
			// Selection
			Population<T> selected = selectionStrategy.select(population);
			// Broken by ElitismSelStrat.
			// Will switch to Phase design in next project.

			// Crossover and Mutation
			Population<T> offspring = new Population<>();
			int n = selected.getSize() - 1;
			for (int i = 0; i < n; i += 2) {
				Genome<T> parent1 = selected.getIndividual(i);
				Genome<T> parent2 = selected.getIndividual(i + 1);

				// Crossover
				Pair<Genome<T>, Genome<T>> children = crossoverStrategy.crossover(parent1, parent2);

				// Mutation
				mutationStrategy.mutate(children.getFirst());
				mutationStrategy.mutate(children.getSecond());

				// Add children to offspring population
				offspring.addIndividual(children.getFirst());
				offspring.addIndividual(children.getSecond());
			}

			// If there's an odd number of individuals, add the last one without crossover
			if (selected.getSize() % 2 != 0) {
				offspring.addIndividual(selected.getIndividual(n));
			}

			// Replace population with offspring
			population = offspring;
			System.out.println("generation: " + generation);
			System.out.println("fitness: " + fitnessFunction.calculate(population));
			System.out.println("average: " + fitnessFunction.getAverageFitness(population));
			System.out.println();
		}

		return population;
	}

}
