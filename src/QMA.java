enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0);

    private final double conversionFactorToFeet;

    LengthUnit(double conversionFactorToFeet) {
        this.conversionFactorToFeet = conversionFactorToFeet;
    }

    public double convertToFeet(double value) {
        return value * conversionFactorToFeet;
    }
}

class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValueInFeet() {
        return unit.convertToFeet(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        return Double.compare(this.getValueInFeet(), other.getValueInFeet()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(getValueInFeet());
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
}

class QuantityMeasurementTests {

    static void testFeetToFeet_SameValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        System.out.println("Feet vs Feet (Same) -> " +
                q1.getValue() + " ft vs " +
                q2.getValue() + " ft => " +
                q1.equals(q2));
    }

    static void testFeetToFeet_DifferentValue() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);

        System.out.println("Feet vs Feet (Different) -> " +
                q1.getValue() + " ft vs " +
                q2.getValue() + " ft => " +
                q1.equals(q2));
    }

    static void testInchToInch_SameValue() {
        QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Inch vs Inch (Same) -> " +
                q1.getValue() + " in vs " +
                q2.getValue() + " in => " +
                q1.equals(q2));
    }

    static void testInchToFeet_Equivalent() {
        QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);

        System.out.println("12 inch vs 1 feet -> " +
                q1.getValue() + " in vs " +
                q2.getValue() + " ft => " +
                q1.equals(q2));
    }

    static void runAllTests() {

        testFeetToFeet_SameValue();
        testFeetToFeet_DifferentValue();
        testInchToInch_SameValue();
        testInchToFeet_Equivalent();
    }
}

public class QMA {

    public static void main(String[] args) {

        QuantityMeasurementTests.runAllTests();
    }
}