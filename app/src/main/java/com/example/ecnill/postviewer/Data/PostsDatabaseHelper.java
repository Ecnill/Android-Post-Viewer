package com.example.ecnill.postviewer.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ecnill.postviewer.Data.Entities.Owner;
import com.example.ecnill.postviewer.Data.Entities.Post;
import com.example.ecnill.postviewer.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecnill on 20.3.17.
 */

public final class PostsDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = PostsDatabaseHelper.class.getSimpleName();

    private static final int DATABASE_VERSION       = 1;
    private static final String DATABASE_NAME       = "db_posts_users";
    private static final String CREATED_AT          = "created_at";

    private static final String TABLE_POST          = "posts";
    private static final String POST_ID             = "post_id";
    private static final String POST_TITLE          = "post_title";
    private static final String POST_HTML           = "post_html";
    private static final String POST_OWNER_ID       = "post_owner_id";
    private static final String POST_VIEW_COUNT     = "post_view_count";

    private static final String TABLE_OWNER         = "owner";
    private static final String OWNER_ID            = "owner_id";
    private static final String OWNER_NAME          = "owner_name";
    private static final String OWNER_IMAGE_URL     = "owner_image_url";
    private static final String OWNER_REPUTATION    = "owner_reputation";

    private static final String CREATE_TABLE_POST   = "CREATE TABLE "
            + TABLE_POST        + "("
            + POST_ID           + " INTEGER PRIMARY KEY,"
            + POST_TITLE        + " TEXT,"
            + POST_HTML         + " TEXT,"
            + POST_OWNER_ID     + " INTEGER,"
            + POST_VIEW_COUNT   + " INTEGER, "
            + CREATED_AT        + " DATETIME" + ")";

    private static final String CREATE_TABLE_OWNER = "CREATE TABLE "
            + TABLE_OWNER       + "("
            + OWNER_ID          + " INTEGER PRIMARY KEY,"
            + OWNER_NAME        + " TEXT, "
            + OWNER_IMAGE_URL   + " TEXT, "
            + OWNER_REPUTATION  + " INTEGER,"
            + CREATED_AT        + " DATETIME" + ")";

    public PostsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_POST);
        db.execSQL(CREATE_TABLE_OWNER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWNER);
        onCreate(db);
    }

    public long createPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POST_ID, post.getId());
        values.put(POST_TITLE, post.getTitle());
        values.put(POST_HTML, post.getHtmlDetail());
        values.put(POST_OWNER_ID, post.getOwner().getId());
        values.put(POST_VIEW_COUNT, post.getViewCount());
        values.put(CREATED_AT, StringUtils.getActualDateTime());
        return db.insert(TABLE_POST, null, values);
    }

    public long createOwner(Owner owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OWNER_ID, owner.getId());
        values.put(OWNER_NAME, owner.getName());
        values.put(OWNER_IMAGE_URL, owner.getProfileImageUrl());
        values.put(OWNER_REPUTATION, owner.getReputation());
        values.put(CREATED_AT, StringUtils.getActualDateTime());
        return db.insert(TABLE_OWNER, null, values);
    }

    public int getPostCount() {
        String countQuery = "SELECT  * FROM " + TABLE_POST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String queryPosts = "SELECT  * FROM " + TABLE_POST;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorPosts = db.rawQuery(queryPosts, null);

        if (cursorPosts.moveToFirst()) {
            do {
                long postId = cursorPosts.getColumnIndex(POST_ID);
                String postTitle = cursorPosts.getString(cursorPosts.getColumnIndex(POST_TITLE));
                long postOwnerId = cursorPosts.getLong(cursorPosts.getColumnIndex(POST_OWNER_ID));

                String queryOwnerOfPost = getOwnerQuery(postOwnerId);

                Cursor cursorOwner = db.rawQuery(queryOwnerOfPost, null);
                if (cursorOwner.moveToFirst()) {
                    Post p = new Post.PostBuilder(
                            postId,
                            postTitle,
                            new Owner.OwnerBuilder(
                                    postOwnerId,
                                    cursorOwner.getString(cursorOwner.getColumnIndex(OWNER_NAME)),
                                    cursorOwner.getString(cursorOwner.getColumnIndex(OWNER_IMAGE_URL)))
                                    .setReputation(cursorOwner.getInt(cursorOwner.getColumnIndex(OWNER_REPUTATION)))
                                    .build())
                            .setHtmlDetail(cursorPosts.getString(cursorPosts.getColumnIndex(POST_HTML)))
                            .setViewCount(cursorPosts.getInt(cursorPosts.getColumnIndex(POST_VIEW_COUNT)))
                            .build();
                    cursorOwner.close();
                    posts.add(p);
                }
            } while (cursorPosts.moveToNext());
            cursorPosts.close();
        }
        return posts;
    }

    private String getOwnerQuery(long ownerId) {
        return "SELECT * FROM " + TABLE_OWNER
                + " WHERE " + OWNER_ID + " = \'"
                + Long.toString(ownerId) + "\'";
    }

}
