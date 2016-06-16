package zappe.com.coaster;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jannik on 15.06.16.
 */
public class DrinkHolder {
    ArrayList<DrinkModel> drinks;
    private static final List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    public static final String AMOUNT = "amountView";
    public static final String COSTS = "costsView";
    public static final String ADDED = "added";
    public static final String REMOVED = "removed";

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

    public DrinkModel get(int i) {
        return drinks.get(i);
    }

    public DrinkHolder add(DrinkModel drink) {
        drinks.add(drink);

        notifyListeners(this,
                ADDED,
                "",
                drink.toString());

        return this;
    }

    public DrinkHolder remove(int i) {
        DrinkModel drink = drinks.remove(i);

        notifyListeners(this,
                REMOVED,
                drink.toString(),
                "");

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

    public static final class DrinkModel implements Serializable {
        String name;
        double costs;
        int count;

        public DrinkModel(String name, double costs, int count) {
            this.name = name;
            this.costs = costs;
            this.count = count;
        }

        public DrinkModel update(DrinkModel drink) {
            this.setName(drink.name);
            this.setCost(drink.costs);
            this.setCount(drink.count);

            return this;
        }

        public int increaseCount() {
            notifyListeners(this,
                AMOUNT,
                String.valueOf(this.count),
                String.valueOf(this.count = count+1));

            return this.count;
        }

        public String setName(String name) {
            if (!name.isEmpty()) {
                notifyListeners(this,
                        AMOUNT,
                        this.name,
                        this.name = name);
            }

            return name;
        }
        public int setCount(int i) {
            this.count = i;

            notifyListeners(this,
                    AMOUNT,
                    String.valueOf(this.count),
                    String.valueOf(i));

            return this.count;
        }

        public double setCost(double costs) {
            double previousCosts = this.costs;

            if (costs > 0) {
                this.costs = costs;

                notifyListeners(this,
                    AMOUNT,
                    String.valueOf(previousCosts),
                    String.valueOf(costs));
            }

            return costs;
        }

        private void notifyListeners(Object object, String property, String oldValue, String newValue) {
            for (PropertyChangeListener name : listener) {
                name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
            }
        }

        @Override
        public String toString() {
            return this.name+" "+this.count+" mal "+this.costs+" Euro";
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
