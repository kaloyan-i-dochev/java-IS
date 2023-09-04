package nqueens.genetic;

public interface TerminationCondition<T> {
	default boolean shouldTerminate(Population<T> population, int generations, FitnessFunction<T> fitnessFunction) {
        Genome<T> fittest = fitnessFunction.getFittest(population);
        return fitnessFunction.calculate(fittest) == 0;
    }
}
