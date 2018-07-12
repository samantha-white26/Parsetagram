package com.example.samanthawhite.parsetagram;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.samanthawhite.parsetagram.model.Post;

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
        Glide.with(context).load(post.getImage().getUrl()).into(holder.imageView);

    }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // the views I want to display
    public ImageView imageView;
    public TextView description;

    // constructor takes in inflated layout
    public ViewHolder(View itemView) {
      super(itemView);
      // perform findbiewby id lookups
      imageView = (ImageView) itemView.findViewById(R.id.imageView);
      description = (TextView) itemView.findViewById(R.id.tvDescription);
    }

      @Override
      public void onClick(View view) {
          HomeActivity home = (HomeActivity) context;

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
