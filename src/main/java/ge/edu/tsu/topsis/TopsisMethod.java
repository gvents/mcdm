package ge.edu.tsu.topsis;

import ge.edu.tsu.CriteriaType;
import ge.edu.tsu.objects.Alternative;
import ge.edu.tsu.objects.Fuzzy;
import ge.edu.tsu.utils.Printer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
public class TopsisMethod {
    private Alternative[][] decisionMatrix;

    public void selectBestSupplier() {
        System.out.println("ფაზი გადაწყვეტილების მატრიცა:\n");
        Printer.printMatrix(decisionMatrix);
        System.out.println();

        Alternative[][] normalizedFuzzyMatrix = calculateNormalizedFuzzyMatrix();

        if (normalizedFuzzyMatrix == null) {
            return;
        }

        System.out.println("ფაზი ნორმალიზებული მატრიცა:\n");
        Printer.printMatrix(normalizedFuzzyMatrix);
        System.out.println();

        Alternative[][] weightedNormalizedFuzzyMatrix = calculateWeightedNormalizedFuzzyMatrix();

        System.out.println("ფაზი შეწონილი ნორმალიზებული მატრიცა:\n");
        Printer.printMatrix(weightedNormalizedFuzzyMatrix);
        System.out.println();
    }

    private Fuzzy getMaxWeight(int indexJ) {
        BigDecimal left = decisionMatrix[0][indexJ].getWeight().getLeft();
        BigDecimal middle = decisionMatrix[0][indexJ].getWeight().getMiddle();
        BigDecimal right = decisionMatrix[0][indexJ].getWeight().getRight();

        for (int i = 1; i < decisionMatrix.length; i++) {
            if (decisionMatrix[i][indexJ].getWeight().getLeft().compareTo(left) > 0) {
                left = decisionMatrix[i][indexJ].getWeight().getLeft();
            }

            if (decisionMatrix[i][indexJ].getWeight().getMiddle().compareTo(middle) > 0) {
                middle = decisionMatrix[i][indexJ].getWeight().getMiddle();
            }

            if (decisionMatrix[i][indexJ].getWeight().getRight().compareTo(right) > 0) {
                right = decisionMatrix[i][indexJ].getWeight().getRight();
            }
        }

        return new Fuzzy(left, middle, right);
    }

    private Fuzzy getMinWeight(int indexJ) {
        BigDecimal left = decisionMatrix[0][indexJ].getWeight().getLeft();
        BigDecimal middle = decisionMatrix[0][indexJ].getWeight().getMiddle();
        BigDecimal right = decisionMatrix[0][indexJ].getWeight().getRight();

        for (int i = 1; i < decisionMatrix.length; i++) {
            if (decisionMatrix[i][indexJ].getWeight().getLeft().compareTo(left) < 0) {
                left = decisionMatrix[i][indexJ].getWeight().getLeft();
            }

            if (decisionMatrix[i][indexJ].getWeight().getMiddle().compareTo(middle) < 0) {
                middle = decisionMatrix[i][indexJ].getWeight().getMiddle();
            }

            if (decisionMatrix[i][indexJ].getWeight().getRight().compareTo(right) < 0) {
                right = decisionMatrix[i][indexJ].getWeight().getRight();
            }
        }

        return new Fuzzy(left, middle, right);
    }

    private Alternative[][] calculateNormalizedFuzzyMatrix() {
        for (int i = 0; i < decisionMatrix.length; i++) {
            for (int j = 0; j < decisionMatrix[i].length; j++) {
                Alternative alternative = decisionMatrix[i][j];
                Fuzzy initialWeight = alternative.getWeight();
                Fuzzy normalized = null;

                if (alternative.getCriteria().getCriteriaType() == CriteriaType.BENEFIT) {
                    Fuzzy maxWeight = getMaxWeight(j);

                    normalized = new Fuzzy(initialWeight.getLeft().divide(maxWeight.getRight(), 3, RoundingMode.HALF_UP),
                            initialWeight.getMiddle().divide(maxWeight.getMiddle(), 3, RoundingMode.HALF_UP),
                            initialWeight.getRight().divide(maxWeight.getLeft(), 3, RoundingMode.HALF_UP));
                } else if (alternative.getCriteria().getCriteriaType() == CriteriaType.COST) {
                    Fuzzy minWeight = getMinWeight(j);

                    normalized = new Fuzzy(minWeight.getLeft().divide(initialWeight.getRight(), 3, RoundingMode.HALF_UP),
                            minWeight.getMiddle().divide(initialWeight.getMiddle(), 3, RoundingMode.HALF_UP),
                            minWeight.getRight().divide(initialWeight.getLeft(), 3, RoundingMode.HALF_UP));
                } else {
                    System.out.println("კრიტერიუმის ტიპი არაა დაკონრეტებული!");
                    return null;
                }

                alternative.setWeight(normalized);
                decisionMatrix[i][j] = alternative;
            }
        }

        return decisionMatrix;
    }

    private Alternative[][] calculateWeightedNormalizedFuzzyMatrix() {
        for (int i = 0; i < decisionMatrix.length; i++) {
            for (int j = 0; j < decisionMatrix[i].length; j++) {
                Alternative alternative = decisionMatrix[i][j];
                Fuzzy initialWeight = alternative.getWeight();
                Fuzzy criteriaWeight = alternative.getCriteria().getWeight();

                Fuzzy weighted = new Fuzzy(initialWeight.getLeft().multiply(criteriaWeight.getLeft()),
                        initialWeight.getMiddle().multiply(criteriaWeight.getMiddle()),
                        initialWeight.getRight().multiply(criteriaWeight.getRight()));


                alternative.setWeight(weighted);
                decisionMatrix[i][j] = alternative;
            }
        }

        return decisionMatrix;
    }
}
