package zappe.com.coaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import zappe.com.coaster.DrinkHolder.DrinkModel;

public class EditActivity extends AppCompatActivity {
    DrinkModel drink;
    int position;
    TextView nameView, amountView, costsView;
    Button saveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameView = (TextView) findViewById(R.id.name_edit_textView);
        amountView = (TextView) findViewById(R.id.amount_edit_textView);
        costsView = (TextView) findViewById(R.id.costs_edit_textView);
        saveEdit = (Button) findViewById(R.id.save_edit_button);

        drink = (DrinkModel) getIntent().getSerializableExtra("drink");
        position = getIntent().getIntExtra("position", -1);

        if (drink != null) {
            nameView.setText(drink.name);
            amountView.setText(String.valueOf(drink.count));
            costsView.setText(String.valueOf(drink.costs));
        }
    }

    public void saveEdit(View view) {
        String name = nameView.getText().toString();
        if (!name.isEmpty()) {
            drink.setName(name);
        } else {
            Toast.makeText(this, "Name darf nicht leer sein", Toast.LENGTH_SHORT).show();
        }
        double costs = Double.valueOf(costsView.getText().toString());
        if (costs > 0) {
            drink.setCost(costs);
        } else {
            Toast.makeText(this, "Preis muss ueber 0 liegen", Toast.LENGTH_SHORT).show();
        }
        int count = Integer.valueOf(amountView.getText().toString());
        if (count > 0) {
            drink.setCount(count);
        } else {
            Toast.makeText(this, "Anzahl muss ueber 0 sein", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("drink", drink);
        intent.putExtra("position", position);
        setResult(MainActivity.EDIT_RESULT, intent);
        finish();
    }

    public void deleteDrink(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("drink", drink);
        intent.putExtra("position", position);
        setResult(MainActivity.DELETE, intent);
        finish();
    }
}
