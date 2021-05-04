package ge.edu.tsu.objects;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Alternative {
    private String name;
    private Fuzzy weight;
    private Criteria criteria;
    private BigDecimal distance;

    public Alternative(String name, Fuzzy weight, Criteria criteria) {
        this.name = name;
        this.weight = weight;
        this.criteria = criteria;
        this.distance = new BigDecimal(0);
    }
}
