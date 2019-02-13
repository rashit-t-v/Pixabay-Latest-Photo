package com.rashit.tiugaev.image.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.dataBase.DataBase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ItemCliick itemCliick;
    private OnCheckedChangeListener onCheckedChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    private Context context;
    private List<DataBase> data;

    public interface ItemCliick {
        void onNoteClick(int position, View view);
    }
    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position, CompoundButton button, boolean ischeked);
    }

    public void setItemCliick(ItemCliick itemCliick) {
        this.itemCliick = itemCliick;
    }

    public RecyclerViewAdapter(Context context, List<DataBase> data) {
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
        holder.name.setText("By: " + data.get(position).getUser());
        holder.tags.setText("Tags: " + data.get(position).getTag());
        Glide.with(context).load(data.get(position).getWeb())
                .apply(bitmapTransform(new BlurTransformation(1, 5)))
                .into(holder.imfeFont);
        Glide.with(context).load(data.get(position).getWeb())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(74)))
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private TextView name;
        private TextView tags;
        private ImageView imfeFont;
        private ImageView imagePoster;
        private CheckBox favorite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imfeFont = itemView.findViewById(R.id.imageView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            name = itemView.findViewById(R.id.textViewName);
            tags = itemView.findViewById(R.id.textViewTag);
            favorite = itemView.findViewById(R.id.checkBox);
            imagePoster.setOnClickListener(this);
            favorite.setOnCheckedChangeListener(this);

        }

        @Override
        public void onClick(View v) {
            if (itemCliick != null) {
                itemCliick.onNoteClick(getAdapterPosition(),v);
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(onCheckedChangeListener != null){
                onCheckedChangeListener.onCheckedChanged(getAdapterPosition(),buttonView,isChecked);
            }
        }
    }
}
