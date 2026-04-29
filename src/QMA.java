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

    public Quantity<U> convertTo(U target) {
        if (target == null) throw new IllegalArgumentException();
        double base = unit.convertToBaseUnit(value);
        double converted = target.convertFromBaseUnit(base);
        return new Quantity<>(converted, target);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U target) {
        validate(other, target);
        double sumBase = this.unit.convertToBaseUnit(this.value)
                + other.unit.convertToBaseUnit(other.value);
        double result = target.convertFromBaseUnit(sumBase);
        return new Quantity<>(result, target);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U target) {
        validate(other, target);
        double diffBase = this.unit.convertToBaseUnit(this.value)
                - other.unit.convertToBaseUnit(other.value);
        double result = target.convertFromBaseUnit(diffBase);
        return new Quantity<>(result, target);
    }

    public double divide(Quantity<U> other) {
        if (other == null) throw new IllegalArgumentException();
        ensureSameCategory(other);
        double a = this.unit.convertToBaseUnit(this.value);
        double b = other.unit.convertToBaseUnit(other.value);
        if (b == 0.0) throw new ArithmeticException();
        return a / b;
    }

    private void validate(Quantity<U> other, U target) {
        if (other == null || target == null) throw new IllegalArgumentException();
        ensureSameCategory(other);
    }

    private void ensureSameCategory(Quantity<U> other) {
        if (this.unit.getClass() != other.unit.getClass()) {
            throw new IllegalArgumentException();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;
        if (this.unit.getClass() != other.unit.getClass()) return false;
        double a = this.unit.convertToBaseUnit(this.value);
        double b = other.unit.convertToBaseUnit(other.value);
        return Double.compare(a, b) == 0;
    }

    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    public String toString() {
        return value + " " + unit.getUnitName();
    }
}

public class QMA {
    public static void main(String[] args) {

        Quantity<LengthUnit> l1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6.0, LengthUnit.INCH);

        System.out.println(l1.equals(l2));
        System.out.println(l1.convertTo(LengthUnit.INCH));
        System.out.println(l1.add(l2));
        System.out.println(l1.subtract(l2));
        System.out.println(l1.divide(l2));

        Quantity<WeightUnit> w1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5000.0, WeightUnit.GRAM);

        System.out.println(w1.equals(w2));
        System.out.println(w1.convertTo(WeightUnit.GRAM));
        System.out.println(w1.add(w2));
        System.out.println(w1.subtract(w2));
        System.out.println(w1.divide(w2));

        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println(v1.equals(v2));
        System.out.println(v1.convertTo(VolumeUnit.MILLILITRE));
        System.out.println(v1.add(v2));
        System.out.println(v1.subtract(v2));
        System.out.println(v1.divide(v2));
    }
}