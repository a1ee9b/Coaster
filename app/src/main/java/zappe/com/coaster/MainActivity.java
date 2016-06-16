package zappe.com.coaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
        drinks.add(new DrinkModel("Anderes", 1.2, 1));

        GridView drinkGrid = (GridView) findViewById(R.id.drinkGrid);
        if(drinkGrid != null) {
            drinkGrid.setAdapter(new DrinkAdapter(this, drinks.getDrinkList()));
        }

        drinks.getDrinkList().get(1).editCost(2.32);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.drink_menu, menu);
        menu.setHeaderTitle("Bearbeite das Getränk");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_amount:
//                drink.editCount()
                Toast.makeText(this, "Menge gewählt", Toast.LENGTH_LONG).show();
                break;

            case R.id.action_change_costs:
                Toast.makeText(this, "Preis gewählt", Toast.LENGTH_LONG).show();
                break;

            case R.id.action_delete_drink:
                Toast.makeText(this, "Löschen gewählt", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void calculateTotals() {
        double totalAmount = drinks.getTotal();
        double totalWithoutTipAmount = drinks.getTotalWithoutTip();
        totalAmount = new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
        totalWithoutTipAmount = new BigDecimal(totalWithoutTipAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();

        total.setText(String.format("%.2f", totalAmount)+" €");
        totalWithoutTip.setText(String.format("%.2f", totalWithoutTipAmount)+" €");
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
