package simple;

import common.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class SimpleGeneticAlgorithmTest {

	private SimpleGeneticAlgorithm ga;
	private FitnessFunction<City> fitnessFunction;
	private int cityCount;
	private int populationSize;
	private int maxGenerations;
	private int iterations;

	@Before
	public void setUp() {
		ga = new SimpleGeneticAlgorithm();
		fitnessFunction = new TSPFitnessFunction(); // Assuming TSPFitnessFunction is implemented
		cityCount = 10;
		populationSize = 10;
		maxGenerations = 1000;
		iterations = 100;
	}

	@Test
	public void testRunWithPregeneratedPopulation() {
		CityManager cityManager = CityManager.getInstance();
		long startTime = System.currentTimeMillis();
		int improvements = 0;
		int totalImprovement = 0;

		for (int i = 0; i < iterations; i++) {
			long iterationStartTime = System.currentTimeMillis();
			Population<City> initialPopulation = generateInitialPopulation(cityCount);
			int initialFittestScore = fitnessFunction.calculate(fitnessFunction.getFittest(initialPopulation));

//	        System.out.println("Iteration: " + i + ", Time: " + iterationStartTime + " ms, Initial Best Score: " + initialFittestScore);

			Population<City> finalPopulation = ga.run(initialPopulation, maxGenerations);
			Route finalFittest = (Route) fitnessFunction.getFittest(finalPopulation);
			int finalFittestScore = fitnessFunction.calculate(finalFittest);

			long iterationEndTime = System.currentTimeMillis();
			double iterationTime = iterationEndTime - iterationStartTime;

			int improvement = initialFittestScore - finalFittestScore;
			totalImprovement += improvement;
			if (improvement > 0) // Expecting improvement or same fitness
				improvements++;

//			System.out.println("Iteration: " + i + ", Result: " + (finalFittestScore <= initialFittestScore)
//					+ ", Initial Best Score: " + initialFittestScore + " , Final Best Score: " + finalFittestScore
//					+ ", Iteration Time: " + iterationTime + " ms");
			assertTrue(finalFittest.isValidSolution());
		}

		long endTime = System.currentTimeMillis();
		double totalTime = (endTime - startTime);

		System.out.println("Configuration: iterations=" + iterations + ", cityCount=" + cityCount + ", populationSize="
				+ populationSize + ", maxGenerations=" + maxGenerations);
		System.out.println("Total time: " + totalTime + " ms");
		System.out.println("Average time: " + totalTime / (double) iterations + " ms");
		System.out.println("Improvement rate: " + improvements / (double) iterations);
		System.out.println("Average improvement: " + totalImprovement / (double) iterations);
		// Additional solution statistics can be printed here
	}

	private Population<City> generateInitialPopulation(int cityCount) {
		Population<City> population = new Population<>();
		CityManager cityManager = CityManager.getInstance();
		cityManager.clearCities();
		for (int i = 0; i < cityCount; i++) {
			City city = cityManager.generateCity(cityCount, cityCount); // Assuming
																		// generateCity
																		// method is
																		// implemented
			cityManager.addCity(city);
		}
		for (int i = 0; i < populationSize; i++) {
			Route route = new Route();
			route.generateRandomRoute(); // Assuming this method is implemented in Route
			population.addIndividual(route);
		}
		return population;
	}

	// Additional tests for other methods and scenarios in the GeneticAlgorithm
	// class
}
