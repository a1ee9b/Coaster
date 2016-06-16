package zappe.com.coaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AddActivity extends AppCompatActivity {
    TextView addDrinkName, addDrinkCosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addDrinkName = (TextView) findViewById(R.id.addDrinkName);
        addDrinkCosts = (TextView) findViewById(R.id.addDrinkCosts);
    }

    public void addDrink(View view) {
        String name = addDrinkName.getText().toString();
        String costsString = addDrinkCosts.getText().toString();
        int count = 1;

        if (name.isEmpty()) {
            Toast.makeText(this, "Name darf nicht leer sein", Toast.LENGTH_SHORT).show();
        }
        if (costsString.isEmpty()) {
            Toast.makeText(this, "Preis muss ueber 0 liegen", Toast.LENGTH_SHORT).show();
        }

        if (!name.isEmpty() && !costsString.isEmpty()) {
            double costs = Double.valueOf(costsString);
            if (costs > 0) {
                DrinkHolder.DrinkModel drinkModel = new DrinkHolder.DrinkModel(name, costs, count);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("drink", drinkModel);
                setResult(MainActivity.ADD_RESULT, intent);
                finish();
            }
        }
    }
}
