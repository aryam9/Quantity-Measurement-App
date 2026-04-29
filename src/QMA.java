enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}

class QuantityWeight {
    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
        this.unit = unit;
    }

    public QuantityWeight convertTo(WeightUnit target) {
        if (target == null) throw new IllegalArgumentException();
        double base = unit.toBase(value);
        double converted = target.fromBase(base);
        return new QuantityWeight(converted, target);
    }

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit target) {
        if (other == null || target == null) throw new IllegalArgumentException();
        double sumBase = this.unit.toBase(this.value) + other.unit.toBase(other.value);
        double result = target.fromBase(sumBase);
        return new QuantityWeight(result, target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityWeight other = (QuantityWeight) obj;
        double a = this.unit.toBase(this.value);
        double b = other.unit.toBase(other.value);
        return Double.compare(a, b) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.toBase(value));
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

public class QMA {
    public static void main(String[] args) {
        QuantityWeight q1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight q2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        System.out.println(q1.equals(q2));

        QuantityWeight q3 = new QuantityWeight(2.0, WeightUnit.POUND);
        System.out.println(q3.convertTo(WeightUnit.KILOGRAM));

        QuantityWeight sum = q1.add(q2);
        System.out.println(sum);

        QuantityWeight sum2 = q1.add(q2, WeightUnit.GRAM);
        System.out.println(sum2);
    }
}