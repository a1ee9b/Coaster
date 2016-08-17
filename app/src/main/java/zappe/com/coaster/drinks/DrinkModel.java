package zappe.com.coaster.drinks;

import java.io.Serializable;

/**
 * Author Jannik
 */
public class DrinkModel implements Serializable {
    public int id;
    public String name;
    public double price;
    public int amount;

    public DrinkModel(String name, int amount, double price) {
        this(-1, name, price, amount);
    }

    public DrinkModel(int id, String name, double price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public DrinkModel update(DrinkModel drink) {
        this.name = drink.name;
        this.price = drink.price;
        this.amount = drink.amount;

        return this;
    }

    public int increaseCount() {
        this.amount += 1;

        return this.amount;
    }

    @Override
    public String toString() {
        return this.name + " " + this.amount + " mal " + this.price + " Euro";
    }
}