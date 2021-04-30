package ge.edu.tsu;

import ge.edu.tsu.topsis.TopsisMethod;
import ge.edu.tsu.utils.FillMatrix;

public class Application {
    public static void main(String[] args) {
        int m = 3;
        int n = 5;

        TopsisMethod topsisMethod = new TopsisMethod(FillMatrix.fillMatrix(m, n));

        topsisMethod.selectBestSupplier();
    }
}
