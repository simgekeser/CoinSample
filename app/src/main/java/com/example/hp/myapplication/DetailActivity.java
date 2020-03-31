package com.example.hp.myapplication;

import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgDecoder;
import com.ahmadrosid.svgloader.SvgDrawableTranscoder;
import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import org.w3c.dom.Text;

import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView descriptionTv,priceTv,nameTv;
    ImageView iconImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layout);

        descriptionTv=findViewById(R.id.descriptionTv);
        priceTv=findViewById(R.id.priceTv);
        nameTv=findViewById(R.id.nameTv);
        iconImageView=findViewById(R.id.detailIconImageView);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Items item = (Items) bundle.getSerializable("item");
        setTitle(item.getSymbol());
        setIconImageView(item,iconImageView);
        descriptionTv.setText(item.getDescription());

        priceTv.setText(String.format("%.2f", item.getPrice()));
        nameTv.setText(item.getName());
        }

    public void setIconImageView(Items item, ImageView imageView){
        GenericRequestBuilder<Uri,InputStream,SVG,PictureDrawable>
                requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
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
                .into(imageView);
    }

}
