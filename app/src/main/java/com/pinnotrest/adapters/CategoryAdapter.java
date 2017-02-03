package com.pinnotrest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pinnotrest.R;
import com.pinnotrest.model.Category;

import java.util.ArrayList;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {
    private Context mContext;
    private ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> category) {
        this.mContext = context;
        this.categories = category;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.category_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.mCategoryName.setText(categories.get(position).getTitle());
        holder.mPhotoCount.setText(categories.get(position).getPhotoCount() + " Photos.");
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView mCategoryName, mPhotoCount;

        public Holder(View itemView) {
            super(itemView);
            mCategoryName = (TextView) itemView.findViewById(R.id.category_name_tv);
            mPhotoCount = (TextView) itemView.findViewById(R.id.photocount_tv);
        }

    }


}
