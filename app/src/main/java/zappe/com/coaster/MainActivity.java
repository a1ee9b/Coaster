package zappe.com.coaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    TextView total, totalWithoutTip;
    Button addDrinkButton;
    DrinkHolder drinks;
    DrinkAdapter adapter;
    SQLiteDatabaseHelper sqLiteDatabaseHelper;

    public final static int ADD_RESULT = 1;
    public final static int EDIT_RESULT = 2;
    public final static int DELETE_RESULT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total = (TextView) findViewById(R.id.totalTextView);
        totalWithoutTip = (TextView) findViewById(R.id.totalWithoutTip);
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

            if (drink != null) {
                switch (resultCode) {
                    case ADD_RESULT:
                        drinks.add(drink.name, drink.count, drink.costs);
                        break;
                    case EDIT_RESULT:
                        drinks.update(position, drink);
                        break;
                    case DELETE_RESULT:
                        drinks.remove(position);
                        break;
                }
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
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_RESULT);
    }
}
