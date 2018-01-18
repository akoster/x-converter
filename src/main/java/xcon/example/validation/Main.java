package xcon.example.validation;

public class Main {

    public static void main(String[] args) {
        Validator<Person> validator = new IsMaleValidator().then(new IsAdultValidator());

        Person mary = new Person("Mary", "F", 2001);
        System.out.printf("All validation messages for %s : %s%n", mary.getName(), validator.validateAll(mary));

        Person john = new Person("John", "M", 1991);
        System.out.printf("Is %s valid? %s%n", john.getName(), validator.isValid(john));
    }
}
