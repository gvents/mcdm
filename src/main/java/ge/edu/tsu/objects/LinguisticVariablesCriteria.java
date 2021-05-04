package ge.edu.tsu.objects;

public enum LinguisticVariablesCriteria {
    ძალიან_დაბალი(new Fuzzy(0, 0, 0.1)),
    დაბალი(new Fuzzy(0, 0.1, 0.3)),
    საშუალოდ_დაბალი(new Fuzzy(0.1, 0.3, 0.5)),
    საშუალო(new Fuzzy(0.3, 0.5, 0.7)),
    საშუალოდ_მაღალი(new Fuzzy(0.5, 0.7, 0.9)),
    მაღალი(new Fuzzy(0.7, 0.9, 1)),
    ძალიან_მაღალი(new Fuzzy(0.9, 1, 1));

    private final Fuzzy weight;

    public Fuzzy getWeight() {
        return weight;
    }

    LinguisticVariablesCriteria(Fuzzy weight) {
        this.weight = weight;
    }
}
