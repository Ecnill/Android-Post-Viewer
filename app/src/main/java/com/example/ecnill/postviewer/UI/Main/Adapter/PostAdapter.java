package com.example.ecnill.postviewer.UI.Main.Adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecnill.postviewer.App;
import com.example.ecnill.postviewer.Data.Entities.Post;
import com.example.ecnill.postviewer.R;
import com.example.ecnill.postviewer.Utils.StringUtils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ecnill on 14.3.17.
 */

public final class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public interface OnPostClickListener {
        void onItemClick(int pos);
    }

    private static final String TAG = PostAdapter.class.getSimpleName();

    private final OnPostClickListener mListener;
    private final List<Post> mPostsList;

    private int mActualItemPos;

    public PostAdapter(final List<Post> posts, final OnPostClickListener listener) {
        this.mPostsList = posts;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mActualItemPos = holder.getAdapterPosition();
        Post post = mPostsList.get(position);

        holder.txtPostTitle.setText(post.getTitle());
        holder.txtPostUserName.setText(post.getOwner().getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mListener.onItemClick(holder.getAdapterPosition());
            }
        });

        Picasso p = Picasso.with(holder.itemView.getContext());
        if (((App) holder.itemView.getContext().getApplicationContext()).isNetworkAvailable()) {
            p.load(post.getOwner().getProfileImageUrl())
                    .error(ContextCompat.getDrawable(holder.itemView.getContext(), R.mipmap.ic_launcher))
                    .into(holder.imgPostImage);
        } else {
            p.load(post.getOwner().getProfileImageUrl())
                    .error(ContextCompat.getDrawable(holder.itemView.getContext(), R.mipmap.ic_launcher))
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.imgPostImage);
        }
    }

    @Override
    public int getItemCount() {
        return mPostsList.size();
    }

    public int getItemActualPos() {
        return mActualItemPos;
    }

    final static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView txtPostTitle;
        final TextView txtPostUserName;
        final ImageView imgPostImage;

        ViewHolder(View itemView) {
            super(itemView);
            txtPostTitle = (TextView) itemView.findViewById(R.id.txt_post_title);
            txtPostUserName = (TextView) itemView.findViewById(R.id.txt_post_user_name);
            imgPostImage = (ImageView) itemView.findViewById(R.id.img_post_image);
        }

    }

}
