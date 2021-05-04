package ge.edu.tsu.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Criteria {
    private String name;
    private CriteriaType criteriaType;
    private Fuzzy weight;
}
