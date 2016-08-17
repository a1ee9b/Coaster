package zappe.com.coaster;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    TextView subtotal;
    TextView tip;
    TextView total;

    DrinkHolder drinks;
    public DrinkAdapter adapter;
    SQLiteDatabaseHelper sqLiteDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(this);

        drinks = new DrinkHolder(this, sqLiteDatabaseHelper);

        subtotal = (TextView) findViewById(R.id.subtotal);
        tip = (TextView) findViewById(R.id.tip);
        total = (TextView) findViewById(R.id.total);

        GridView drinkGrid = (GridView) findViewById(R.id.drinkGrid);
        if(drinkGrid != null) {
            adapter = new DrinkAdapter(this, drinks);
            drinkGrid.setAdapter(adapter);
            calculateTotals();
        }
    }

    public void addDrink(View view) {
        FragmentManager manager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        Fragment previousFragment = manager.findFragmentByTag("add_drink");
        if (previousFragment != null) {
            fragmentTransaction.remove(previousFragment);
        }

        DialogFragment editFragment = new AddFragment();
        editFragment.show(fragmentTransaction, "add_drink");
    }

    public void calculateTotals() {
        double subtotalAmount = drinks.getSubtotal();
        double totalAmount = drinks.getTotal();

        String subtotalAmountString = NumberFormat.getCurrencyInstance().format(subtotalAmount);
        String tipString = NumberFormat.getCurrencyInstance().format(totalAmount-subtotalAmount);
        String totalAmountString = NumberFormat.getCurrencyInstance().format(totalAmount);

        subtotal.setText(subtotalAmountString);
        tip.setText(tipString);
        total.setText(totalAmountString);
    }

    public void notifyChanged() {
        adapter.notifyDataSetChanged();
        calculateTotals();
    }

}
