package neuralnetwork.training;

/**
 * Implements a step decay learning rate scheduler.
 * <p>
 * The step decay learning rate scheduler starts with an initial learning rate and reduces it 
 * by a factor (decay rate) after a specified number of epochs (step size). This approach allows 
 * the model to start with a higher learning rate for faster convergence and then reduce the 
 * learning rate for fine-tuning.
 * </p>
 */
public class StepDecayScheduler implements LearningRateScheduler {

    private double initialRate;
    private double decayRate;
    private int stepSize;

    /**
     * Constructs a new StepDecayScheduler with the specified parameters.
     *
     * @param initialRate The initial learning rate to start with.
     * @param decayRate   The factor by which the learning rate should be reduced.
     * @param stepSize    The number of epochs after which the learning rate should be reduced.
     */
    public StepDecayScheduler(double initialRate, double decayRate, int stepSize) {
        this.initialRate = initialRate;
        this.decayRate = decayRate;
        this.stepSize = stepSize;
    }

    /**
     * Computes the learning rate for the current epoch and session.
     * <p>
     * The learning rate is calculated as:
     * <code>initialRate * Math.pow(decayRate, Math.floor((1 + currentEpoch) / stepSize))</code>
     * </p>
     *
     * @param currentEpoch   The current epoch number.
     * @param totalEpochs    The total number of epochs.
     * @param currentSession The current session number.
     * @param totalSessions  The total number of sessions.
     * @param currentError   The current error value.
     * @return The computed learning rate for the current epoch and session.
     */
    @Override
    public double getLearningRate(int currentEpoch, int totalEpochs, int currentSession, int totalSessions, double currentError) {
        return initialRate * Math.pow(decayRate, Math.floor((1 + currentEpoch) / stepSize));
    }
    
    @Override
    public String getDescription() {
        return getClass().getSimpleName() + " [initialRate=" + initialRate + ", decayRate=" + decayRate + ", stepSize=" + stepSize + "]";
    }
}
