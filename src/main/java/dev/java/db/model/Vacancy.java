package dev.java.db.model;

public class Vacancy extends Entity {
    private String name;
    private int minExperienceInYears;
    private int maxExperienceInYears;
    private int minSalaryInDollars;
    private int maxSalaryInDollars;

    public Vacancy() {
    }

    public Vacancy(long id, String name, int minExperienceInYears, int maxExperienceInYears, int minSalaryInDollars, int maxSalaryInDollars) {
        super(id);
        this.name = name;
        this.minExperienceInYears = minExperienceInYears;
        this.maxExperienceInYears = maxExperienceInYears;
        this.minSalaryInDollars = minSalaryInDollars;
        this.maxSalaryInDollars = maxSalaryInDollars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinExperienceInYears() {
        return minExperienceInYears;
    }

    public void setMinExperienceInYears(int minExperienceInYears) {
        this.minExperienceInYears = minExperienceInYears;
    }

    public int getMaxExperienceInYears() {
        return maxExperienceInYears;
    }

    public void setMaxExperienceInYears(int maxExperienceInYears) {
        this.maxExperienceInYears = maxExperienceInYears;
    }

    public int getMinSalaryInDollars() {
        return minSalaryInDollars;
    }

    public void setMinSalaryInDollars(int minSalaryInDollars) {
        this.minSalaryInDollars = minSalaryInDollars;
    }

    public int getMaxSalaryInDollars() {
        return maxSalaryInDollars;
    }

    public void setMaxSalaryInDollars(int maxSalaryInDollars) {
        this.maxSalaryInDollars = maxSalaryInDollars;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "name='" + name + '\'' +
                ", minExperienceInYears=" + minExperienceInYears +
                ", maxExperienceInYears=" + maxExperienceInYears +
                ", minSalaryInDollars=" + minSalaryInDollars +
                ", maxSalaryInDollars=" + maxSalaryInDollars +
                '}';
    }
}
