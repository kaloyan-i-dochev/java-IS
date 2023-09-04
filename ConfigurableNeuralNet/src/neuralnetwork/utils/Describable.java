package neuralnetwork.utils;

public interface Describable {
    default String getDescription() {
    	return getClass().getSimpleName();
    }
}
