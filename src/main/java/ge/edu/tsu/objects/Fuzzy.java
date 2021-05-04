package ge.edu.tsu.objects;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Fuzzy {
    private BigDecimal left;
    private BigDecimal middle;
    private BigDecimal right;

    public Fuzzy(double left, double middle, double right) {
        this.left = new BigDecimal(left).setScale(3, RoundingMode.HALF_UP);
        this.middle = new BigDecimal(middle).setScale(3, RoundingMode.HALF_UP);
        this.right = new BigDecimal(right).setScale(3, RoundingMode.HALF_UP);
    }

    public Fuzzy(BigDecimal left, BigDecimal middle, BigDecimal right) {
        this.left = left.setScale(3, RoundingMode.HALF_UP);
        this.middle = middle.setScale(3, RoundingMode.HALF_UP);
        this.right = right.setScale(3, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "(" +
                left +
                ", " + middle +
                ", " + right +
                ')';
    }
}
