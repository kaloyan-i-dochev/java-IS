package common.test;

import common.Point;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PointTest {

    /**
     * Provides test data for parametric tests of distanceTo method.
     *
     * @return Stream of test data.
     */
    private static Stream<Object[]> provideTestDataForDistanceTo() {
        return Stream.of(
            // Test case 1: Two 2D points
            new Object[] {
                new Point(new double[]{1, 1}),
                new Point(new double[]{3, 3}),
                Math.sqrt(8)
            },
            // Test case 2: Two 3D points
            new Object[] {
                new Point(new double[]{1, 2, 3}),
                new Point(new double[]{4, 5, 6}),
                Math.sqrt(27)
            },
            // Test case 3: Two points with same coordinates
            new Object[] {
                new Point(new double[]{1, 2, 3}),
                new Point(new double[]{1, 2, 3}),
                0.0
            }
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForDistanceTo")
    public void testDistanceTo(Point point1, Point point2, double expectedDistance) {
        double actualDistance = point1.distanceTo(point2);
        Assertions.assertEquals(expectedDistance, actualDistance, 0.001,
            "Expected distance between " + point1 + " and " + point2 + " to be " + expectedDistance + " but got " + actualDistance);
    }
}
