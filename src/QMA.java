enum LengthUnit {

    INCH(1.0),
    FEET(12.0),
    YARD(36.0),
    CENTIMETER(0.393701);

    private final double toInchFactor;

    LengthUnit(double toInchFactor) {
        this.toInchFactor = toInchFactor;
    }

    public double convertToInch(double value) {
        return value * toInchFactor;
    }
}

class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValueInInch() {
        return unit.convertToInch(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        return Double.compare(this.getValueInInch(), other.getValueInInch()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(getValueInInch());
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
}

class QuantityMeasurementTests {

    static void testYardToFeet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("1 Yard vs 3 Feet => " + q1.equals(q2));
    }

    static void testYardToInch() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCH);

        System.out.println("1 Yard vs 36 Inch => " + q1.equals(q2));
    }

    static void testCentimeterToInch() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETER);
        QuantityLength q2 = new QuantityLength(0.393701, LengthUnit.INCH);

        System.out.println("1 cm vs 0.393701 inch => " + q1.equals(q2));
    }

    static void testComplexCase() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARD);
        QuantityLength q2 = new QuantityLength(72.0, LengthUnit.INCH);

        System.out.println("2 Yard vs 72 Inch => " + q1.equals(q2));
    }

    static void runAllTests() {

        testYardToFeet();
        testYardToInch();
        testCentimeterToInch();
        testComplexCase();
    }
}

public class QMA {

    public static void main(String[] args) {

        QuantityMeasurementTests.runAllTests();
    }
}