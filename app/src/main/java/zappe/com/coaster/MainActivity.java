package zappe.com.coaster;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    DrinkAdapter adapter;
    SQLiteDatabaseHelper sqLiteDatabaseHelper;

    public final static int EDIT_RESULT = 1;
    public final static int DELETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total = (TextView) findViewById(R.id.totalTextView);
        totalWithoutTip = (TextView) findViewById(R.id.totalWithoutTip);
        addDrinkName = (TextView) findViewById(R.id.addDrinkName);
        addDrinkCosts = (TextView) findViewById(R.id.addDrinkCosts);
        addDrinkButton = (Button) findViewById(R.id.addDrinkButton);

        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(this);

        drinks = new DrinkHolder(sqLiteDatabaseHelper);
        drinks.addChangeListener(this);

        GridView drinkGrid = (GridView) findViewById(R.id.drinkGrid);
        if(drinkGrid != null) {
            adapter = new DrinkAdapter(this, drinks);
            drinkGrid.setAdapter(adapter);
            calculateTotals();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            DrinkModel drink = (DrinkModel) data.getExtras().getSerializable("drink");
            int position = data.getIntExtra("position", -1);

            if (resultCode == EDIT_RESULT) {
                drinks.update(position, drink);
                adapter.notifyDataSetChanged();
            } else if (resultCode == DELETE) {
                drinks.remove(position);
                adapter.notifyDataSetChanged();
            }
        }
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
        String costsString = addDrinkCosts.getText().toString();
        int amount = 1;

        if (!name.isEmpty() && !costsString.isEmpty()) {

            double costs = Double.valueOf(costsString);
            if (costs > 0) {
                drinks.add(name, amount, costs);
                addDrinkName.setText("");
                addDrinkCosts.setText("");

//                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }

    }
}
