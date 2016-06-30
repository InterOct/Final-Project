package by.epam.eshop.entity;

import java.util.Date;
import java.util.Map;

public class Order implements Entity {
    private int id;
    private int userId;
    private Date date;
    private Status status;
    private Map<Product, Integer> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null) {
            this.status = Status.CONSIDERATION;
        } else {
            this.status = Status.getValueStatus(status.toUpperCase());
        }
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (id != order.id) {
            return false;
        }
        if (userId != order.userId) {
            return false;
        }
        if (date != null ? !date.equals(order.date) : order.date != null) {
            return false;
        }
        if (status != order.status) {
            return false;
        }
        return products != null ? !products.equals(order.products) : order.products == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }

    private enum Status {
        CONSIDERATION, PROCESSING, COMPLETED, CANCELLED;

        public static Status getValueStatus(String status) {
            switch (status) {
                case "CONSIDERATION":
                case "ОБРАБОТКА":
                    return CONSIDERATION;
                case "PROCESSING":
                case "ВЫПОЛНЯЕТСЯ":
                    return PROCESSING;
                case "COMPLETED":
                case "ЗАВЕРШЕН":
                    return COMPLETED;
                case "CANCELLED":
                case "ОТМЕНЕН":
                    return CANCELLED;
                default:
                    throw new EnumConstantNotPresentException(Status.class, status);
            }
        }
    }
}
