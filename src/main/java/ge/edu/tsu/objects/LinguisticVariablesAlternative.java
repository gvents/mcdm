package ge.edu.tsu.objects;

public enum LinguisticVariablesAlternative {
    ძალიან_ცუდი(new Fuzzy(0, 0, 1)),
    ცუდი(new Fuzzy(0, 1, 3)),
    საშუალოდ_ცუდი(new Fuzzy(1, 3, 5)),
    დამაკმაყოფილებელი(new Fuzzy(3, 5, 7)),
    საშუალოდ_კარგი(new Fuzzy(5, 7, 9)),
    კარგი(new Fuzzy(7, 9, 10)),
    ძალიან_კარგი(new Fuzzy(9, 10, 10));

    private final Fuzzy weight;

    public Fuzzy getWeight() {
        return weight;
    }

    LinguisticVariablesAlternative(Fuzzy weight) {
        this.weight = weight;
    }
}
