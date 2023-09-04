package nqueens.genetic;

public interface Genome<T> {
    int getSize();
    T getGene(int index);
    void setGene(int index, T value);
	Genome<T> clone();
}
