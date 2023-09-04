package nqueens.genetic;

import java.util.Random;

public class IntegerGeneGenerator implements GeneGenerator<Integer> {
    Random random;
    private int max;

    public IntegerGeneGenerator(int max) {
    	random = new Random();
        this.max = max;
    }

    @Override
    public Integer generate() {
		return random.nextInt(max);
    }
}
