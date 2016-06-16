package zappe.com.coaster;

import java.util.ArrayList;

/**
 * Author jannik
 */
public class DrinkHolder {
    private final MainActivity activity;
    final SQLiteDatabaseHelper db;
    ArrayList<DrinkModel> drinks;

    public DrinkHolder(MainActivity activity, SQLiteDatabaseHelper db) {
        this.db = db;
        this.activity = activity;

        this.drinks = db.getDrinks();
    }

    public ArrayList<DrinkModel> getDrinks() {
        return drinks;
    }

    public DrinkModel get(int i) {
        return drinks.get(i);
    }

    public DrinkHolder add(String name, int amount, double price) {
        DrinkModel drink = new DrinkModel(name, amount, price);
        int id = (int) db.insertDrink(drink);
        if (id > 0) {
            drink.id = id;
            drinks.add(drink);
        }

        return this;
    }

    public void update(int position, DrinkModel drink) {
        db.updateDrink(drink);
        drinks.get(position).update(drink);
    }

    public int increaseCount(int position) {
        DrinkModel drinkModel = this.get(position);
        int count = drinkModel.increaseCount();
        db.updateDrink(drinkModel);
        activity.notifyChanged();

        return count;
    }

    public DrinkHolder remove(int i) {
        db.deleteDrink(drinks.get(i));
        drinks.remove(i);

        return this;
    }

    public double getTotalWithoutTip() {
        double total = 0;
        for (int i = 0; i < drinks.size(); i++) {
            DrinkModel drink = drinks.get(i);
            total += drink.price *drink.amount;
        }

        return total;
    }
    public double getTotal() {
//        Be cheap, use 7%
        double tip = 1.07;
        double total = getTotalWithoutTip();

        return total*tip;
    }
}
