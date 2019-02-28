package com.rashit.tiugaev.image.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.dataBase.DataBase;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteRecAdapter extends RecyclerView.Adapter<FavoriteRecAdapter.FavoritHolder>{
    private Context context;
    private List<DataBase> dataBases;
    private ItemCliick itemCliick;

    public interface ItemCliick {
        void onNoteClick(int position);
    }

    public void setItemCliick(ItemCliick itemCliick) {
        this.itemCliick = itemCliick;
    }

    public FavoriteRecAdapter(Context context, List<DataBase> dataBases) {
        this.context = context;
        this.dataBases = dataBases;
    }


    @NonNull
    @Override
    public FavoritHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
        FavoritHolder favoritHolder = new FavoritHolder(view);
        return favoritHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritHolder holder, int position) {
        Glide.with(context).load(dataBases.get(position).getWeb())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(holder.imageFavorite);
    }

    @Override
    public int getItemCount() {
        return dataBases.size();
    }

    class FavoritHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageFavorite;

        public FavoritHolder(@NonNull View itemView) {
            super(itemView);
            imageFavorite = itemView.findViewById(R.id.imageViewFavorite);
            imageFavorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemCliick != null) {
                itemCliick.onNoteClick(getAdapterPosition());
            }
        }
    }

    public void setItems(List<DataBase> items) {
        this.dataBases = items;
    }

    public List<DataBase> getItems() {
        return dataBases;
    }
}
