package com.rashit.tiugaev.image.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rashit.tiugaev.image.pojo.Hit;
import com.rashit.tiugaev.image.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ItemCliick itemCliick;

    private Context context;
    private List<Hit> data;

    public interface ItemCliick {
        void onNoteClick(int position, View view);
    }

    public void setItemCliick(ItemCliick itemCliick) {
        this.itemCliick = itemCliick;
    }

    public RecyclerViewAdapter(Context context, List<Hit> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyckler_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Glide.with(context)
                .load(data.get(position).getWebformatURL())
                .placeholder(R.drawable.pixabay)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .error(R.drawable.error)
                .into(holder.imagePoster);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imagePoster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            imagePoster.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (itemCliick != null) {
                itemCliick.onNoteClick(getAdapterPosition(),v);
            }
        }
    }
}
