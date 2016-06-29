package by.epam.eshop.entity;

public class Product implements Entity {

    private static final long serialVersionUID = 1L;

    private int id;
    private String catName;
    private String name;
    private double price;
    private String imgPath;
    private String description;
    private String shortDescription;
    private double discountPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if (id != product.id) {
            return false;
        }
        if (Double.compare(product.price, price) != 0) {
            return false;
        }
        if (catName != null ? !catName.equals(product.catName) : product.catName != null) {
            return false;
        }
        if (name != null ? !name.equals(product.name) : product.name != null) {
            return false;
        }
        if (imgPath != null ? !imgPath.equals(product.imgPath) : product.imgPath != null) {
            return false;
        }
        if (description != null ? !description.equals(product.description) : product.description != null) {
            return false;
        }
        if (shortDescription != null ? !shortDescription.equals(product.shortDescription) : product.shortDescription != null) {
            return false;
        }
        return discountPrice == product.discountPrice;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (catName != null ? catName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (imgPath != null ? imgPath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        temp = Double.doubleToLongBits(discountPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
