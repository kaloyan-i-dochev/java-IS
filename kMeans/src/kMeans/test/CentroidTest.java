package kMeans.test;

import kMeans.Centroid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import common.*;

public class CentroidTest {

    /**
     * Provides test data for parametric tests.
     *
     * @return Stream of test data.
     */
    private static Stream<Object[]> provideTestDataForMoveToAverage() {
        return Stream.of(
            // Test case 1: Two 2D points
            new Object[] {
                Arrays.asList(new Point(new double[]{1, 1}), new Point(new double[]{3, 3})),
                new double[]{2, 2}
            },
            // Test case 2: Three 2D points
            new Object[] {
                Arrays.asList(new Point(new double[]{1, 2}), new Point(new double[]{3, 4}), new Point(new double[]{5, 6})),
                new double[]{3, 4}
            },
            // Test case 3: Empty list
            new Object[] {
                Arrays.asList(),
                new double[]{0, 0}
            },
            // Test case 4: Single 3D point
            new Object[] {
                Arrays.asList(new Point(new double[]{1, 2, 3})),
                new double[]{1, 2, 3}
            }
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForMoveToAverage")
    public void testMoveToAverageOfPoints(List<Point> points, double[] expectedCoordinates) {
        Centroid centroid = new Centroid(new double[expectedCoordinates.length]);
        centroid.moveToAverageOfPoints(points);

        Assertions.assertArrayEquals(expectedCoordinates, centroid.getCoordinates(), 0.001, "\nExpected coordinates: " + Arrays.toString(expectedCoordinates) + "\nCentroid coordinates: " + centroid + "\n");
    }
}
