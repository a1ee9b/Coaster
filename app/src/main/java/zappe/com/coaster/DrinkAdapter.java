package zappe.com.coaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        return drinks.getDrinks().size();
    }

    @Override
    public Object getItem(int position) {
        return drinks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View drinkView;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            drinkView = li.inflate(R.layout.drink, parent, false);

            GridView gridView = (GridView) parent;
            int columnWidth = gridView.getColumnWidth();

            drinkView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, columnWidth));
            drinkView.setPadding(0, 20, 0, 20);
        }
        else {
            drinkView = convertView;
        }

        final DrinkModel drink = drinks.get(position);

        TextView name = (TextView) drinkView.findViewById(R.id.drink_header_textView);
        TextView costs = (TextView) drinkView.findViewById(R.id.cost_textView);
        final TextView amount = (TextView) drinkView.findViewById(R.id.amount_textView);

        name.setText(drink.name);
        double priceRounded = new BigDecimal(drink.price).setScale(2, RoundingMode.HALF_UP).doubleValue();;
        costs.setText(String.format("%.2f", priceRounded)+" €");
        amount.setText(String.valueOf(drink.amount));

        drinkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = drinks.increaseCount(position);
                amount.setText(String.valueOf(count));
            }
        });

        drinkView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("drink", drink);
                intent.putExtra("position", position);
                ((Activity)mContext).startActivityForResult(intent, MainActivity.EDIT_RESULT);

                return false;
            }
        });

        return drinkView;
    }

}
