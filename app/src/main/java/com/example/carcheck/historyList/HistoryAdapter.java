package com.example.carcheck.historyList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcheck.R;
import com.example.carcheck.model.TankUpRecord;

import java.text.DateFormat;
import java.util.List;

/*
ADAPTER BUILDING ELEMENTS OF HISTORY LIST USING CAR HISTORY
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<TankUpRecord> tankList;
    private TankUpRecord tankUpRecord;
    private Drawable drawable;

    public HistoryAdapter(Context context, List<TankUpRecord> tankList) {
        this.context = context;
        this.tankList = tankList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_history_item, null);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        tankUpRecord = tankList.get(position);
        drawable = context.getResources().getDrawable(R.drawable.ic_gas_tankup);
        holder.activityImageView.setImageDrawable(drawable);
        DateFormat dateFormat = DateFormat.getDateInstance();
        holder.leftLabelTop.setText(dateFormat.format(tankUpRecord.getTankUpDate()));
        holder.rightLabelTop.setText(tankUpRecord.getMileage().toString() + " km");
        holder.leftLabelBottom.setText(tankUpRecord.getLiters().toString() + " L");
        holder.rightLabelBottom.setText(tankUpRecord.getCostInPLN().toString() + " PLN");
        holder.trashImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tankList.remove(position);
                HistoryAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {

        if (tankList == null) {
            return 0;
        } else {
            return tankList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView leftLabelTop;
        protected TextView leftLabelBottom;
        protected TextView rightLabelTop;
        protected TextView rightLabelBottom;
        protected ImageView trashImageView;
        protected ImageView activityImageView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            this.activityImageView = (ImageView) itemView.findViewById(R.id.activity_image_view);
            this.trashImageView = (ImageView) itemView.findViewById(R.id.trash_image_view);

            this.leftLabelTop = (TextView) itemView.findViewById(R.id.left_label_top);
            this.leftLabelBottom = (TextView) itemView.findViewById(R.id.left_label_bottom);
            this.rightLabelTop = (TextView) itemView.findViewById(R.id.right_label_top);
            this.rightLabelBottom = (TextView) itemView.findViewById(R.id.right_label_bottom);
        }
    }
}
