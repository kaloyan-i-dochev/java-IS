package neuralnetwork.training;

public class ConstantLRScheduler implements LearningRateScheduler {

    private double rate;
    
    public ConstantLRScheduler(double rate) {
        this.rate = rate;
    }

	@Override
	public double getLearningRate(int currentEpoch, int totalEpochs, int currentSession, int totalSessions,
			double currentError) {
		return rate;
	}
	
    @Override
    public String getDescription() {
        return getClass().getSimpleName() + " [rate=" + rate + "]";
    }
}
