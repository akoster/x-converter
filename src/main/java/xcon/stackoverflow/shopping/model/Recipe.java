package xcon.stackoverflow.shopping.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Recipe {
    private Long id;
    private String name;
    private Set<Category> category;
    private String description;
    private String imageUrl;
    private Integer calories;
    private List<Ingredient> ingredients;
}
