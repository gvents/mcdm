package ge.edu.tsu.utils;

import ge.edu.tsu.objects.Alternative;
import ge.edu.tsu.objects.Fuzzy;

import java.math.BigDecimal;

public class Printer {
    public static void printMatrix(Alternative[][] matrix) {
        String format = "%-15s%-23s%-23s%-23s%-23s%-23s%n";
        for (int i = 0; i < matrix.length; i++) {
            if (i == 0) {
                System.out.format(format, "",
                        matrix[i][0].getCriteria().getName(),
                        matrix[i][1].getCriteria().getName(),
                        matrix[i][2].getCriteria().getName(),
                        matrix[i][3].getCriteria().getName(),
                        matrix[i][4].getCriteria().getName());
                System.out.println();
            }

            for (int j = 0; j < matrix[i].length; j++) {
                if (j == 0) {
                    System.out.print(matrix[i][j].getName() + "   ");
                } else {
                    System.out.print("   ");
                }

                System.out.print(matrix[i][j].getWeight() + "  ");
            }
            System.out.println();
        }
    }

    public static void printCriteriaWeight(Alternative[][] matrix) {
        String format = "%-15s%-23s%-23s%-23s%-23s%-23s%n";

        System.out.format(format, "",
                matrix[0][0].getCriteria().getName(),
                matrix[0][1].getCriteria().getName(),
                matrix[0][2].getCriteria().getName(),
                matrix[0][3].getCriteria().getName(),
                matrix[0][4].getCriteria().getName());
        System.out.println();

        for (int j = 0; j < matrix[0].length; j++) {
            System.out.print(matrix[0][j].getCriteria().getWeight() + "  ");
        }
        System.out.println();
    }


    public static void printFuzzyNumberVector(Fuzzy[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.println(vector[i]);
        }

        System.out.println();
    }

    public static void printAlternatives(Alternative[] alternatives) {
        for (int i = 0; i < alternatives.length; i++) {
            System.out.println(alternatives[i].getName() + "      " + alternatives[i].getDistance());
        }

        System.out.println();
    }

    public static void printAlternativesWithWeights(Alternative[] alternatives) {
        for (int i = 0; i < alternatives.length; i++) {
            System.out.println(alternatives[i].getName() + "      " + alternatives[i].getWeight());
        }

        System.out.println();
    }

    public static void printNegativeIdealDistance(Alternative[] alternatives, BigDecimal[] distance) {
        for (int i = 0; i < alternatives.length; i++) {
            System.out.println(alternatives[i].getName() + "      " + distance[i]);
        }

        System.out.println();
    }
}
