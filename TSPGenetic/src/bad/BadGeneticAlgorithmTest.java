package bad;

import common.*;

import org.junit.Before;
import org.junit.Test;

public class BadGeneticAlgorithmTest {

	private PurposefullyBadGeneticAlgorithm ga;
	private FitnessFunction<City> fitnessFunction;
	private int cityCount;
	private int populationSize;
	private int maxGenerations;
	private int iterations;

	@Before
	public void setUp() {
		ga = new PurposefullyBadGeneticAlgorithm();
		fitnessFunction = new TSPFitnessFunction(); // Assuming TSPFitnessFunction is implemented
		cityCount = 10;
		populationSize = 100;
		maxGenerations = 1000;
		iterations = 100;
	}

	@Test
	public void testRunWithPregeneratedPopulation() {
		long startTime = System.currentTimeMillis();
		int improvements = 0;
//		int solutions = 0;
		for (int i = 0; i < iterations; i++) {
			Population<City> initialPopulation = generateInitialPopulation(cityCount);
			int initialFittestScore = fitnessFunction.calculate(fitnessFunction.getFittest(initialPopulation));

			Population<City> finalPopulation = ga.run(cityCount, initialPopulation, maxGenerations);
			Route finalFittest = (Route) fitnessFunction.getFittest(finalPopulation);
			int finalFittestScore = fitnessFunction.calculate(finalFittest);

			if (finalFittestScore <= initialFittestScore) // Expecting improvement or same fitness
				improvements++;
//			if (finalFittestScore == 0)
//				solutions++; 
		}
		long endTime = System.currentTimeMillis();
		double timeImprovement = (endTime - startTime) / (double) iterations;

		System.out.println("Configuration: iterations=" + iterations + ", cityCount=" + cityCount + ", populationSize=" + populationSize
				+ ", maxGenerations=" + maxGenerations);
		System.out.println("Average time: " + timeImprovement + " ms");
		System.out.println("Improvement rate: " + improvements / (double) iterations);
//		System.out.println("Solution rate: " + solutions / (double) iterations);
		// Additional solution statistics can be printed here
	}

	private Population<City> generateInitialPopulation(int cityCount) {
		Population<City> population = new Population<>();
		CityManager.getInstance().clearCities();
		for (int i = 0; i < cityCount; i++) {
			City city = CityManager.getInstance().generateCity(cityCount, cityCount); // Assuming
																						// generateCity
																						// method is
																						// implemented
			CityManager.getInstance().addCity(city);
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
