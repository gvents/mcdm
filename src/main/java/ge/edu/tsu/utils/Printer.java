package ge.edu.tsu.utils;

import ge.edu.tsu.objects.Alternative;

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

                System.out.print("(" + matrix[i][j].getWeight().getLeft() + "," + matrix[i][j].getWeight().getMiddle() + "," + matrix[i][j].getWeight().getRight() + ")" + "  ");
            }
            System.out.println();
        }
    }
}
