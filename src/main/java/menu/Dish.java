package menu;

import javax.persistence.*;

@Entity
@Table(name = "Dishes")
public class Dish {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double weight;

    private int sale;

    public Dish() {
    }

    public Dish(String name, double price, double weight, int sale) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.sale = sale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "Dishes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", sale=" + sale +
                '}';
    }
}
