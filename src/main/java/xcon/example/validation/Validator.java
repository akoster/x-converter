package xcon.example.validation;

import java.util.ArrayList;
import java.util.List;

public abstract class Validator<T> {

    private Validator<T> next;

    public Validator<T> then(Validator<T> next) {
        next.next = this;
        return next;
    }

    public List<String> validateAll(T state) {
        List<String> messages = new ArrayList<>();
        chain(state, messages);
        return messages;
    }

    public boolean isValid(T state) {
        return chain(state, null);
    }

    protected boolean chain(T state, List<String> messages) {
        String message = validate(state);
        if (message != null) {
            if (messages != null) {
                messages.add(message);
            } else {
                return false;
            }
        }
        if (next != null) {
            return next.chain(state, messages);
        } else {
            return true;
        }
    }

    protected abstract String validate(T state);


}
