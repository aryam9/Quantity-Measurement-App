import java.util.function.DoubleBinaryOperator;

interface IMeasurable {
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
}

enum LengthUnit implements IMeasurable {
    INCH(1.0),
    FEET(12.0),
    YARD(36.0),
    CENTIMETER(0.393701);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

enum WeightUnit implements IMeasurable {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

enum VolumeUnit implements IMeasurable {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double factor;

    VolumeUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

enum ArithmeticOperation {
    ADD((a, b) -> a + b),
    SUBTRACT((a, b) -> a - b),
    DIVIDE((a, b) -> {
        if (b == 0) throw new ArithmeticException();
        return a / b;
    });

    private final DoubleBinaryOperator op;

    ArithmeticOperation(DoubleBinaryOperator op) {
        this.op = op;
    }

    public double compute(double a, double b) {
        return op.applyAsDouble(a, b);
    }
}

class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
        this.unit = unit;
    }

    private void validate(Quantity<U> other) {
        if (other == null || other.unit == null) {
            throw new IllegalArgumentException();
        }
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException();
        }
        if (Double.isNaN(other.value) || Double.isInfinite(other.value)) {
            throw new IllegalArgumentException();
        }
    }

    private double baseValue(Quantity<U> q) {
        return q.unit.convertToBaseUnit(q.value);
    }

    private double operate(Quantity<U> other, ArithmeticOperation op) {
        validate(other);
        return op.compute(baseValue(this), baseValue(other));
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U target) {
        if (target == null) throw new IllegalArgumentException();
        double result = operate(other, ArithmeticOperation.ADD);
        double converted = target.convertFromBaseUnit(result);
        return new Quantity<>(converted, target);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U target) {
        if (target == null) throw new IllegalArgumentException();
        double result = operate(other, ArithmeticOperation.SUBTRACT);
        double converted = target.convertFromBaseUnit(result);
        return new Quantity<>(converted, target);
    }

    public double divide(Quantity<U> other) {
        return operate(other, ArithmeticOperation.DIVIDE);
    }

    public Quantity<U> convertTo(U target) {
        if (target == null) throw new IllegalArgumentException();
        double base = unit.convertToBaseUnit(value);
        double converted = target.convertFromBaseUnit(base);
        return new Quantity<>(converted, target);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quantity<?> other = (Quantity<?>) obj;
        if (!this.unit.getClass().equals(other.unit.getClass())) return false;
        return Double.compare(
                this.unit.convertToBaseUnit(this.value),
                other.unit.convertToBaseUnit(other.value)
        ) == 0;
    }

    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    public String toString() {
        return value + " " + unit.getUnitName();
    }
}

class QuantityMeasurementApp {
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException();
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException();
        }
        double baseValue = source.convertToBaseUnit(value);
        return target.convertFromBaseUnit(baseValue);
    }
}

public class QMA {
    public static void main(String[] args) {
        System.out.println(QuantityMeasurementApp.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));
        System.out.println(QuantityMeasurementApp.convert(3.0, LengthUnit.YARD, LengthUnit.FEET));
        System.out.println(QuantityMeasurementApp.convert(36.0, LengthUnit.INCH, LengthUnit.YARD));

        Quantity<LengthUnit> l1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6.0, LengthUnit.INCH);

        System.out.println(l1.subtract(l2));
        System.out.println(l1.add(l2));
        System.out.println(l1.divide(l2));
    }
}