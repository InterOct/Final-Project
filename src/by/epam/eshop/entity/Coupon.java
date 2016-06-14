package by.epam.eshop.entity;

public class Coupon implements Entity {
    private int id;
    private int userId;
    private int productId;
    private byte discount;

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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public byte getDiscount() {
        return discount;
    }

    public void setDiscount(byte discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Coupon coupon = (Coupon) o;

        if (id != coupon.id) {
            return false;
        }
        if (userId != coupon.userId) {
            return false;
        }
        if (productId != coupon.productId) {
            return false;
        }
        return discount == coupon.discount;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + productId;
        result = 31 * result + discount;
        return result;
    }
}
