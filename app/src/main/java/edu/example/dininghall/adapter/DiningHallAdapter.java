package edu.example.dininghall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.example.dininghall.R;
import edu.example.dininghall.pojo.DiningHall;
import edu.example.dininghall.variable.Level;

public class DiningHallAdapter extends RecyclerView.Adapter<DiningHallAdapter.ViewHolder> {

    private final List<DiningHall> mDiningHalls;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView diningImage;
        TextView diningNumber;
        TextView diningName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diningImage = itemView.findViewById(R.id.dining_state);
            diningNumber = itemView.findViewById(R.id.dining_number);
            diningName = itemView.findViewById(R.id.dining_name);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param diningHalls List<DiningHall> containing the data to populate views to be used
     *                    by RecyclerView.
     */
    public DiningHallAdapter(List<DiningHall> diningHalls) {
        this.mDiningHalls = diningHalls;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.dining_hall_item, parent, false);
        DiningHallAdapter.ViewHolder holder = new DiningHallAdapter.ViewHolder(view);
        return holder;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DiningHall diningHall = mDiningHalls.get(position);
        if (diningHall.getState() == Level.NORMAL) {
            holder.diningImage.setImageResource(R.drawable.cir_g);
        } else if (diningHall.getState() == Level.CROWDED) {
            holder.diningImage.setImageResource(R.drawable.cir_y);
        } else {
            holder.diningImage.setImageResource(R.drawable.cir_r);
        }
        holder.diningName.setText(diningHall.getName());
        if (diningHall.getNumber() <= 100)
            holder.diningNumber.setText("" + diningHall.getNumber());
        else
            holder.diningNumber.setText("100+");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDiningHalls.size();
    }
}


