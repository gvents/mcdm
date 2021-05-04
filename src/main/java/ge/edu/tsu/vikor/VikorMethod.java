package ge.edu.tsu.vikor;

import ge.edu.tsu.objects.*;
import ge.edu.tsu.utils.Printer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;

@Data
@AllArgsConstructor
public class VikorMethod {
    private Alternative[][] decisionMatrix;
    private int alternativeNumber;
    private int criteriaNumber;
    private BigDecimal v;

    public Alternative selectBestSupplier() {
        long start = System.currentTimeMillis();

        System.out.println("კრიტერიუმების წონები:\n");
        Printer.printCriteriaWeight(decisionMatrix);
        System.out.println();

        System.out.println("ფაზი გადაწყვეტილების მატრიცა:\n");
        Printer.printMatrix(decisionMatrix);
        System.out.println();

        System.out.println("ფაზი დადებითი იდეალური მნიშვნელობები:");
        Fuzzy[] positiveIdealPoints = getFuzzyPositiveIdealWeights();
        Printer.printFuzzyNumberVector(positiveIdealPoints);

        System.out.println("ფაზი უარყოფითი იდეალური მნიშვნელობები:");
        Fuzzy[] negativeIdealPoints = getFuzzyNegativeIdealWeights();
        Printer.printFuzzyNumberVector(negativeIdealPoints);

        Alternative[][] normalizedFuzzyMatrix = calculateNormalizedFuzzyMatrix(decisionMatrix, positiveIdealPoints, negativeIdealPoints);

        if (normalizedFuzzyMatrix == null) {
            return null;
        }

        System.out.println("ფაზი ნორმალიზებული მატრიცა:\n");
        Printer.printMatrix(normalizedFuzzyMatrix);
        System.out.println();

        AlternativesSR weightedSumAndFuzzyMax = calculateWeightedSumAndFuzzyMax(normalizedFuzzyMatrix);

        System.out.println("ფაზი შეწონილი ჯამი:\n");
        Printer.printAlternativesWithWeights(weightedSumAndFuzzyMax.getS());
        System.out.println();

        System.out.println("ფაზი MAX:");
        Printer.printAlternativesWithWeights(weightedSumAndFuzzyMax.getR());
        System.out.println();

        SR fuzzyMax = getFuzzyMax(weightedSumAndFuzzyMax);
        System.out.println("Max S = " + fuzzyMax.getS());
        System.out.println("Max R = " + fuzzyMax.getR());

        SR fuzzyMin = getFuzzyMin(weightedSumAndFuzzyMax);
        System.out.println("Min S = " + fuzzyMin.getS());
        System.out.println("Min R = " + fuzzyMin.getR());

        System.out.println();

        System.out.println("Q მნიშვნელობები:");
        Alternative[] qValues = calculateQValues(weightedSumAndFuzzyMax, fuzzyMax, fuzzyMin);
        Printer.printAlternativesWithWeights(qValues);
        System.out.println();

        System.out.println("Q დეფაზიფიკაცია:");
        Alternative[] defuzzifiedQ = defuzzifyArray(qValues);
        Printer.printAlternatives(defuzzifiedQ);
        System.out.println();

        System.out.println("S დეფაზიფიკაცია:");
        Alternative[] defuzzifiedS = defuzzifyArray(weightedSumAndFuzzyMax.getS());
        Printer.printAlternatives(defuzzifiedS);
        System.out.println();

        System.out.println("R დეფაზიფიკაცია:");
        Alternative[] defuzzifiedR = defuzzifyArray(weightedSumAndFuzzyMax.getR());
        Printer.printAlternatives(defuzzifiedR);
        System.out.println();

        System.out.println("რანჟირებული სია Q:");
        Alternative[] rankedQ = rankAlternatives(defuzzifiedQ);
        Printer.printAlternatives(rankedQ);

        System.out.println("რანჟირებული სია S:");
        Alternative[] rankedS = rankAlternatives(defuzzifiedS);
        Printer.printAlternatives(rankedS);

        System.out.println("რანჟირებული სია R:");
        Alternative[] rankedR = rankAlternatives(defuzzifiedR);
        Printer.printAlternatives(rankedR);

        long end = System.currentTimeMillis();
        System.out.println("მილიწამი: " + (end - start));

        return rankedQ[0];
    }

    private Fuzzy[] getFuzzyPositiveIdealWeights() {
        Fuzzy[] maxWeight = new Fuzzy[criteriaNumber];

        for (int indexJ = 0; indexJ < criteriaNumber; indexJ++) {
            BigDecimal left = decisionMatrix[0][indexJ].getWeight().getLeft();
            BigDecimal middle = decisionMatrix[0][indexJ].getWeight().getMiddle();
            BigDecimal right = decisionMatrix[0][indexJ].getWeight().getRight();

            for (int i = 0; i < decisionMatrix.length; i++) {
                if (decisionMatrix[i][indexJ].getCriteria().getCriteriaType() == CriteriaType.BENEFIT) {
                    if (decisionMatrix[i][indexJ].getWeight().getLeft().compareTo(left) > 0) {
                        left = decisionMatrix[i][indexJ].getWeight().getLeft();
                    }

                    if (decisionMatrix[i][indexJ].getWeight().getMiddle().compareTo(middle) > 0) {
                        middle = decisionMatrix[i][indexJ].getWeight().getMiddle();
                    }

                    if (decisionMatrix[i][indexJ].getWeight().getRight().compareTo(right) > 0) {
                        right = decisionMatrix[i][indexJ].getWeight().getRight();
                    }
                } else if (decisionMatrix[i][indexJ].getCriteria().getCriteriaType() == CriteriaType.COST) {
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
            }

            maxWeight[indexJ] = new Fuzzy(left, middle, right);
        }

        return maxWeight;
    }

    private Fuzzy[] getFuzzyNegativeIdealWeights() {
        Fuzzy[] minWeight = new Fuzzy[criteriaNumber];

        for (int indexJ = 0; indexJ < criteriaNumber; indexJ++) {
            BigDecimal left = decisionMatrix[0][indexJ].getWeight().getLeft();
            BigDecimal middle = decisionMatrix[0][indexJ].getWeight().getMiddle();
            BigDecimal right = decisionMatrix[0][indexJ].getWeight().getRight();

            for (int i = 0; i < decisionMatrix.length; i++) {
                if (decisionMatrix[i][indexJ].getCriteria().getCriteriaType() == CriteriaType.BENEFIT) {
                    if (decisionMatrix[i][indexJ].getWeight().getLeft().compareTo(left) < 0) {
                        left = decisionMatrix[i][indexJ].getWeight().getLeft();
                    }

                    if (decisionMatrix[i][indexJ].getWeight().getMiddle().compareTo(middle) < 0) {
                        middle = decisionMatrix[i][indexJ].getWeight().getMiddle();
                    }

                    if (decisionMatrix[i][indexJ].getWeight().getRight().compareTo(right) < 0) {
                        right = decisionMatrix[i][indexJ].getWeight().getRight();
                    }
                } else if (decisionMatrix[i][indexJ].getCriteria().getCriteriaType() == CriteriaType.COST) {
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
            }

            minWeight[indexJ] = new Fuzzy(left, middle, right);
        }

        return minWeight;
    }

    private Alternative[][] calculateNormalizedFuzzyMatrix(Alternative[][] matrix,
                                                           Fuzzy[] positiveIdealPoints,
                                                           Fuzzy[] negativeIdealPoints) {
        Alternative[][] normalizedMatrix = new Alternative[alternativeNumber][criteriaNumber];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                normalizedMatrix[i][j] = new Alternative(matrix[i][j].getName(), matrix[i][j].getWeight(), matrix[i][j].getCriteria());

                BigDecimal left;
                BigDecimal middle;
                BigDecimal right;
                BigDecimal divider;

                if (normalizedMatrix[i][j].getCriteria().getCriteriaType() == CriteriaType.BENEFIT) {
                    divider = positiveIdealPoints[j].getRight().subtract(negativeIdealPoints[j].getLeft());

                    left = (positiveIdealPoints[j].getLeft().subtract(matrix[i][j].getWeight().getRight())).divide(divider, 3, RoundingMode.HALF_UP);
                    middle = (positiveIdealPoints[j].getMiddle().subtract(matrix[i][j].getWeight().getMiddle())).divide(divider, 3, RoundingMode.HALF_UP);
                    right = (positiveIdealPoints[j].getRight().subtract(matrix[i][j].getWeight().getLeft())).divide(divider, 3, RoundingMode.HALF_UP);
                } else if (normalizedMatrix[i][j].getCriteria().getCriteriaType() == CriteriaType.COST) {
                    divider = negativeIdealPoints[j].getRight().subtract(positiveIdealPoints[j].getLeft());

                    left = (matrix[i][j].getWeight().getLeft().subtract(positiveIdealPoints[j].getRight())).divide(divider, 3, RoundingMode.HALF_UP);
                    middle = (matrix[i][j].getWeight().getMiddle().subtract(positiveIdealPoints[j].getMiddle())).divide(divider, 3, RoundingMode.HALF_UP);
                    right = (matrix[i][j].getWeight().getRight().subtract(positiveIdealPoints[j].getLeft())).divide(divider, 3, RoundingMode.HALF_UP);
                } else {
                    System.out.println("კრიტერიუმის ტიპი არაა დაკონრეტებული!");
                    return null;
                }

                normalizedMatrix[i][j].setWeight(new Fuzzy(left, middle, right));
            }
        }

        return normalizedMatrix;
    }

    private AlternativesSR calculateWeightedSumAndFuzzyMax(Alternative[][] matrix) {
        Alternative[] alternativesS = new Alternative[alternativeNumber];
        Alternative[] alternativesR = new Alternative[alternativeNumber];

        for (int i = 0; i < matrix.length; i++) {
            alternativesS[i] = new Alternative(matrix[i][0].getName(), null, matrix[i][0].getCriteria());
            alternativesR[i] = new Alternative(matrix[i][0].getName(), null, matrix[i][0].getCriteria());

            Fuzzy initialWeight = matrix[i][0].getWeight();
            Fuzzy criteriaWeight = matrix[i][0].getCriteria().getWeight();

            BigDecimal sumLeft = BigDecimal.valueOf(0);
            BigDecimal sumMiddle = BigDecimal.valueOf(0);
            BigDecimal sumRight = BigDecimal.valueOf(0);


            BigDecimal maxLeft = initialWeight.getLeft().multiply(criteriaWeight.getLeft());
            BigDecimal maxMiddle = initialWeight.getMiddle().multiply(criteriaWeight.getMiddle());
            BigDecimal maxRight = initialWeight.getRight().multiply(criteriaWeight.getRight());

            for (int j = 0; j < matrix[i].length; j++) {
                alternativesS[i] = new Alternative(matrix[i][0].getName(), null, matrix[i][0].getCriteria());
                initialWeight = matrix[i][j].getWeight();

                criteriaWeight = matrix[i][j].getCriteria().getWeight();

                BigDecimal left = initialWeight.getLeft().multiply(criteriaWeight.getLeft());
                BigDecimal middle = initialWeight.getMiddle().multiply(criteriaWeight.getMiddle());
                BigDecimal right = initialWeight.getRight().multiply(criteriaWeight.getRight());

                sumLeft = sumLeft.add(left);
                sumMiddle = sumMiddle.add(middle);
                sumRight = sumRight.add(right);

                if (left.compareTo(maxLeft) > 0) {
                    maxLeft = left;
                }

                if (middle.compareTo(maxMiddle) > 0) {
                    maxMiddle = middle;
                }

                if (right.compareTo(maxRight) > 0) {
                    maxRight = right;
                }
            }

            alternativesS[i].setWeight(new Fuzzy(sumLeft, sumMiddle, sumRight));
            alternativesR[i].setWeight(new Fuzzy(maxLeft, maxMiddle, maxRight));
        }

        return new AlternativesSR(alternativesS, alternativesR);
    }

    public SR getFuzzyMax(AlternativesSR alternativesSR) {
        BigDecimal leftS = alternativesSR.getS()[0].getWeight().getLeft();
        BigDecimal middleS = alternativesSR.getS()[0].getWeight().getMiddle();
        BigDecimal rightS = alternativesSR.getS()[0].getWeight().getRight();

        BigDecimal leftR = alternativesSR.getR()[0].getWeight().getLeft();
        BigDecimal middleR = alternativesSR.getR()[0].getWeight().getMiddle();
        BigDecimal rightR = alternativesSR.getR()[0].getWeight().getRight();

        for (int i = 0; i < alternativesSR.getS().length; i++) {
            if (alternativesSR.getS()[i].getWeight().getLeft().compareTo(leftS) > 0) {
                leftS = alternativesSR.getS()[i].getWeight().getLeft();
            }

            if (alternativesSR.getS()[i].getWeight().getMiddle().compareTo(middleS) > 0) {
                middleS = alternativesSR.getS()[i].getWeight().getMiddle();
            }

            if (alternativesSR.getS()[i].getWeight().getRight().compareTo(rightS) > 0) {
                rightS = alternativesSR.getS()[i].getWeight().getRight();
            }

            if (alternativesSR.getR()[i].getWeight().getLeft().compareTo(leftR) > 0) {
                leftR = alternativesSR.getR()[i].getWeight().getLeft();
            }

            if (alternativesSR.getR()[i].getWeight().getMiddle().compareTo(middleR) > 0) {
                middleR = alternativesSR.getR()[i].getWeight().getMiddle();
            }

            if (alternativesSR.getR()[i].getWeight().getRight().compareTo(rightR) > 0) {
                rightR = alternativesSR.getR()[i].getWeight().getRight();
            }
        }

        return new SR(new Fuzzy(leftS, middleS, rightS), new Fuzzy(leftR, middleR, rightR));
    }

    public SR getFuzzyMin(AlternativesSR alternativesSR) {
        BigDecimal leftS = alternativesSR.getS()[0].getWeight().getLeft();
        BigDecimal middleS = alternativesSR.getS()[0].getWeight().getMiddle();
        BigDecimal rightS = alternativesSR.getS()[0].getWeight().getRight();

        BigDecimal leftR = alternativesSR.getR()[0].getWeight().getLeft();
        BigDecimal middleR = alternativesSR.getR()[0].getWeight().getMiddle();
        BigDecimal rightR = alternativesSR.getR()[0].getWeight().getRight();

        for (int i = 0; i < alternativesSR.getS().length; i++) {
            if (alternativesSR.getS()[i].getWeight().getLeft().compareTo(leftS) < 0) {
                leftS = alternativesSR.getS()[i].getWeight().getLeft();
            }

            if (alternativesSR.getS()[i].getWeight().getMiddle().compareTo(middleS) < 0) {
                middleS = alternativesSR.getS()[i].getWeight().getMiddle();
            }

            if (alternativesSR.getS()[i].getWeight().getRight().compareTo(rightS) < 0) {
                rightS = alternativesSR.getS()[i].getWeight().getRight();
            }

            if (alternativesSR.getR()[i].getWeight().getLeft().compareTo(leftR) < 0) {
                leftR = alternativesSR.getR()[i].getWeight().getLeft();
            }

            if (alternativesSR.getR()[i].getWeight().getMiddle().compareTo(middleR) < 0) {
                middleR = alternativesSR.getR()[i].getWeight().getMiddle();
            }

            if (alternativesSR.getR()[i].getWeight().getRight().compareTo(rightR) < 0) {
                rightR = alternativesSR.getR()[i].getWeight().getRight();
            }
        }


        return new SR(new Fuzzy(leftS, middleS, rightS), new Fuzzy(leftR, middleR, rightR));
    }

    public Alternative[] calculateQValues(AlternativesSR alternativesSR, SR fuzzyMax, SR fuzzyMin) {
        Alternative[] alternatives = new Alternative[alternativeNumber];

        BigDecimal dividerS = fuzzyMax.getS().getRight().subtract(fuzzyMin.getS().getLeft());
        BigDecimal dividerR = fuzzyMax.getR().getRight().subtract(fuzzyMin.getR().getLeft());

        for (int i = 0; i < alternativesSR.getS().length; i++) {
            BigDecimal leftS = ((alternativesSR.getS()[i].getWeight().getLeft().subtract(fuzzyMin.getS().getRight())).divide(dividerS, 3, RoundingMode.HALF_UP)).multiply(v);
            BigDecimal middleS = ((alternativesSR.getS()[i].getWeight().getMiddle().subtract(fuzzyMin.getS().getMiddle())).divide(dividerS, 3, RoundingMode.HALF_UP)).multiply(v);
            BigDecimal rightS = ((alternativesSR.getS()[i].getWeight().getRight().subtract(fuzzyMin.getS().getLeft())).divide(dividerS, 3, RoundingMode.HALF_UP)).multiply(v);

            BigDecimal leftR = ((alternativesSR.getR()[i].getWeight().getLeft().subtract(fuzzyMin.getR().getRight())).divide(dividerR, 3, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(1).subtract(v));
            BigDecimal middleR = ((alternativesSR.getR()[i].getWeight().getMiddle().subtract(fuzzyMin.getR().getMiddle())).divide(dividerR, 3, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(1).subtract(v));
            BigDecimal rightR = ((alternativesSR.getR()[i].getWeight().getRight().subtract(fuzzyMin.getR().getLeft())).divide(dividerR, 3, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(1).subtract(v));

            alternatives[i] = new Alternative(alternativesSR.getS()[i].getName(), new Fuzzy(leftS.add(leftR), middleS.add(middleR), rightS.add(rightR)), alternativesSR.getS()[i].getCriteria());
        }

        return alternatives;
    }

    public BigDecimal defuzzification(Fuzzy number) {
        return (number.getLeft().add(number.getMiddle().add(number.getRight()))).divide(BigDecimal.valueOf(3), 3, RoundingMode.HALF_UP);
    }

    public Alternative[] defuzzifyArray(Alternative[] alternatives) {
        for (int i = 0; i < alternatives.length; i++) {
            alternatives[i].setDistance(defuzzification(alternatives[i].getWeight()));
        }

        return alternatives;
    }

    public Alternative[] rankAlternatives(Alternative[] alternatives) {
        Arrays.sort(alternatives, (Comparator<Object>) (a1, a2) -> {
            BigDecimal bigDec1 = ((Alternative) (a1)).getDistance();
            BigDecimal bigDec2 = ((Alternative) (a2)).getDistance();
            return bigDec1.compareTo(bigDec2);
        });

        return alternatives;
    }
}
