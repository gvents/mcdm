package ge.edu.tsu;

import ge.edu.tsu.objects.Alternative;
import ge.edu.tsu.topsis.TopsisMethod;
import ge.edu.tsu.utils.FillMatrix;
import ge.edu.tsu.vikor.VikorMethod;

import java.math.BigDecimal;

public class Application {
    public static void main(String[] args) {
        int alternativeNumber = 3;
        int criteriaNumber = 5;
        int decisionMakerNumber = 3;

        TopsisMethod topsisMethod = new TopsisMethod(FillMatrix.fillMatrix(alternativeNumber, criteriaNumber, decisionMakerNumber), alternativeNumber, criteriaNumber);

        Alternative topsis = topsisMethod.selectBestSupplier();

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        VikorMethod vikorMethod = new VikorMethod(FillMatrix.fillMatrix(alternativeNumber, criteriaNumber, decisionMakerNumber), alternativeNumber, criteriaNumber, BigDecimal.valueOf(0.5));

        Alternative vikor = vikorMethod.selectBestSupplier();

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println();
        System.out.println("საუკეთესო მიმწოდებელი (TOPSIS): " + topsis.getName());
        System.out.println("საუკეთესო მიმწოდებელი (VIKOR): " + vikor.getName());
    }
}
