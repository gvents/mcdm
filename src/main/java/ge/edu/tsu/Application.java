package ge.edu.tsu;

import ge.edu.tsu.objects.Alternative;
import ge.edu.tsu.objects.Criteria;
import ge.edu.tsu.objects.Fuzzy;
import ge.edu.tsu.topsis.TopsisMethod;

public class Application {
    public static void main(String[] args) {
        int m = 3;
        int n = 5;

        Criteria quality = new Criteria("ხარისხი", CriteriaType.BENEFIT, new Fuzzy(-1, 0, 1));
        Criteria deliveryTime = new Criteria("მიწოდების დრო", CriteriaType.BENEFIT, new Fuzzy(-1, 0, 1));
        Criteria cost = new Criteria("ღირებულება", CriteriaType.COST, new Fuzzy(-1, 0, 1));
        Criteria flexibility = new Criteria("მოქნილობა", CriteriaType.BENEFIT, new Fuzzy(-1, 0, 1));
        Criteria geographicalLocation = new Criteria("გეოგრაფიული მდებარეობა", CriteriaType.BENEFIT, new Fuzzy(-1, 0, 1));

        Alternative[] first = new Alternative[n];

        Alternative element0 = new Alternative("პირველი", new Fuzzy(-1, 0, 1), quality);
        Alternative element1 = new Alternative("პირველი", new Fuzzy(-1, 0, 1), deliveryTime);
        Alternative element2 = new Alternative("პირველი", new Fuzzy(-1, 0, 1), cost);
        Alternative element3 = new Alternative("პირველი", new Fuzzy(-1, 0, 1), flexibility);
        Alternative element4 = new Alternative("პირველი", new Fuzzy(-1, 0, 1), geographicalLocation);

        first[0] = element0;
        first[1] = element1;
        first[2] = element2;
        first[3] = element3;
        first[4] = element4;

        Alternative[] second = new Alternative[5];

        element0 = new Alternative("მეორე", new Fuzzy(-1, 0, 1), quality);
        element1 = new Alternative("მეორე", new Fuzzy(-1, 0, 1), deliveryTime);
        element2 = new Alternative("მეორე", new Fuzzy(-1, 0, 1), cost);
        element3 = new Alternative("მეორე", new Fuzzy(-1, 0, 1), flexibility);
        element4 = new Alternative("მეორე", new Fuzzy(-1, 0, 1), geographicalLocation);

        second[0] = element0;
        second[1] = element1;
        second[2] = element2;
        second[3] = element3;
        second[4] = element4;

        Alternative[] third = new Alternative[5];

        element0 = new Alternative("მესამე", new Fuzzy(-1, 0, 1), quality);
        element1 = new Alternative("მესამე", new Fuzzy(-1, 0, 1), deliveryTime);
        element2 = new Alternative("მესამე", new Fuzzy(-1, 0, 1), cost);
        element3 = new Alternative("მესამე", new Fuzzy(-1, 0, 1), flexibility);
        element4 = new Alternative("მესამე", new Fuzzy(-1, 0, 1), geographicalLocation);

        third[0] = element0;
        third[1] = element1;
        third[2] = element2;
        third[3] = element3;
        third[4] = element4;

        Alternative[][] matrix = new Alternative[m][n];
        matrix[0] = first;
        matrix[1] = second;
        matrix[2] = third;


        TopsisMethod topsisMethod = new TopsisMethod(matrix);

        topsisMethod.selectBestSupplier();
    }
}
