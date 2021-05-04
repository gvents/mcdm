package ge.edu.tsu.utils;

import ge.edu.tsu.objects.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FillMatrix {
    public static Alternative[][] fillMatrix(int m, int n, int k) {
        Fuzzy[][] expertCriteriaWeights = new Fuzzy[][] {
                new Fuzzy[] {
                        LinguisticVariablesCriteria.მაღალი.getWeight(),
                        LinguisticVariablesCriteria.მაღალი.getWeight(),
                        LinguisticVariablesCriteria.ძალიან_მაღალი.getWeight(),
                        LinguisticVariablesCriteria.საშუალოდ_დაბალი.getWeight(),
                        LinguisticVariablesCriteria.საშუალო.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesCriteria.მაღალი.getWeight(),
                        LinguisticVariablesCriteria.მაღალი.getWeight(),
                        LinguisticVariablesCriteria.მაღალი.getWeight(),
                        LinguisticVariablesCriteria.საშუალო.getWeight(),
                        LinguisticVariablesCriteria.საშუალოდ_დაბალი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesCriteria.საშუალოდ_მაღალი.getWeight(),
                        LinguisticVariablesCriteria.მაღალი.getWeight(),
                        LinguisticVariablesCriteria.საშუალოდ_მაღალი.getWeight(),
                        LinguisticVariablesCriteria.საშუალო.getWeight(),
                        LinguisticVariablesCriteria.საშუალო.getWeight()
                }
        };

        Fuzzy[] criteriaWeights = new Fuzzy[expertCriteriaWeights[0].length];

        for (int j = 0; j < expertCriteriaWeights[0].length; j++) {
            Fuzzy weight = new Fuzzy(0, 0, 0);

            for (int i = 0; i < expertCriteriaWeights.length; i++) {
                weight = new Fuzzy(weight.getLeft().add(expertCriteriaWeights[i][j].getLeft()),
                        weight.getMiddle().add(expertCriteriaWeights[i][j].getMiddle()),
                        weight.getRight().add(expertCriteriaWeights[i][j].getRight()));
            }

            weight = new Fuzzy(weight.getLeft().divide(BigDecimal.valueOf(expertCriteriaWeights.length), 3, RoundingMode.HALF_UP),
                    weight.getMiddle().divide(BigDecimal.valueOf(expertCriteriaWeights.length), 3, RoundingMode.HALF_UP),
                    weight.getRight().divide(BigDecimal.valueOf(expertCriteriaWeights.length), 3, RoundingMode.HALF_UP));

            criteriaWeights[j] = weight;
        }

        Criteria quality = new Criteria("ხარისხი", CriteriaType.BENEFIT, criteriaWeights[0]);
        Criteria deliveryTime = new Criteria("მიწოდების დრო", CriteriaType.BENEFIT, criteriaWeights[1]);
        Criteria cost = new Criteria("ღირებულება", CriteriaType.COST, criteriaWeights[2]);
        Criteria flexibility = new Criteria("მოქნილობა", CriteriaType.BENEFIT, criteriaWeights[3]);
        Criteria geographicalLocation = new Criteria("გეოგრაფიული მდებარეობა", CriteriaType.BENEFIT, criteriaWeights[4]);

        Fuzzy[][] expertAlternativeWeightsQuality = new Fuzzy[][] {
                new Fuzzy[] {
                        LinguisticVariablesAlternative.კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight(),
                        LinguisticVariablesAlternative.კარგი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.ძალიან_კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight(),
                        LinguisticVariablesAlternative.კარგი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_ცუდი.getWeight(),
                        LinguisticVariablesAlternative.კარგი.getWeight()
                }
        };

        Fuzzy[] alternativeWeightsQuality = new Fuzzy[expertAlternativeWeightsQuality[0].length];

        for (int j = 0; j < expertAlternativeWeightsQuality[0].length; j++) {
            Fuzzy weight = new Fuzzy(0, 0, 0);

            for (int i = 0; i < expertAlternativeWeightsQuality.length; i++) {
                weight = new Fuzzy(weight.getLeft().add(expertAlternativeWeightsQuality[i][j].getLeft()),
                        weight.getMiddle().add(expertAlternativeWeightsQuality[i][j].getMiddle()),
                        weight.getRight().add(expertAlternativeWeightsQuality[i][j].getRight()));
            }

            weight = new Fuzzy(weight.getLeft().divide(BigDecimal.valueOf(expertAlternativeWeightsQuality.length), 1, RoundingMode.HALF_UP),
                    weight.getMiddle().divide(BigDecimal.valueOf(expertAlternativeWeightsQuality.length), 1, RoundingMode.HALF_UP),
                    weight.getRight().divide(BigDecimal.valueOf(expertAlternativeWeightsQuality.length), 1, RoundingMode.HALF_UP));

            alternativeWeightsQuality[j] = weight;
        }

        Fuzzy[][] expertAlternativeWeightsDeliveryTime = new Fuzzy[][] {
                new Fuzzy[] {
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight(),
                        LinguisticVariablesAlternative.კარგი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_ცუდი.getWeight(),
                        LinguisticVariablesAlternative.ძალიან_კარგი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight()
                }
        };

        Fuzzy[] alternativeWeightsDeliveryTime = new Fuzzy[expertAlternativeWeightsDeliveryTime[0].length];

        for (int j = 0; j < expertAlternativeWeightsDeliveryTime[0].length; j++) {
            Fuzzy weight = new Fuzzy(0, 0, 0);

            for (int i = 0; i < expertAlternativeWeightsDeliveryTime.length; i++) {
                weight = new Fuzzy(weight.getLeft().add(expertAlternativeWeightsDeliveryTime[i][j].getLeft()),
                        weight.getMiddle().add(expertAlternativeWeightsDeliveryTime[i][j].getMiddle()),
                        weight.getRight().add(expertAlternativeWeightsDeliveryTime[i][j].getRight()));
            }

            weight = new Fuzzy(weight.getLeft().divide(BigDecimal.valueOf(alternativeWeightsDeliveryTime.length), 1, RoundingMode.HALF_UP),
                    weight.getMiddle().divide(BigDecimal.valueOf(expertAlternativeWeightsDeliveryTime.length), 1, RoundingMode.HALF_UP),
                    weight.getRight().divide(BigDecimal.valueOf(expertAlternativeWeightsDeliveryTime.length), 1, RoundingMode.HALF_UP));

            alternativeWeightsDeliveryTime[j] = weight;
        }

        Fuzzy[][] expertAlternativeWeightsCost = new Fuzzy[][] {
                new Fuzzy[] {
                        LinguisticVariablesAlternative.კარგი.getWeight(),
                        LinguisticVariablesAlternative.კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.ცუდი.getWeight(),
                        LinguisticVariablesAlternative.დამაკმაყოფილებელი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.დამაკმაყოფილებელი.getWeight(),
                        LinguisticVariablesAlternative.ძალიან_კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_ცუდი.getWeight()
                }
        };

        Fuzzy[] alternativeWeightsCost = new Fuzzy[expertAlternativeWeightsCost[0].length];

        for (int j = 0; j < expertAlternativeWeightsCost[0].length; j++) {
            Fuzzy weight = new Fuzzy(0, 0, 0);

            for (int i = 0; i < expertAlternativeWeightsCost.length; i++) {
                weight = new Fuzzy(weight.getLeft().add(expertAlternativeWeightsCost[i][j].getLeft()),
                        weight.getMiddle().add(expertAlternativeWeightsCost[i][j].getMiddle()),
                        weight.getRight().add(expertAlternativeWeightsCost[i][j].getRight()));
            }

            weight = new Fuzzy(weight.getLeft().divide(BigDecimal.valueOf(expertAlternativeWeightsCost.length), 1, RoundingMode.HALF_UP),
                    weight.getMiddle().divide(BigDecimal.valueOf(expertAlternativeWeightsCost.length), 1, RoundingMode.HALF_UP),
                    weight.getRight().divide(BigDecimal.valueOf(expertAlternativeWeightsCost.length), 1, RoundingMode.HALF_UP));

            alternativeWeightsCost[j] = weight;
        }

        Fuzzy[][] expertAlternativeWeightsFlexibility= new Fuzzy[][] {
                new Fuzzy[] {
                        LinguisticVariablesAlternative.კარგი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight(),
                        LinguisticVariablesAlternative.ძალიან_კარგი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.ძალიან_კარგი.getWeight(),
                        LinguisticVariablesAlternative.დამაკმაყოფილებელი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_კარგი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.საშუალოდ_ცუდი.getWeight(),
                        LinguisticVariablesAlternative.ცუდი.getWeight(),
                        LinguisticVariablesAlternative.ცუდი.getWeight()
                }
        };

        Fuzzy[] alternativeWeightsFlexibility = new Fuzzy[expertAlternativeWeightsFlexibility[0].length];

        for (int j = 0; j < expertAlternativeWeightsFlexibility[0].length; j++) {
            Fuzzy weight = new Fuzzy(0, 0, 0);

            for (int i = 0; i < expertAlternativeWeightsFlexibility.length; i++) {
                weight = new Fuzzy(weight.getLeft().add(expertAlternativeWeightsFlexibility[i][j].getLeft()),
                        weight.getMiddle().add(expertAlternativeWeightsFlexibility[i][j].getMiddle()),
                        weight.getRight().add(expertAlternativeWeightsFlexibility[i][j].getRight()));
            }

            weight = new Fuzzy(weight.getLeft().divide(BigDecimal.valueOf(expertAlternativeWeightsFlexibility.length), 1, RoundingMode.HALF_UP),
                    weight.getMiddle().divide(BigDecimal.valueOf(expertAlternativeWeightsFlexibility.length), 1, RoundingMode.HALF_UP),
                    weight.getRight().divide(BigDecimal.valueOf(expertAlternativeWeightsFlexibility.length), 1, RoundingMode.HALF_UP));

            alternativeWeightsFlexibility[j] = weight;
        }

        Fuzzy[][] expertAlternativeWeightsGeoLocation = new Fuzzy[][] {
                new Fuzzy[] {
                        LinguisticVariablesAlternative.ცუდი.getWeight(),
                        LinguisticVariablesAlternative.ცუდი.getWeight(),
                        LinguisticVariablesAlternative.ცუდი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.საშუალოდ_ცუდი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_ცუდი.getWeight(),
                        LinguisticVariablesAlternative.ცუდი.getWeight()
                },
                new Fuzzy[] {
                        LinguisticVariablesAlternative.ცუდი.getWeight(),
                        LinguisticVariablesAlternative.ცუდი.getWeight(),
                        LinguisticVariablesAlternative.საშუალოდ_ცუდი.getWeight()
                }
        };

        Fuzzy[] alternativeWeightsGeoLocation = new Fuzzy[expertAlternativeWeightsGeoLocation[0].length];

        for (int j = 0; j < expertAlternativeWeightsGeoLocation[0].length; j++) {
            Fuzzy weight = new Fuzzy(0, 0, 0);

            for (int i = 0; i < expertAlternativeWeightsGeoLocation.length; i++) {
                weight = new Fuzzy(weight.getLeft().add(expertAlternativeWeightsGeoLocation[i][j].getLeft()),
                        weight.getMiddle().add(expertAlternativeWeightsGeoLocation[i][j].getMiddle()),
                        weight.getRight().add(expertAlternativeWeightsGeoLocation[i][j].getRight()));
            }

            weight = new Fuzzy(weight.getLeft().divide(BigDecimal.valueOf(expertAlternativeWeightsGeoLocation.length), 1, RoundingMode.HALF_UP),
                    weight.getMiddle().divide(BigDecimal.valueOf(expertAlternativeWeightsGeoLocation.length), 1, RoundingMode.HALF_UP),
                    weight.getRight().divide(BigDecimal.valueOf(expertAlternativeWeightsGeoLocation.length), 1, RoundingMode.HALF_UP));

            alternativeWeightsGeoLocation[j] = weight;
        }

        Alternative[] first = new Alternative[n];

        Alternative element0 = new Alternative("პირველი", alternativeWeightsQuality[0], quality);
        Alternative element1 = new Alternative("პირველი", alternativeWeightsDeliveryTime[0], deliveryTime);
        Alternative element2 = new Alternative("პირველი", alternativeWeightsCost[0], cost);
        Alternative element3 = new Alternative("პირველი", alternativeWeightsFlexibility[0], flexibility);
        Alternative element4 = new Alternative("პირველი", alternativeWeightsGeoLocation[0], geographicalLocation);

        first[0] = element0;
        first[1] = element1;
        first[2] = element2;
        first[3] = element3;
        first[4] = element4;

        Alternative[] second = new Alternative[n];

        element0 = new Alternative("მეორე", alternativeWeightsQuality[1], quality);
        element1 = new Alternative("მეორე", alternativeWeightsDeliveryTime[1], deliveryTime);
        element2 = new Alternative("მეორე", alternativeWeightsCost[1], cost);
        element3 = new Alternative("მეორე", alternativeWeightsFlexibility[1], flexibility);
        element4 = new Alternative("მეორე", alternativeWeightsGeoLocation[1], geographicalLocation);

        second[0] = element0;
        second[1] = element1;
        second[2] = element2;
        second[3] = element3;
        second[4] = element4;

        Alternative[] third = new Alternative[n];

        element0 = new Alternative("მესამე", alternativeWeightsQuality[2], quality);
        element1 = new Alternative("მესამე", alternativeWeightsDeliveryTime[2], deliveryTime);
        element2 = new Alternative("მესამე", alternativeWeightsCost[2], cost);
        element3 = new Alternative("მესამე", alternativeWeightsFlexibility[2], flexibility);
        element4 = new Alternative("მესამე", alternativeWeightsGeoLocation[2], geographicalLocation);

        third[0] = element0;
        third[1] = element1;
        third[2] = element2;
        third[3] = element3;
        third[4] = element4;

        Alternative[][] matrix = new Alternative[m][n];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;

        return matrix;
    }
}
