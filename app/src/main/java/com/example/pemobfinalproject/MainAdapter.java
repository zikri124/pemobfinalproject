package com.example.pemobfinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<MainModel> mainModels;
    Context context;
    RecyclerViewClickListener listener;

    public MainAdapter (Context context, ArrayList<MainModel> mainModels, RecyclerViewClickListener listener) {
        this.context = context;
        this.mainModels = mainModels;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Initialize Variable
        ImageView imageView;
        TextView textView;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Assign variable
            imageView = itemView.findViewById(R.id.imageView2);
            textView = itemView.findViewById(R.id.txtView);

            item = itemView.findViewById(R.id.item);

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAbsoluteAdapterPosition());
        }
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        //Set Logo to ImageView2
        holder.imageView.setImageResource(mainModels.get(position).getLangLogo());

        //Set Name to TxtView
        holder.textView.setText(mainModels.get(position).getLangName());
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }


}
