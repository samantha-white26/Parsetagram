package com.example.samanthawhite.parsetagram;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LogoutFragment extends Fragment {

    @BindView(R.id.logout_btn) Button logoutButton;

    private OnLogoutSelectedListener listener;

    public interface OnLogoutSelectedListener {
        // This can be any number of events to be sent to the activity
        public void onMainItemSelected();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.logout_btn)
    protected void logoutButton(){
        listener.onMainItemSelected();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogoutSelectedListener) {
            listener = (OnLogoutSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement LogoutFragment.OnLogoutSelectedListener");
        }
    }

}
