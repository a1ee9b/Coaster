package zappe.com.coaster;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jannik on 15.06.16.
 */
public class DrinkHolder {
    ArrayList<DrinkModel> drinks;
    private static final List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    public static final String AMOUNT = "amount";
    public static final String ADDED = "added";

    public DrinkHolder() {
        drinks = new ArrayList<DrinkModel>();
        add(new DrinkModel("Bier", 1.50, 3));
        add(new DrinkModel("Jägermeister", 1.50, 4));
        add(new DrinkModel("Wein", 4, 2));
        add(new DrinkModel("Wasser", 1, 1));
    }

    public ArrayList<DrinkModel> getDrinkList() {
        return drinks;
    }

    public DrinkHolder add(DrinkModel drink) {
        drinks.add(drink);

        notifyListeners(this,
                ADDED,
                "",
                drink.toString());

        return this;
    }
    
    public double getTotalWithoutTip() {
        double total = 0;
        for (int i = 0; i < drinks.size(); i++) {
            DrinkModel drink = drinks.get(i);
            total += drink.costs*drink.count;
        }

        return total;
    }
    public double getTotal() {
//        Be cheap, use 7%
        double tip = 1.07;
        double total = getTotalWithoutTip();

        return total*tip;
    }

    public static final class DrinkModel {
        String name;
        double costs;
        int count;

        public DrinkModel(String name, double costs, int count) {
            this.name = name;
            this.costs = costs;
            this.count = count;
        }

        public int increaseCount() {
            notifyListeners(this,
                    AMOUNT,
                    String.valueOf(this.count),
                    String.valueOf(this.count = count+1));

            return this.count;
        }

        private void notifyListeners(Object object, String property, String oldValue, String newValue) {
            for (PropertyChangeListener name : listener) {
                name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
            }
        }
    }

    private void notifyListeners(Object object, String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

}
