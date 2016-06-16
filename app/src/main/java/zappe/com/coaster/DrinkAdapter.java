package zappe.com.coaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;

import zappe.com.coaster.DrinkHolder.DrinkModel;

/**
 * Created by jannik on 15.06.16.
 */
public class DrinkAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DrinkModel> drinks;

    public DrinkAdapter(Context context, ArrayList<DrinkModel> drinks) {
        this.mContext = context;
        this.drinks = drinks;
    }

    @Override
    public int getCount() {
        return drinks.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View drinkView;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            drinkView = li.inflate(R.layout.drink, parent, false);

            drinkView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, GridView.AUTO_FIT));
        }
        else {
            drinkView = (View) convertView;
        }

        final DrinkHolder.DrinkModel drink = drinks.get(position);

        TextView name = (TextView) drinkView.findViewById(R.id.drink_header_textView);
        TextView costs = (TextView) drinkView.findViewById(R.id.cost_textView);
        Button increase_button = (Button) drinkView.findViewById(R.id.increase_drink_button);

        name.setText(drink.name);
        costs.setText(String.valueOf(drink.costs));
        increase_button.setText(String.valueOf(drink.count));

        increase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = drink.increaseCount();
                Button thisButton = (Button) v.findViewById(R.id.increase_drink_button);
                thisButton.setText(String.valueOf(count));
            }
        });

        return drinkView;
    }
}
