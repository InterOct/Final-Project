package by.epam.eshop.entity;

import java.util.List;

public class Page implements Entity {
    private List<? extends Entity> list;
    private int numberOfItems;

    public List<? extends Entity> getList() {
        return list;
    }

    public void setList(List<? extends Entity> list) {
        this.list = list;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Page page = (Page) o;

        if (numberOfItems != page.numberOfItems) {
            return false;
        }
        return list != null ? list.equals(page.list) : page.list == null;

    }

    @Override
    public int hashCode() {
        int result = list != null ? list.hashCode() : 0;
        result = 31 * result + numberOfItems;
        return result;
    }
}
