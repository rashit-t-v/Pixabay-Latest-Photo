package com.rashit.tiugaev.image.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.rashit.tiugaev.image.Hit;
import com.rashit.tiugaev.image.R;
import com.rashit.tiugaev.image.dataBase.DataBase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ItemCliick itemCliick;
//    private OnCheckedChangeListener onCheckedChangeListener;
//
//    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
//        this.onCheckedChangeListener = onCheckedChangeListener;
//    }

    private Context context;
    private List<Hit> data;

    public interface ItemCliick {
        void onNoteClick(int position, View view);
    }
//    public interface OnCheckedChangeListener {
//        void onCheckedChanged(int position, CompoundButton button, boolean ischeked);
//    }

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
        holder.name.setText("By: " + data.get(position).getUser());
        holder.tags.setText("Tags: " + data.get(position).getTags());
        Glide.with(context).load(data.get(position).getWebformatURL())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(74)))
                .into(holder.imagePoster);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        private TextView tags;
        private ImageView imagePoster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            name = itemView.findViewById(R.id.textViewName);
            tags = itemView.findViewById(R.id.textViewTag);
            imagePoster.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (itemCliick != null) {
                itemCliick.onNoteClick(getAdapterPosition(),v);
            }
        }

//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if(onCheckedChangeListener != null){
//                onCheckedChangeListener.onCheckedChanged(getAdapterPosition(),buttonView,isChecked);
//            }
//        }
    }
}
