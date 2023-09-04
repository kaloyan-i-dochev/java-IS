package nqueens.genetic.solvers;

import nqueens.genetic.ConflictingQueensFitnessFunction;
import nqueens.genetic.FitnessFunction;
import nqueens.genetic.GeneticBoard;
import nqueens.genetic.GeneticSolver;
import nqueens.genetic.IntegerGeneGenerator;
import nqueens.genetic.Population;
import nqueens.genetic.SimpleGeneticAlgorithmRunner;
import nqueens.genetic.TerminationCondition;
import nqueens.genetic.strategies.CrossoverStrategy;
import nqueens.genetic.strategies.MutationStrategy;
import nqueens.genetic.strategies.RandomMutationStrategy;
import nqueens.genetic.strategies.SelectionStrategy;
import nqueens.genetic.strategies.SinglePointCrossoverStrategy;
import nqueens.genetic.strategies.TournamentSelectionStrategy;

public class SimpleGeneticSolver implements GeneticSolver<Integer> {
	private int boardSize;
	private int populationSize;
	private int maxGenerations;

	public SimpleGeneticSolver(int boardSize, int populationSize, int maxGenerations) {
		this.boardSize = boardSize;
		this.populationSize = populationSize;
		this.maxGenerations = maxGenerations;
	}

	public GeneticBoard solve() {
		// Initialize strategies
		int tournamentSize = Math.max(3, populationSize / 10);
		SelectionStrategy<Integer> selectionStrategy = new TournamentSelectionStrategy<>(tournamentSize,
				new ConflictingQueensFitnessFunction());
		CrossoverStrategy<Integer> crossoverStrategy = new SinglePointCrossoverStrategy<>();
		MutationStrategy<Integer> mutationStrategy = new RandomMutationStrategy<>(new IntegerGeneGenerator(boardSize));
		FitnessFunction<Integer> fitnessFunction = new ConflictingQueensFitnessFunction();// Initialize termination condition
		TerminationCondition<Integer> terminationCondition = new TerminationCondition<>() {};


		// Initialize genetic algorithm runner
		SimpleGeneticAlgorithmRunner<Integer> geneticAlgorithm = new SimpleGeneticAlgorithmRunner<>(selectionStrategy,
				crossoverStrategy, mutationStrategy, fitnessFunction, terminationCondition);

		// Initialize initial population
		Population<Integer> initialPopulation = new Population<>();
		for (int i = 0; i < populationSize; i++) {
			GeneticBoard individual = new GeneticBoard(boardSize);
			individual.randomize();
			initialPopulation.addIndividual(individual);
		}

		// Run the genetic algorithm
		Population<Integer> finalPopulation = geneticAlgorithm.run(initialPopulation, maxGenerations);

		// Return the fittest individual from the final population
//		return (GeneticBoard) getFittestIndividual(finalPopulation);
		return (GeneticBoard) fitnessFunction.getFittest(finalPopulation);
	}
}
