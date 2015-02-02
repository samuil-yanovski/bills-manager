package yanovski.billsmanager.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.R;
import yanovski.billsmanager.adapter.vh.CategoryViewHolder;
import yanovski.billsmanager.daogen.Category;
import yanovski.billsmanager.util.DrawableUtils;
import yanovski.billsmanager.util.ViewUtils;

/**
 * Created by Samuil on 1/19/2015.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private List<Category> categories;

    public CategoriesAdapter() {
        setHasStableIds(true);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(BillsManagerApplication.context, R.layout.category_view, null);
        ViewUtils.setSelectableBackground(rootView);
        return new CategoryViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.title.setText(category.getName());
        Drawable tintedDrawable =
            DrawableUtils.getTintedDrawable(BillsManagerApplication.context.getResources(),
                category.getIconId(), R.color.primary);
        holder.icon.setImageDrawable(tintedDrawable);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position)
            .getId();
    }

    public void addItem(int position, Category category) {
        categories.add(position, category);
    }

    public void removeItem(int position) {
        categories.remove(position);
    }
}
