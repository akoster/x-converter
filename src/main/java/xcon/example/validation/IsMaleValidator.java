package xcon.example.validation;

public class IsMaleValidator extends Validator<Person> {
    @Override
    protected String validate(Person person) {
        String message = null;
        if (person.getGender() != Person.Gender.M) {
            message = String.format("%s is not a male", person.getName());
        }
        return message;
    }
}
