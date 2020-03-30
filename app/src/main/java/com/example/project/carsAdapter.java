package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class carsAdapter extends RecyclerView.Adapter<carsAdapter.ViewHolder> {
    private List<String> mcars;
    private OnCarListener mOnCarListener;
    public carsAdapter(List<String> cars,OnCarListener OnCarListener) {
        mcars = cars;
        this.mOnCarListener = OnCarListener;
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View carsView = inflater.inflate(R.layout.caritem, parent, false);
        ViewHolder viewHolder = new ViewHolder(carsView,mOnCarListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(carsAdapter.ViewHolder holder, int position) {
        String car = mcars.get(position);
        TextView name = holder.carName;
        name.setText(car);
    }

    @Override
    public int getItemCount() {
        return mcars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView carName;
        OnCarListener onCarListener;
        public ViewHolder(View itemView,OnCarListener onCarListener){
            super(itemView);
            carName = (TextView) itemView.findViewById(R.id.car_text);
            this.onCarListener = onCarListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCarListener.onCarClick(getAdapterPosition());
        }
    }
    public interface OnCarListener{
        void onCarClick(int pos);
    }
}
