package com.zappe.coaster.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zappe.coaster.R;


public class AddFragment extends DialogFragment {
    EditText nameView;
    EditText priceView;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.fragment_add, null);
        nameView = (EditText) view.findViewById(R.id.drink_name_textView);
        nameView.requestFocus();
        nameView.callOnClick();
        priceView = (EditText) view.findViewById(R.id.drink_costs_textView);

        builder.setView(view)
                .setMessage(R.string.edit)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addDrink(view);
                    }
                })
                .setNegativeButton(R.string.abort, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public void addDrink(View view) {
        String name = nameView.getText().toString();
        String costsString = priceView.getText().toString();
        int amount = 1;

        if (name.isEmpty()) {
            Toast.makeText(getActivity(), R.string.name_must_not_be_empty, Toast.LENGTH_SHORT).show();
        }
        if (costsString.isEmpty()) {
            Toast.makeText(getActivity(), R.string.price_must_be_over_0, Toast.LENGTH_SHORT).show();
        }

        if (!name.isEmpty() && !costsString.isEmpty()) {
            double price = Double.valueOf(costsString);
            if (price > 0) {
                MainActivity activity = (MainActivity) getActivity();
                activity.drinks.add(name, amount, price);
                activity.notifyChanged();
            }
        }
    }
}
