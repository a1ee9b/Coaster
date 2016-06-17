package zappe.com.coaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    DrinkModel drink;
    int position;
    TextView nameView, amountView, priceView;
    Button saveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle(R.string.edit);

        nameView = (TextView) findViewById(R.id.name_edit_textView);
        amountView = (TextView) findViewById(R.id.amount_edit_textView);
        priceView = (TextView) findViewById(R.id.costs_edit_textView);
        saveEdit = (Button) findViewById(R.id.save_edit_button);

        Intent intent = getIntent();
        drink = (DrinkModel) intent.getSerializableExtra("drink");
        position = intent.getIntExtra("position", -1);

        if (drink != null) {
            nameView.setText(drink.name);
            amountView.setText(String.valueOf(drink.amount));
            priceView.setText(String.valueOf(drink.price));
        }
    }

    public void saveEdit(View view) {
        String name = nameView.getText().toString();
        if (!name.isEmpty()) {
            drink.name = name;
        } else {
            Toast.makeText(this, R.string.name_must_not_be_empty, Toast.LENGTH_SHORT).show();
        }
        String price = priceView.getText().toString();
        if (!price.isEmpty() || Double.valueOf(price) > 0) {
            drink.price = Double.valueOf(price);
        } else {
            Toast.makeText(this, R.string.price_must_be_over_0, Toast.LENGTH_SHORT).show();
        }
        String amount = amountView.getText().toString();
        if (!amount.isEmpty() || Integer.valueOf(amount) > 0) {
            drink.amount = Integer.valueOf(amount);
        } else {
            Toast.makeText(this, R.string.amount_must_be_over_0, Toast.LENGTH_SHORT).show();
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
        setResult(MainActivity.DELETE_RESULT, intent);
        finish();
    }
}
