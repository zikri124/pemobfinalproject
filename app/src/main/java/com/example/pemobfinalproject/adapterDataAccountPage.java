package com.example.pemobfinalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class adapterDataAccountPage extends RecyclerView.Adapter<adapterDataAccountPage.ListViewHolder> {

    private ArrayList<ListAccountPage> listAccountPages;

    public adapterDataAccountPage(ArrayList<ListAccountPage> listAccountPages) {
        this.listAccountPages = listAccountPages;
    }

    @NonNull
    @NotNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_account, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterDataAccountPage.ListViewHolder holder, int position) {
        ListAccountPage listAccountPage = listAccountPages.get(position);

        Glide.with(holder.iconImage.getContext())
                .load(listAccountPage.getIcon())
                .apply(new RequestOptions().override(24,24))
                .into(holder.iconImage);

        holder.tvIconName.setText(listAccountPage.getListData());
    }

    @Override
    public int getItemCount() {
        return listAccountPages.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView tvIconName;

        public  ListViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.icon);
            tvIconName = itemView.findViewById(R.id.txtAccountPage);
        }
    }
}
