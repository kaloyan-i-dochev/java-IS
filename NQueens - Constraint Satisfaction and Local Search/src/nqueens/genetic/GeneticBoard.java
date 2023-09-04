package nqueens.genetic;

import java.util.Random;

import nqueens.boards.SingleDimensionBoard;

public class GeneticBoard extends SingleDimensionBoard implements Genome<Integer> {
	private static Random random = new Random();

    public GeneticBoard(int size) {
        super(size);
    }

    @Override
    public Integer getGene(int index) {
        return getQueen(index);
    }

    @Override
    public void setGene(int index, Integer value) {
        placeQueen(index, value);
    }

    @Override
    public GeneticBoard clone() {
        GeneticBoard clone = new GeneticBoard(getSize());
        for (int i = 0; i < getSize(); i++) {
            clone.setGene(i, getGene(i));
        }
        return clone;
    }

    public void randomize() {
        for (int i = 0; i < getSize(); i++) {
            setGene(i, random.nextInt(getSize()));
        }
    }
}

