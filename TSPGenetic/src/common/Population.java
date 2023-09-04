package common;

import java.util.ArrayList;
import java.util.List;

public class Population<T> {
    private List<Genome<T>> individuals;

    public Population() {
        this.individuals = new ArrayList<>();
    }

    public Population(List<Genome<T>> individuals) {
        this.individuals = new ArrayList<>(individuals);
    }

    // Copy constructor
    public Population(Population<T> other) {
        this.individuals = new ArrayList<>();
        for (Genome<T> individual : other.individuals) {
            this.individuals.add(individual.clone());
        }
    }

    public void addIndividual(Genome<T> individual) {
        this.individuals.add(individual);
    }

    public void addIndividuals(List<Genome<T>> individuals) {
        this.individuals.addAll(individuals);
    }

    public List<Genome<T>> getIndividuals() {
        return this.individuals;
    }

    public Genome<T> getIndividual(int index) {
        return this.individuals.get(index);
    }

    public void setIndividual(int index, Genome<T> individual) {
        this.individuals.set(index, individual);
    }

    public void setIndividuals(List<Genome<T>> individuals) {
        this.individuals = new ArrayList<>(individuals);
    }

    public void removeIndividual(Genome<T> individual) {
        this.individuals.remove(individual);
    }

    public int getSize() {
        return this.individuals.size();
    }
}
