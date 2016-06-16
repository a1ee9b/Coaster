package zappe.com.coaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import zappe.com.coaster.DrinkHolder.DrinkModel;

public class MainActivity extends AppCompatActivity implements PropertyChangeListener {
    TextView total, totalWithoutTip, addDrinkName, addDrinkCosts;
    Button addDrinkButton;
    DrinkHolder drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total = (TextView) findViewById(R.id.totalTextView);
        totalWithoutTip = (TextView) findViewById(R.id.totalWithoutTip);
        addDrinkName = (TextView) findViewById(R.id.addDrinkName);
        addDrinkCosts = (TextView) findViewById(R.id.addDrinkCosts);
        addDrinkButton = (Button) findViewById(R.id.addDrinkButton);

        drinks = new DrinkHolder();
        drinks.addChangeListener(this);
        drinks.add(new DrinkModel("Anderes", 1.32, 1));

        GridView drinkGrid = (GridView) findViewById(R.id.drinkGrid);
        if(drinkGrid != null) {
            drinkGrid.setAdapter(new DrinkAdapter(this, drinks.getDrinkList()));
        }
    }

    private void calculateTotals() {
        double totalAmount = drinks.getTotal();
        double totalWithoutTipAmount = drinks.getTotalWithoutTip();
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;
        totalWithoutTipAmount = Math.round(totalWithoutTipAmount * 100.0) / 100.0;

        total.setText(String.valueOf(totalAmount));
        totalWithoutTip.setText(String.valueOf(totalWithoutTipAmount));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        calculateTotals();
        System.out.println("Changed property: " + event.getPropertyName() + " [old -> "
                + event.getOldValue() + "] | [new -> " + event.getNewValue() +"]");
    }

    public void addDrink(View view) {
        String name = addDrinkName.getText().toString();
        double costs = Double.valueOf(addDrinkCosts.getText().toString());
        int amount = 1;

        if (!name.isEmpty() && costs > 0) {
            DrinkModel drink = new DrinkModel(name, costs, amount);
            drinks.add(drink);

            addDrinkName.setText("");
            addDrinkCosts.setText("");
        }

    }
}
