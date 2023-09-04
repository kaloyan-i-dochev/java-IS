package neuralnetwork.functions.termination;

import neuralnetwork.training.Trainer;

/**
 * Interface to define various termination conditions for training.
 */
public interface TerminationCondition {

    /**
     * Checks whether the training should be terminated based on the provided trainer's state.
     *
     * @param trainer The trainer instance containing the current state of training.
     * @return true if the training should be terminated, false otherwise.
     */
    boolean shouldTerminate(Trainer trainer);
}
