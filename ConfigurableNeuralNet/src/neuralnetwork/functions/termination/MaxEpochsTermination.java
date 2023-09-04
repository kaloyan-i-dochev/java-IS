package neuralnetwork.functions.termination;

import neuralnetwork.training.Trainer;

public class MaxEpochsTermination implements TerminationCondition {
    private final int maxEpochs;

    public MaxEpochsTermination(int maxEpochs) {
        this.maxEpochs = maxEpochs;
    }

    @Override
    public boolean shouldTerminate(Trainer trainer) {
        return trainer.getCurrentTotalEpochs() >= maxEpochs;
    }
}
