package yanovski.billsmanager.adapter.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yanovski.billsmanager.R;
import yanovski.billsmanager.util.ViewUtils;

/**
 * Created by Samuil on 1/19/2015.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.title)
    public TextView title;
    @InjectView(R.id.icon)
    public ImageView icon;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        ButterKnife.inject(this, itemView);
        ViewUtils.setCheckableBackground(itemView);
    }
}
