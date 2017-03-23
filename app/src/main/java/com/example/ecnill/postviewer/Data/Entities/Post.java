package com.example.ecnill.postviewer.Data.Entities;

/**
 * Created by ecnill on 14.3.17.
 */

public final class Post {

    private final long      id;
    private final String    title;
    private final Owner     owner;
    private final int       viewCount;
    private final String    htmlDetail;

    private Post (final PostBuilder builder) {
        this.id         = builder.id;
        this.title      = builder.title;
        this.owner      = builder.owner;
        this.htmlDetail = builder.htmlDetail;
        this.viewCount  = builder.viewCount;
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

    public int getViewCount() {
        return viewCount;
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return  "Post: "
                + "id = " + Long.toString(id)
                + ", title = " + title
                + ", viewCount = " + Integer.toString(viewCount)
                + ", ownerNae = " + owner.getName();
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
        return  id == other.id &&
                title.equals(other.title) &&
                htmlDetail.equals(other.htmlDetail) &&
                owner.equals(other.owner);
    }

    @Override
    public int hashCode() {
        final int prime = 71;
        int result = 1;
        result = (int) (prime * result + id);
        result = prime * result + viewCount;
        return result;
    }

    public final static class PostBuilder {

        private long id;
        private String title;
        private Owner owner;
        private int viewCount = 0;
        private String htmlDetail = "";

        public PostBuilder(final long id, final String title, final Owner owner) {
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
