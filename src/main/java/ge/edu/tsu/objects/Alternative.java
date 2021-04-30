package ge.edu.tsu.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Alternative {
    private String name;
    private Fuzzy weight;
    private Criteria criteria;
}
