package common;

public interface Genome<T> {
    int getSize();
    T getGene(int index);
    void setGene(int index, T allele);
	Genome<T> clone();
}
