package nqueens.genetic;

public class ConflictingQueensFitnessFunction implements FitnessFunction<Integer> {
    @Override
    public int calculate(Genome<Integer> individual) {
        GeneticBoard board = (GeneticBoard) individual;
        int conflictingPairs = 0;
        int size = board.getSize();

        // Check each pair of queens
        for (int i = 0; i < size; i++) {
            if (!board.hasQueen(i)) continue;
            int queen1 = board.getQueen(i);
            for (int j = i + 1; j < size; j++) {
                if (!board.hasQueen(j)) continue;
                int queen2 = board.getQueen(j);

                // Check if queens are on the same row
                if (queen1 == queen2) {
                    conflictingPairs++;
                }

                // Check if queens are on the same diagonal
                if (Math.abs(queen1 - queen2) == Math.abs(i - j)) {
                    conflictingPairs++;
                }
            }
        }

        return conflictingPairs;
    }
    
    @Override
    public int compare(Genome<Integer> individual1, Genome<Integer> individual2) {
        int fitness1 = calculate(individual1);
        int fitness2 = calculate(individual2);

        // By default, assume that lower values are better.
        // Override this method in implementations where higher values are better.
        return Integer.compare(fitness1, fitness2);
    }
}
