package ge.edu.tsu.topsis;

import ge.edu.tsu.objects.CriteriaType;
import ge.edu.tsu.objects.Alternative;
import ge.edu.tsu.objects.Fuzzy;
import ge.edu.tsu.utils.Printer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;

@Data
@AllArgsConstructor
public class TopsisMethod {
    private Alternative[][] decisionMatrix;
    private int m;
    private int n;

    public Alternative selectBestSupplier() {
        long start = System.currentTimeMillis();

        System.out.println("კრიტერიუმების წონები:\n");
        Printer.printCriteriaWeight(decisionMatrix);
        System.out.println();

        System.out.println("ფაზი გადაწყვეტილების მატრიცა:\n");
        Printer.printMatrix(decisionMatrix);
        System.out.println();

        Alternative[][] normalizedFuzzyMatrix = calculateNormalizedFuzzyMatrix(decisionMatrix);

        if (normalizedFuzzyMatrix == null) {
            return null;
        }

        System.out.println("ფაზი ნორმალიზებული მატრიცა:\n");
        Printer.printMatrix(normalizedFuzzyMatrix);
        System.out.println();

        Alternative[][] weightedNormalizedFuzzyMatrix = calculateWeightedNormalizedFuzzyMatrix(normalizedFuzzyMatrix);

        System.out.println("ფაზი შეწონილი ნორმალიზებული მატრიცა:\n");
        Printer.printMatrix(weightedNormalizedFuzzyMatrix);
        System.out.println();

        System.out.println("ფაზი დადებითი იდეალური გადაწყვეტილება:");
        Fuzzy[] fuzzyPositiveIdealSolution = getFuzzyPositiveIdealSolution(weightedNormalizedFuzzyMatrix);
        Printer.printFuzzyNumberVector(fuzzyPositiveIdealSolution);
        System.out.println();

        System.out.println("ფაზი უარყოფითი იდეალური გადაწყვეტილება:");
        Fuzzy[] fuzzyNegativeIdealSolution = getFuzzyNegativeIdealSolution(weightedNormalizedFuzzyMatrix);
        Printer.printFuzzyNumberVector(fuzzyNegativeIdealSolution);
        System.out.println();

        System.out.println("მანძილი ფაზი დადებით იდეალურ გადაწყვეტილებამდე: ");
        Alternative[] positiveIdealDistance = calculateIdealDistance(weightedNormalizedFuzzyMatrix, fuzzyPositiveIdealSolution);
        Printer.printAlternatives(positiveIdealDistance);

        System.out.println("მანძილი ფაზი უარყოფით იდეალურ გადაწყვეტილებამდე: ");
        Alternative[] negativeIdealDistance = calculateIdealDistance(weightedNormalizedFuzzyMatrix, fuzzyNegativeIdealSolution);
        Printer.printAlternatives(negativeIdealDistance);

        System.out.println("იდეალურ გადაწყვეტილებამდე შეფარდებითი სიახლოვე: ");
        Alternative[] relativeCloseness = calculateRelativeCloseness(positiveIdealDistance, negativeIdealDistance);
        Printer.printAlternatives(relativeCloseness);

        System.out.println("რანჟირებული სია:");
        Alternative[] ranked = rankAlternatives(relativeCloseness);
        Printer.printAlternatives(ranked);

        long end = System.currentTimeMillis();
        System.out.println("მილიწამი: " + (end - start));

        return ranked[0];
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

    private Alternative[][] calculateNormalizedFuzzyMatrix(Alternative[][] matrix) {
        Alternative[][] normalizedMatrix = new Alternative[m][n];
        Fuzzy[] maxAndMinWeights = new Fuzzy[n];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Alternative alternative = new Alternative(matrix[i][j].getName(), matrix[i][j].getWeight(), matrix[i][j].getCriteria());
                Fuzzy initialWeight = alternative.getWeight();
                Fuzzy normalized;

                if (i == 0) {
                    Fuzzy weight = null;

                    if (alternative.getCriteria().getCriteriaType() == CriteriaType.BENEFIT) {
                        weight = getMaxWeight(j);
                    } else if (alternative.getCriteria().getCriteriaType() == CriteriaType.COST) {
                        weight = getMinWeight(j);
                    }

                    maxAndMinWeights[j] = weight;
                }

                Fuzzy weight = maxAndMinWeights[j];

                if (alternative.getCriteria().getCriteriaType() == CriteriaType.BENEFIT) {
                    normalized = new Fuzzy(initialWeight.getLeft().divide(weight.getRight(), 3, RoundingMode.HALF_UP),
                            initialWeight.getMiddle().divide(weight.getMiddle(), 3, RoundingMode.HALF_UP),
                            initialWeight.getRight().divide(weight.getLeft(), 3, RoundingMode.HALF_UP));
                } else if (alternative.getCriteria().getCriteriaType() == CriteriaType.COST) {
                    normalized = new Fuzzy(weight.getLeft().divide(initialWeight.getRight(), 3, RoundingMode.HALF_UP),
                            weight.getMiddle().divide(initialWeight.getMiddle(), 3, RoundingMode.HALF_UP),
                            weight.getRight().divide(initialWeight.getLeft(), 3, RoundingMode.HALF_UP));
                } else {
                    System.out.println("კრიტერიუმის ტიპი არაა დაკონრეტებული!");
                    return null;
                }

                normalizedMatrix[i][j] = alternative;
                normalizedMatrix[i][j].setWeight(normalized);
            }
        }

        return normalizedMatrix;
    }

    private Alternative[][] calculateWeightedNormalizedFuzzyMatrix(Alternative[][] matrix) {
        Alternative[][] normalizedWeightedMatrix = new Alternative[m][n];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Alternative alternative = new Alternative(matrix[i][j].getName(), matrix[i][j].getWeight(), matrix[i][j].getCriteria());
                Fuzzy initialWeight = alternative.getWeight();
                Fuzzy criteriaWeight = alternative.getCriteria().getWeight();

                Fuzzy weighted = new Fuzzy(initialWeight.getLeft().multiply(criteriaWeight.getLeft()),
                        initialWeight.getMiddle().multiply(criteriaWeight.getMiddle()),
                        initialWeight.getRight().multiply(criteriaWeight.getRight()));


                normalizedWeightedMatrix[i][j] = alternative;
                normalizedWeightedMatrix[i][j].setWeight(weighted);
            }
        }

        return normalizedWeightedMatrix;
    }

    public Fuzzy[] getFuzzyPositiveIdealSolution(Alternative[][] matrix) {
        Fuzzy[] fuzzyPositiveIdealSolution = new Fuzzy[n];
        BigDecimal right = matrix[0][0].getWeight().getRight();

        for (int indexJ = 0; indexJ < n; indexJ++) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][indexJ].getWeight().getRight().compareTo(right) > 0) {
                    right = matrix[i][indexJ].getWeight().getRight();
                }
            }

            fuzzyPositiveIdealSolution[indexJ] = new Fuzzy(right, right, right);

            right = matrix[0][indexJ != n - 1 ? indexJ + 1 : 0].getWeight().getRight();
        }

        return fuzzyPositiveIdealSolution;
    }

    public Fuzzy[] getFuzzyNegativeIdealSolution(Alternative[][] matrix) {
        Fuzzy[] fuzzyNegativeIdealSolution = new Fuzzy[n];
        BigDecimal left = matrix[0][0].getWeight().getRight();

        for (int indexJ = 0; indexJ < n; indexJ++) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][indexJ].getWeight().getLeft().compareTo(left) < 0) {
                    left = matrix[i][indexJ].getWeight().getLeft();
                }
            }

            fuzzyNegativeIdealSolution[indexJ] = new Fuzzy(left, left, left);

            left = matrix[0][indexJ != n - 1 ? indexJ + 1 : 0].getWeight().getLeft();
        }

        return fuzzyNegativeIdealSolution;
    }

    public BigDecimal calculateDistance(Fuzzy point, Fuzzy ideal) {
        Double left = Math.pow(point.getLeft().subtract(ideal.getLeft()).doubleValue(), 2);
        Double middle = Math.pow(point.getMiddle().subtract(ideal.getMiddle()).doubleValue(), 2);
        Double right = Math.pow(point.getRight().subtract(ideal.getRight()).doubleValue(), 2);

        return BigDecimal.valueOf(Math.sqrt((left + middle + right) / 3)).setScale(3, RoundingMode.HALF_UP);
    }

    public Alternative[] calculateIdealDistance(Alternative[][] matrix, Fuzzy[] ideal) {
        Alternative[] alternatives = new Alternative[m];
        BigDecimal sum = BigDecimal.valueOf(0);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sum = sum.add(calculateDistance(matrix[i][j].getWeight(), ideal[j]));
            }

            alternatives[i] = new Alternative(matrix[i][0].getName(), matrix[i][0].getWeight(), matrix[i][0].getCriteria());
            alternatives[i].setDistance(sum);

            sum = BigDecimal.valueOf(0);
        }

        return alternatives;
    }

    public Alternative[] calculateRelativeCloseness(Alternative[] positiveIdealDistance, Alternative[] negativeIdealDistance) {
        Alternative[] alternatives = new Alternative[m];
        BigDecimal relativeCloseness;

        for (int i = 0; i < positiveIdealDistance.length; i++) {
            BigDecimal divider = positiveIdealDistance[i].getDistance().add(negativeIdealDistance[i].getDistance());
            relativeCloseness = negativeIdealDistance[i].getDistance().divide(divider, 3, RoundingMode.HALF_UP);

            alternatives[i] = new Alternative(positiveIdealDistance[i].getName(), positiveIdealDistance[i].getWeight(), positiveIdealDistance[i].getCriteria());
            alternatives[i].setDistance(relativeCloseness);
        }

        return alternatives;
    }

    public Alternative[] rankAlternatives(Alternative[] alternatives) {
        Arrays.sort(alternatives, (Comparator<Object>) (a1, a2) -> {
            BigDecimal bigDec1 = ((Alternative) (a1)).getDistance();
            BigDecimal bigDec2 = ((Alternative) (a2)).getDistance();
            return bigDec2.compareTo(bigDec1);
        });

        return alternatives;
    }
}
