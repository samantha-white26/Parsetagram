package com.example.samanthawhite.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.samanthawhite.parsetagram.model.Post;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public List<Post> mPosts;
    Context context;


    public PostsAdapter (List<Post> posts) {
        mPosts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout that we created
        //get context
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        //inflate the tweet row
        View tweetView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //which tweet object to show based on position
         Post post = mPosts.get(position);



        holder.description.setText(post.getDescription());
        holder.username.setText(post.getUser().getUsername());
        Glide.with(context).load(post.getImage().getUrl()).into(holder.imageView);
        holder.postUsername.setText(post.getUser().getUsername());
        holder.timeStamp.setText(post.getRelativeTimeAgo());


    }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // the views I want to display
    public ImageView imageView;
    public TextView description;
    public TextView username;
    public TextView postUsername;
    public TextView timeStamp;




    // constructor takes in inflated layout
    public ViewHolder(View itemView) {
      super(itemView);
      // perform findbiewby id lookups
      imageView = (ImageView) itemView.findViewById(R.id.imageView);
      description = (TextView) itemView.findViewById(R.id.tvDescription);
      username = (TextView) itemView.findViewById(R.id.username);
      postUsername = (TextView) itemView.findViewById(R.id.postusername);
      timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);



      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

      MainActivity main = (MainActivity) context;

      int position = getAdapterPosition();
      Post post = mPosts.get(position);

      if (position != RecyclerView.NO_POSITION) {
        // create an intent for an activity
        Intent intent = new Intent(main, DetailsActivity.class);
        // set up so the information we want to pass will be passed to the new activtiy
        intent.putExtra("postDetails", Parcels.wrap(post));

        main.startActivity(intent);
      }
    }
  }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }





}
