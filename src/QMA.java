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

class Inch {

    private final double value;

    public Inch(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Inch other = (Inch) obj;

        return Double.compare(this.value, other.value) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}

class QuantityMeasurementTests {

    // FEET TESTS
    static void testFeet_SameValue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println(
                "Feet Same Value -> " +
                        f1.getValue() + " ft vs " +
                        f2.getValue() + " ft => " +
                        f1.equals(f2)
        );
    }

    static void testFeet_DifferentValue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(2.0);

        System.out.println(
                "Feet Different Value -> " +
                        f1.getValue() + " ft vs " +
                        f2.getValue() + " ft => " +
                        f1.equals(f2)
        );
    }

    // INCH TESTS
    static void testInch_SameValue() {
        Inch i1 = new Inch(1.0);
        Inch i2 = new Inch(1.0);

        System.out.println(
                "Inch Same Value -> " +
                        i1.getValue() + " in vs " +
                        i2.getValue() + " in => " +
                        i1.equals(i2)
        );
    }

    static void testInch_DifferentValue() {
        Inch i1 = new Inch(1.0);
        Inch i2 = new Inch(2.0);

        System.out.println(
                "Inch Different Value -> " +
                        i1.getValue() + " in vs " +
                        i2.getValue() + " in => " +
                        i1.equals(i2)
        );
    }

    static void runAllTests() {
        System.out.println("===== FEET TESTS =====");
        testFeet_SameValue();
        testFeet_DifferentValue();

        System.out.println("\n===== INCH TESTS =====");
        testInch_SameValue();
        testInch_DifferentValue();
    }
}

public class QMA {

    public static void main(String[] args) {

        System.out.println("===== Quantity Measurement App =====\n");

        QuantityMeasurementTests.runAllTests();
    }
}