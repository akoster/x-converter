package xcon.stackoverflow.shopping.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Meal implements Comparable<Meal> {
    private Long id;
    private LocalDate date;
    private Recipe recipe;
    private String name;

    @Override
    public int compareTo(Meal o) {
        return getName().compareTo(o.getName());
    }
}
