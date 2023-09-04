package neuralnetwork.functions.termination;

import neuralnetwork.training.Trainer;

public class TargetAccuracyTermination implements TerminationCondition {
    private final double targetAccuracy;

    public TargetAccuracyTermination(double targetAccuracy) {
        this.targetAccuracy = targetAccuracy;
    }

    @Override
    public boolean shouldTerminate(Trainer trainer) {
        return trainer.getLatestAccuracy() >= targetAccuracy;
    }
}
