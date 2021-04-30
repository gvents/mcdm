package ge.edu.tsu.objects;

import ge.edu.tsu.CriteriaType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Criteria {
    private String name;
    private CriteriaType criteriaType;
    private Fuzzy weight;
}
