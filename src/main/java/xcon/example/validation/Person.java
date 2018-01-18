package xcon.example.validation;

public class Person {

    private int yearOfBirth;
    private String name;

    public enum Gender {
        M, F
    }

    private Gender gender;

    public Person(String name, String gender, int yearOfBirth) {
        this.name = name;
        this.gender = Gender.valueOf(gender.toUpperCase());
        this.yearOfBirth = yearOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

}