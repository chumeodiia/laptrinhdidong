package com.example.recyclerviewwithindicator_search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewwithindicator_search.R;
import com.example.recyclerviewwithindicator_search.model.IconModel;

import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder> {

    private Context context;
    private List<IconModel> arrayList;

    public IconAdapter(Context context, List<IconModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_icon_promotion, parent, false);
        return new IconHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconHolder holder, int position) {
        IconModel iconModel = arrayList.get(position);
        holder.imgIcon.setImageResource(iconModel.getImgID());
        holder.tvDesc.setText(iconModel.getDesc());
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public static class IconHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvDesc;

        public IconHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }

    // dùng cho phần Search ở ví dụ 2
    public void setFilterList(List<IconModel> filterList) {
        this.arrayList = filterList;
        notifyDataSetChanged();
    }
}
