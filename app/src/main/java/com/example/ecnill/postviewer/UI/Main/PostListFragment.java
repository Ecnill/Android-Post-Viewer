package com.example.ecnill.postviewer.UI.Main;

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
import com.example.ecnill.postviewer.Data.LocalProvider;
import com.example.ecnill.postviewer.Data.PostsDatabaseHelper;
import com.example.ecnill.postviewer.Data.InternetProvider;
import com.example.ecnill.postviewer.UI.Main.Adapter.PostAdapter;
import com.example.ecnill.postviewer.Data.Entities.Post;
import com.example.ecnill.postviewer.R;
import com.example.ecnill.postviewer.UI.Main.Presenter.PostListPresenter;
import com.example.ecnill.postviewer.UI.Main.Presenter.PostListPresenterImpl;
import com.example.ecnill.postviewer.UI.FragmentChangeListener;
import com.example.ecnill.postviewer.Utils.ProgressHudUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by ecnill on 14.3.17.
 */

public final class PostListFragment extends Fragment implements PostListView,
        PostAdapter.OnPostClickListener {

    private static final String TAG = PostListFragment.class.getSimpleName();

    private boolean mInitView = true;

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private KProgressHUD mProgressHUD;

    private PostListPresenter<Post> mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        PostsDatabaseHelper databaseHelper = new PostsDatabaseHelper(getActivity());

        if (((App) getContext().getApplicationContext()).isNetworkAvailable()) {
            mPresenter = new PostListPresenterImpl(this, new InternetProvider(), databaseHelper);
        } else  {
            mPresenter = new PostListPresenterImpl(this, new LocalProvider(databaseHelper), databaseHelper);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_posts);
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
