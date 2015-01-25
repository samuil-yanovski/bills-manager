package yanovski.billsmanager.adapter.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yanovski.billsmanager.R;

/**
 * Created by Samuil on 1/19/2015.
 */
public class ExpenseViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.date)
    public TextView date;
    @InjectView(R.id.description)
    public TextView description;
    @InjectView(R.id.amount)
    public TextView amount;

    public ExpenseViewHolder(View itemView) {
        super(itemView);

        ButterKnife.inject(this, itemView);
    }
}
