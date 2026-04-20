class Feet {

    private final double value;

    public Feet(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Feet other = (Feet) obj;

        return Double.compare(this.value, other.value) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}

class QuantityMeasurementTests {

    static void testEquality_SameValue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println(
                "Same Value Test -> " +
                        f1.getValue() + " ft and " +
                        f2.getValue() + " ft => " +
                        f1.equals(f2)
        );
    }

    static void testEquality_DifferentValue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(2.0);

        System.out.println(
                "Different Value Test -> " +
                        f1.getValue() + " ft and " +
                        f2.getValue() + " ft => " +
                        f1.equals(f2)
        );
    }

    static void testEquality_NullComparison() {
        Feet f1 = new Feet(1.0);

        System.out.println(
                "Null Comparison Test -> " +
                        f1.getValue() + " ft and null => " +
                        f1.equals(null)
        );
    }

    static void testEquality_SameReference() {
        Feet f1 = new Feet(1.0);

        System.out.println(
                "Same Reference Test -> " +
                        f1.getValue() + " ft and itself => " +
                        f1.equals(f1)
        );
    }

    static void runAllTests() {
        testEquality_SameValue();
        testEquality_DifferentValue();
        testEquality_NullComparison();
        testEquality_SameReference();
    }
}

public class QMA {
    public static void main(String[] args) {

        System.out.println("===== Quantity Measurement Test Run =====\n");

        QuantityMeasurementTests.runAllTests();
    }
}