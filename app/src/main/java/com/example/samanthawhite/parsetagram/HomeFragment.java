package com.example.samanthawhite.parsetagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samanthawhite.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    ArrayList<Post> posts;
    PostsAdapter postsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //do view operations
        //instead of findviewbyid view.findviewid
        RecyclerView rvPosts = view.findViewById(R.id.RecyclerView);

        posts = new ArrayList<>();

        postsAdapter = new PostsAdapter(posts);

        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        //set the adapter
        rvPosts.setAdapter(postsAdapter);

        //TODO get posts call some function
        loadTopPosts();


        //set layout manager
        //set adapter









        return view;
    }



    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        // modifying the query to make it more specific
        postsQuery.getTop().withUser();

        // make a query to make sure we can get the posts without users inflated
        postsQuery.findInBackground(
                new FindCallback<Post>() {
                    @Override
                    // this will find all of the posts in background thread
                    public void done(List<Post> objects, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < objects.size(); i++) {
                                Log.d(
                                        "HomeFragment",
                                        "Post["
                                                + i
                                                + "] = "
                                                + objects.get(i).getDescription()
                                                + "\nusername = "
                                                + objects.get(i).getUser().getUsername());
                                posts.add(objects.get(i));
                                postsAdapter.notifyItemInserted(posts.size()-1);


                            }
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
