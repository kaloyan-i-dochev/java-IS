package neuralnetwork.utils;

public enum NetworkLayouts {
    MINIMAL(new int[]{2, 1}),
    SIMPLE(new int[]{2, 2, 1}),
    DEEP(new int[]{2, 4, 4, 4, 1}),
    WIDE(new int[]{2, 8, 8, 1}),
    MIXED(new int[]{2, 5, 7, 3, 1}),
    CONDITIONAL(new int[]{3, 6, 6, 6, 1});

    private final int[] layout;

    NetworkLayouts(int[] layout) {
        this.layout = layout;
    }

    public int[] getLayout() {
        return layout;
    }
}
