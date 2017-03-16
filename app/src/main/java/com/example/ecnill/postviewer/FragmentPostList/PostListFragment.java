package com.example.ecnill.postviewer.FragmentPostList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecnill.postviewer.Adapters.PostAdapter;
import com.example.ecnill.postviewer.Entities.Post;
import com.example.ecnill.postviewer.R;
import com.example.ecnill.postviewer.Utils.FragmentChangeListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import static com.kaopiz.kprogresshud.KProgressHUD.create;

/**
 * Created by ecnill on 14.3.17.
 */

public class PostListFragment extends Fragment implements PostListView,
        PostAdapter.OnPostClickListener {

    private static final String TAG = PostListFragment.class.getSimpleName();

    private PostListPresenter<Post> mPresenter;

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private KProgressHUD mProgressHUD;

    private boolean mInitView = true;

    public PostListFragment() {
        Log.i(TAG, "constructor.");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // if there is internet connection
        mPresenter = new PostListPresenterImpl(this, new InternetProvider());
        // todo: else if db
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_posts);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (mProgressHUD == null) {
            createProgressIdentifier(activity);
        }
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PostAdapter(mPresenter.getAllData(), this);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               mRecyclerView.setAdapter(mAdapter);
            }
        });

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
                if ((mAdapter.getItemActualPos() + 1)  == layoutManager.getItemCount()) {
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
    public void showMessage(int resourceId) {
        String message = getActivity().getResources().getString(resourceId);
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
        mPresenter.itemClick(pos, getResources().getBoolean(R.bool.two_pane_mode));
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

    private void createProgressIdentifier(Activity activity){
        mProgressHUD = create(activity);
        mProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getResources().getString(R.string.load_message))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

}
