package com.example.ecnill.postviewer.Entities;

/**
 * Created by ecnill on 14.3.17.
 */

public class Post {

    private final long id;
    private final String title;
    private final Owner owner;
    private final int viewCount;
    private final String htmlDetail;

    private Post (PostBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.owner = builder.owner;
        this.htmlDetail = builder.htmlDetail;
        this.viewCount = builder.viewCount;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHtmlDetail() {
        return htmlDetail;
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return this.id + " " + this.title + " " + this.owner.getId() + " " + this.owner.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Post other = (Post) obj;
        if (id != other.id) {
            return false;
        }
        if (!title.equals(other.title)) {
            return false;
        }
        if (!htmlDetail.equals(other.htmlDetail)) {
            return false;
        }
        if (!owner.equals(other.owner)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 71;
        int result = 1;
        result = (int) (prime * result + id);
        result = prime * result + viewCount;
        return result;
    }

    public static class PostBuilder {

        private long id;
        private String title;
        private Owner owner;
        private int viewCount = 0;
        private String htmlDetail = "";

        public PostBuilder(long id, String title, Owner owner) {
            this.id = id;
            this.title = title;
            this.owner = owner;
        }

        public PostBuilder setViewCount(int viewCount) {
            this.viewCount = viewCount;
            return this;
        }

        public PostBuilder setHtmlDetail(String htmlDetail) {
            this.htmlDetail = htmlDetail;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }

}
