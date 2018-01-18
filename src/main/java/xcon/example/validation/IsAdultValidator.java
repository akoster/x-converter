package xcon.example.validation;

import java.util.Calendar;

public class IsAdultValidator extends Validator<Person> {
    @Override
    protected String validate(Person person) {
        String message = null;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if ((year - person.getYearOfBirth()) < 18) {
            message = String.format("%s is not an adult", person.getName());
        }
        return message;
    }
}
