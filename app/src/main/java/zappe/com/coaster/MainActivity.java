package zappe.com.coaster;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView total, totalWithoutTip;
    Button addDrinkButton;

    DrinkHolder drinks;
    public DrinkAdapter adapter;
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

        drinks = new DrinkHolder(this, sqLiteDatabaseHelper);

        GridView drinkGrid = (GridView) findViewById(R.id.drinkGrid);
        if(drinkGrid != null) {
            adapter = new DrinkAdapter(this, drinks);
            drinkGrid.setAdapter(adapter);
            calculateTotals();
        }
    }

    public void addDrink(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_RESULT);
    }

    private void calculateTotals() {
        double totalAmount = drinks.getTotal();
        double totalWithoutTipAmount = drinks.getTotalWithoutTip();
        totalAmount = new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
        totalWithoutTipAmount = new BigDecimal(totalWithoutTipAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();

        Resources res = getResources();
        total.setText(String.format(res.getString(R.string.price_placeholder), totalAmount));
        totalWithoutTip.setText(String.format(res.getString(R.string.price_placeholder), totalWithoutTipAmount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void paid(MenuItem item) {
        ArrayList<DrinkModel> drinksArrayList = this.drinks.getDrinks();
        for (int i=drinksArrayList.size()-1; i >= 0; i--) {
            drinks.remove(i);
        }
        notifyChanged();

        Toast.makeText(this, R.string.everything_paid, Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            DrinkModel drink = (DrinkModel) data.getExtras().getSerializable("drink");
            int position = data.getIntExtra("position", -1);

            if (drink != null) {
                switch (resultCode) {
                    case ADD_RESULT:
                        drinks.add(drink.name, drink.amount, drink.price);
                        break;
                    case EDIT_RESULT:
                        drinks.update(position, drink);
                        break;
                    case DELETE_RESULT:
                        drinks.remove(position);
                        break;
                }
                adapter.notifyDataSetChanged();
                calculateTotals();
            }
        }
    }

    public void notifyChanged() {
        adapter.notifyDataSetChanged();
        calculateTotals();
    }

}
