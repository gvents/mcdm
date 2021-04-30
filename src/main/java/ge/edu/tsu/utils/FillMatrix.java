package ge.edu.tsu.utils;

import ge.edu.tsu.CriteriaType;
import ge.edu.tsu.objects.Alternative;
import ge.edu.tsu.objects.Criteria;
import ge.edu.tsu.objects.Fuzzy;

public class FillMatrix {
    public static Alternative[][] fillMatrix(int m, int n) {
        Criteria quality = new Criteria("ხარისხი", CriteriaType.BENEFIT, new Fuzzy(0.633, 0.833, 0.967));
        Criteria deliveryTime = new Criteria("მიწოდების დრო", CriteriaType.BENEFIT, new Fuzzy(0.700, 0.900, 1.000));
        Criteria cost = new Criteria("ღირებულება", CriteriaType.COST, new Fuzzy(0.700, 0.867, 0.967));
        Criteria flexibility = new Criteria("მოქნილობა", CriteriaType.BENEFIT, new Fuzzy(0.233, 0.433, 0.633));
        Criteria geographicalLocation = new Criteria("გეოგრაფიული მდებარეობა", CriteriaType.BENEFIT, new Fuzzy(0.233, 0.433, 0.633));

        Alternative[] first = new Alternative[n];

        Alternative element0 = new Alternative("პირველი", new Fuzzy(7.7, 9.3, 10), quality);
        Alternative element1 = new Alternative("პირველი", new Fuzzy(6.3, 8.3, 9.7), deliveryTime);
        Alternative element2 = new Alternative("პირველი", new Fuzzy(3.3, 5, 6.7), cost);
        Alternative element3 = new Alternative("პირველი", new Fuzzy(5.7, 7.3, 8.3), flexibility);
        Alternative element4 = new Alternative("პირველი", new Fuzzy(0.3, 1.7, 3.7), geographicalLocation);

        first[0] = element0;
        first[1] = element1;
        first[2] = element2;
        first[3] = element3;
        first[4] = element4;

        Alternative[] second = new Alternative[5];

        element0 = new Alternative("მეორე", new Fuzzy(3.7, 5.7, 7.7), quality);
        element1 = new Alternative("მეორე", new Fuzzy(3.7, 5.7, 7.7), deliveryTime);
        element2 = new Alternative("მეორე", new Fuzzy(6.3, 8, 9), cost);
        element3 = new Alternative("მეორე", new Fuzzy(2.7, 4.3, 6.3), flexibility);
        element4 = new Alternative("მეორე", new Fuzzy(0.3, 1.7, 3.7), geographicalLocation);

        second[0] = element0;
        second[1] = element1;
        second[2] = element2;
        second[3] = element3;
        second[4] = element4;

        Alternative[] third = new Alternative[5];

        element0 = new Alternative("მესამე", new Fuzzy(7, 9, 10), quality);
        element1 = new Alternative("მესამე", new Fuzzy(7, 8.7, 9.7), deliveryTime);
        element2 = new Alternative("მესამე", new Fuzzy(3.7, 5.7, 6.3), cost);
        element3 = new Alternative("მესამე", new Fuzzy(4.7, 6, 7.3), flexibility);
        element4 = new Alternative("მესამე", new Fuzzy(0.3, 1.7, 3.7), geographicalLocation);

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
