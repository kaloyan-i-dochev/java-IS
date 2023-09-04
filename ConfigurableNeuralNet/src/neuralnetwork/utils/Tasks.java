package neuralnetwork.utils;

public enum Tasks {
	// @formatter:off
	ENCODED(new double[][]{
        {0, 0, 0},
        {0, 0, 1},
        {0, 1, 0},
        {0, 1, 1},
        {1, 0, 0},
        {1, 0, 0},
        {1, 1, 0},
        {1, 1, 1},
    }, new double[][]{
        {0},
        {1},
        {1},
        {1},
        {0},
        {0},
        {0},
        {1},
    }),	
	AND(new double[][]{
        {0, 0},
        {0, 1},
        {1, 0},
        {1, 1}
    }, new double[][]{
        {0},
        {0},
        {0},
        {1}
    }),
    OR(new double[][]{
        {0, 0},
        {0, 1},
        {1, 0},
        {1, 1}
    }, new double[][]{
        {0},
        {1},
        {1},
        {1}
    }),
    NAND(new double[][]{
        {0, 0},
        {0, 1},
        {1, 0},
        {1, 1}
    }, new double[][]{
        {1},
        {1},
        {1},
        {0}
    }),
    NOR(new double[][]{
        {0, 0},
        {0, 1},
        {1, 0},
        {1, 1}
    }, new double[][]{
        {1},
        {0},
        {0},
        {0}
    }),
    XOR(new double[][]{
        {0, 0},
        {0, 1},
        {1, 0},
        {1, 1}
    }, new double[][]{
        {0},
        {1},
        {1},
        {0}
    }),
    XNOR(new double[][]{
        {0, 0},
        {0, 1},
        {1, 0},
        {1, 1}
    }, new double[][]{
        {1},
        {0},
        {0},
        {1}
    });
	// @formatter:on

	private final double[][] inputData;
	private final double[][] outputData;

	Tasks(double[][] inputData, double[][] outputData) {
		this.inputData = inputData;
		this.outputData = outputData;
	}

	public double[][] getInputData() {
		return inputData;
	}

	public double[][] getOutputData() {
		return outputData;
	}
}
