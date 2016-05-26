package by.epam.eshop.entity;

/**
 * Created by Aspire on 15.05.2016.
 */
public class Product implements Entity {

    private static final long serialVersionUID = 1L;

    private int id;
    private String catName;
    private String name;
    private double price;
    private String producer;
    private String imgPath;
    private String description;

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

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) {
            return false;
        }
        if (id != product.getId()) {
            return false;
        }
        if (catName == null || !catName.equals(product.getCatName())) {
            return false;
        }
        if (name == null || !name.equals(product.getName())) {
            return false;
        }
        if (producer == null || !producer.equals(product.getProducer())) {
            return false;
        }
        if (imgPath == null || !imgPath.equals(product.getProducer())) {
            return false;
        }
        return !(description == null || !description.equals(product.getDescription()));

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + catName.hashCode();
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + producer.hashCode();
        result = 31 * result + imgPath.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
