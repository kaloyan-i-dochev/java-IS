package neuralnetwork.training;

public interface LearningRateScheduler {

    /**
     * Determines the learning rate based on the provided parameters.
     *
     * @param currentEpoch The current training epoch.
     * @param totalEpochs The total number of epochs for training.
     * @param currentSession The current training session.
     * @param totalSessions The total number of training sessions.
     * @param currentError The current error or loss value.
     * @return The learning rate for the current epoch/session.
     */
    double getLearningRate(int currentEpoch, int totalEpochs, int currentSession, int totalSessions, double currentError);
    String getDescription();
}
