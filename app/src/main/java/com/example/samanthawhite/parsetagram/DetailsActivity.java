package com.example.samanthawhite.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.samanthawhite.parsetagram.model.Post;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
    @BindView(R.id.ivPicture) ImageView ivPicture;

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);
        post = Parcels.unwrap(getIntent().getParcelableExtra("postDetails"));
        tvDescription.setText(post.getDescription());
        tvTimeStamp.setText(post.getRelativeTimeAgo());

        Glide.with(DetailsActivity.this).load(post.getImage().getUrl()).into(ivPicture);


    }
}
