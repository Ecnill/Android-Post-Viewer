package com.example.ecnill.postviewer.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecnill.postviewer.App;
import com.example.ecnill.postviewer.data.InternetProvider;
import com.example.ecnill.postviewer.data.LocalProvider;
import com.example.ecnill.postviewer.data.PostsDatabaseHelper;
import com.example.ecnill.postviewer.network.NetworkService;
import com.example.ecnill.postviewer.R;
import com.example.ecnill.postviewer.ui.FragmentChangeListener;
import com.example.ecnill.postviewer.utils.ProgressHudUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ecnill on 14.3.17.
 */

public final class PostListFragment extends Fragment implements Question.View,
        PostAdapter.OnPostClickListener {

    private static final String TAG = PostListFragment.class.getSimpleName();

    private boolean mInitView = true;

    @BindView(R.id.recycler_view_posts) RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private KProgressHUD mProgressHUD;

    private PostListPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        PostsDatabaseHelper databaseHelper = new PostsDatabaseHelper(getActivity());
        if (((App) getContext().getApplicationContext()).isNetworkAvailable()) {
            NetworkService networkService = ((App) getContext().getApplicationContext()).getNetworkService();
            mPresenter = new PostListPresenter(this, new InternetProvider(networkService), databaseHelper);
        } else  {
            mPresenter = new PostListPresenter(this, new LocalProvider(databaseHelper), databaseHelper);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        if (mProgressHUD == null) {
            mProgressHUD = ProgressHudUtils.createProgressIdentifier(getActivity());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PostAdapter(mPresenter.getAllData(), this);
        activity.runOnUiThread(() -> mRecyclerView.setAdapter(mAdapter));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // I don't know why onScrolled gets init call without any touch
                if (mInitView) {
                    mInitView = false;
                    return;
                }
                // if last visible item has same position as number of all items, then load more
                if ((mAdapter.getItemActualPosition() + 1)  == layoutManager.getItemCount()) {
                    loadMore();
                }
            }
        });
    }

    @Override
    public void showProgress() {
        mProgressHUD.show();
    }

    @Override
    public void hideProgress() {
        mProgressHUD.dismiss();
    }

    @Override
    public void showFinishLoadMessage() {
        String message = getActivity().getResources().getString(R.string.all_data_loaded_message);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyDataSetChanged(final int pos) {
        mAdapter.notifyItemInserted(pos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onItemClick(int pos) {
        mPresenter.itemClick(pos);
    }

    @Override
    public  void showNextFragment(Fragment fragment) {
        FragmentChangeListener fc = (FragmentChangeListener) getContext();
        if (!getResources().getBoolean(R.bool.two_pane_mode)) {
            fc.replaceFragment(R.id.fragment_container, fragment, true);
        } else {
            fc.replaceFragment(R.id.detail_fragment, fragment, true);
        }
    }

}
