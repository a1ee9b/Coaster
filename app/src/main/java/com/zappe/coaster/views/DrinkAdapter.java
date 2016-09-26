package com.zappe.coaster.views;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import com.zappe.coaster.R;
import com.zappe.coaster.drinks.DrinkHolder;
import com.zappe.coaster.drinks.DrinkModel;

/**
 * Author jannik
 */
public class DrinkAdapter extends BaseAdapter {
    private Context mContext;
    private final DrinkHolder drinks;

    public DrinkAdapter(Context context, DrinkHolder drinks) {
        this.mContext = context;
        this.drinks = drinks;
    }

    @Override
    public int getCount() {
        return drinks.getDrinks().size()+1;
    }

    @Override
    public Object getItem(int position) {
        if (position >= drinks.getDrinks().size()) {
            return null;
        } else {
            return drinks.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View drinkView;

        if (position == drinks.getDrinks().size()) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            drinkView = li.inflate(R.layout.add_drink, parent, false);
            GridView gridView = (GridView) parent;
            int columnWidth = gridView.getColumnWidth();

            drinkView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, columnWidth));
        } else {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            drinkView = li.inflate(R.layout.drink, parent, false);

            GridView gridView = (GridView) parent;
            int columnWidth = gridView.getColumnWidth();

            drinkView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, columnWidth));

            final DrinkModel drink = drinks.get(position);

            final LinearLayout outerLayout = (LinearLayout) drinkView.findViewById(R.id.outerLayout);
            final TextView name = (TextView) drinkView.findViewById(R.id.drink_header_textView);
            final TextView costs = (TextView) drinkView.findViewById(R.id.cost_textView);
            final TextView amount = (TextView) drinkView.findViewById(R.id.amount_textView);

            name.setText(drink.name);
            costs.setText(NumberFormat.getCurrencyInstance().format(drink.price));
            amount.setText(String.valueOf(drink.amount));

            outerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = drinks.increaseCount(position);
                    amount.setText(String.valueOf(count));
//                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
//                            R.animator.animate_bg);
//                    set.setTarget(amount);
//                    set.start();
                }
            });

            outerLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    FragmentManager manager = ((Activity) mContext).getFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    Fragment previousFragment = manager.findFragmentByTag("edit_drink");
                    if (previousFragment != null) {
                        fragmentTransaction.remove(previousFragment);
                    }

                    DialogFragment editFragment = EditFragment.newInstance(drink, position);
                    editFragment.show(fragmentTransaction, "edit_drink");
                    return false;
                }
            });
        }

        return drinkView;
    }
}
