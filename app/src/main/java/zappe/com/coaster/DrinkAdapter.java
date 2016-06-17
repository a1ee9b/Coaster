package zappe.com.coaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

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
        if (position == drinks.getDrinks().size()) {
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
        View drinkView;
        Resources res = mContext.getResources();

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

            LinearLayout innerLayout = (LinearLayout) drinkView.findViewById(R.id.innerLayout);
            TextView name = (TextView) drinkView.findViewById(R.id.drink_header_textView);
            TextView costs = (TextView) drinkView.findViewById(R.id.cost_textView);
            final TextView amount = (TextView) drinkView.findViewById(R.id.amount_textView);

            name.setText(drink.name);
            costs.setText(NumberFormat.getCurrencyInstance().format(drink.price));
            amount.setText(String.valueOf(drink.amount));

            innerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = drinks.increaseCount(position);
                    amount.setText(String.valueOf(count));
                }
            });

            innerLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(mContext, EditActivity.class);
                    intent.putExtra("drink", drink);
                    intent.putExtra("position", position);
                    ((Activity) mContext).startActivityForResult(intent, MainActivity.EDIT_RESULT);

                    return false;
                }
            });
        }

        return drinkView;
    }

}
