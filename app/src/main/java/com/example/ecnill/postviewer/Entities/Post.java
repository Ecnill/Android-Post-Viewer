package com.example.ecnill.postviewer.Entities;

/**
 * Created by ecnill on 14.3.17.
 */

public class Post {

    private long id;
    private int viewCount;
    private String title;
    private String htmlDetail;
    private Owner owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlDetail() {
        return htmlDetail;
    }

    public void setHtmlDetail(String htmlDetail) {
        this.htmlDetail = htmlDetail;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
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

}

