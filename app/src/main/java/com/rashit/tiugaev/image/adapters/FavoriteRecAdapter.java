package com.rashit.tiugaev.image.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    private List<DataBase> notes;
    private NoteCliick noteCliick;

    public interface NoteCliick {
        void onNoteClick(int position);
    }

    public void setNoteCliick(NoteCliick noteCliick) {
        this.noteCliick = noteCliick;
    }

    public FavoriteRecAdapter(Context context, List<DataBase> notes) {
        this.context = context;
        this.notes = notes;
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
        Glide.with(context).load(notes.get(position).getWeb())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(holder.imageFavorite);
        holder.userName.setText(notes.get(position).getUser());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class FavoritHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageFavorite;
        private TextView userName;

        public FavoritHolder(@NonNull View itemView) {
            super(itemView);
            imageFavorite = itemView.findViewById(R.id.imageViewFavorite);
            userName = itemView.findViewById(R.id.textViewUserName);
            imageFavorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (noteCliick != null) {
                noteCliick.onNoteClick(getAdapterPosition());
            }
        }
    }

    public void setNotes(List<DataBase> notes1) {
        this.notes = notes1;
        notifyDataSetChanged();
    }

    public List<DataBase> getNotes() {
        return notes;
    }
}
