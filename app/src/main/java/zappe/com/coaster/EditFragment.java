package zappe.com.coaster;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditFragment extends DialogFragment {
    DrinkModel drink;
    int position;

    EditText nameView;
    TextView amountView;
    EditText priceView;
    ImageView upButton;
    ImageView downButton;

    public static EditFragment newInstance(DrinkModel drink, int position) {
        EditFragment fragment = new EditFragment();
        fragment.drink = drink;
        fragment.position = position;

        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_edit, null);
        nameView = (EditText) view.findViewById(R.id.drink_name_textView);
        priceView = (EditText) view.findViewById(R.id.drink_costs_textView);
        amountView = (TextView) view.findViewById(R.id.amount_edit_textView);
        upButton = (ImageView) view.findViewById(R.id.amount_up);
        downButton = (ImageView) view.findViewById(R.id.amount_down);

        if (drink != null) {
            nameView.setText(drink.name);
            amountView.setText(String.valueOf(drink.amount));
            priceView.setText(String.valueOf(drink.price));

            upButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(String.valueOf(amountView.getText()));
                    amount = amount + 1;
                    amountView.setText(String.valueOf(amount));
                }
            });

            downButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(String.valueOf(amountView.getText()));
                    amount = amount - 1;
                    amountView.setText(String.valueOf(amount));
                }
            });
        }

        builder.setView(view)
                .setMessage(R.string.edit)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveEdit(view);
                    }
                })
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteDrink(view);
                    }
                });
        return builder.create();
    }

    public void saveEdit(View view) {
        String name = nameView.getText().toString();
        if (!name.isEmpty()) {
            drink.name = name;
        } else {
            Toast.makeText(getActivity(), R.string.name_must_not_be_empty, Toast.LENGTH_SHORT).show();
        }
        String price = priceView.getText().toString();
        if (!price.isEmpty() || Double.valueOf(price) > 0) {
            drink.price = Double.valueOf(price);
        } else {
            Toast.makeText(getActivity(), R.string.price_must_be_over_0, Toast.LENGTH_SHORT).show();
        }
        String amount = String.valueOf(amountView.getText());
        if (!amount.isEmpty() || Integer.valueOf(amount) > 0) {
            drink.amount = Integer.valueOf(amount);
        } else {
            Toast.makeText(getActivity(), R.string.amount_must_be_over_0, Toast.LENGTH_SHORT).show();
        }

        MainActivity activity = (MainActivity) getActivity();
        activity.drinks.update(position, drink);
        activity.notifyChanged();
    }

    public void deleteDrink(View view) {
        MainActivity activity = (MainActivity) getActivity();
        activity.drinks.remove(position);
        activity.notifyChanged();
    }
}
