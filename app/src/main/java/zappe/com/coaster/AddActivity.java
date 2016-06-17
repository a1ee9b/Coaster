package zappe.com.coaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    TextView addDrinkName, addDrinkCosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle(R.string.add);

        addDrinkName = (TextView) findViewById(R.id.addDrinkName);
        addDrinkCosts = (TextView) findViewById(R.id.addDrinkCosts);
    }

    public void addDrink(View view) {
        String name = addDrinkName.getText().toString();
        String costsString = addDrinkCosts.getText().toString();
        int count = 1;

        if (name.isEmpty()) {
            Toast.makeText(this, R.string.name_must_not_be_empty, Toast.LENGTH_SHORT).show();
        }
        if (costsString.isEmpty()) {
            Toast.makeText(this, R.string.price_must_be_over_0, Toast.LENGTH_SHORT).show();
        }

        if (!name.isEmpty() && !costsString.isEmpty()) {
            double costs = Double.valueOf(costsString);
            if (costs > 0) {
                DrinkModel drinkModel = new DrinkModel(name, count, costs);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("drink", drinkModel);
                setResult(MainActivity.ADD_RESULT, intent);
                finish();
            }
        }
    }
}
