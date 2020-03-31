package com.example.hp.myapplication;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgDecoder;
import com.ahmadrosid.svgloader.SvgDrawableTranscoder;
import com.ahmadrosid.svgloader.SvgLoader;
import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> {

    private Context context;
    private List<Items> items;

    public RecyclerViewCustomAdapter(Context context, List items) {
        this.context = context;
        this.items = items;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(items.get(position));

        Items item = items.get(position);
            int color;

            if(item.getColor()==null){
                color = Color.parseColor("#E53935");
            }else {
                try {
                    color = Color.parseColor(item.getColor());
                } catch (IllegalArgumentException e) {
                    color = Color.parseColor("#E53935");
                }
            }
        holder.symbolNameTv.setText(item.getSymbol());
        holder.symbolNameTv.setTextColor(color);
        setIconImageView(item,holder);
        holder.priceTv.setText(String.format("%10.2f", item.getPrice()));
        holder.priceTv.setTextColor(color);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView symbolNameTv;
        public ImageView iconImageView;
        public TextView priceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            symbolNameTv = (TextView) itemView.findViewById(R.id.symbolNameTextView);
            iconImageView =(ImageView)itemView.findViewById(R.id.iconImageView);
            priceTv=(TextView)itemView.findViewById(R.id.priceTextView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", (Serializable) items.get(getPosition()));
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
    public void setIconImageView(Items item,ViewHolder holder){
        GenericRequestBuilder<Uri,InputStream,SVG,PictureDrawable>
                requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>());

        Uri uri = Uri.parse(item.getIconUrl());
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(holder.iconImageView);
    }
}