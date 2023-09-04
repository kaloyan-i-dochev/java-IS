package neuralnetwork.functions.termination;

import neuralnetwork.training.Trainer;

public class NoImprovementTermination implements TerminationCondition {
    private final int maxEpochsWithoutImprovement;
    private int lastImprovementEpoch = 0;
    private double lastBestAccuracy = 0.0;

    public NoImprovementTermination(int maxEpochsWithoutImprovement) {
        this.maxEpochsWithoutImprovement = maxEpochsWithoutImprovement;
    }

    @Override
    public boolean shouldTerminate(Trainer trainer) {
        double currentAccuracy = trainer.getLatestAccuracy();
        if (currentAccuracy > lastBestAccuracy) {
            lastBestAccuracy = currentAccuracy;
            lastImprovementEpoch = trainer.getCurrentTotalEpochs();
        }
        return (trainer.getCurrentTotalEpochs() - lastImprovementEpoch) >= maxEpochsWithoutImprovement;
    }
}
