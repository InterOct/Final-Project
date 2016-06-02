package by.epam.eshop.entity;

/**
 * Created by Aspire on 14.05.2016.
 */
public class Category implements Entity {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;

        if (name != null ? !name.equals(category.name) : category.name != null) {
            return false;
        }
        return description != null ? !description.equals(category.description) : category.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
