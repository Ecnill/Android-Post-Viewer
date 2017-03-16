package com.example.ecnill.postviewer.Entities;

/**
 * Created by ecnill on 14.3.17.
 */

public class Owner {

    private long id;
    private int reputation;
    private String name;
    private String profileImageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
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
        Owner other = (Owner) obj;
        if (id != other.id) {
            return false;
        }
        if (reputation != other.reputation) {
            return false;
        }
        if (!name.equals(other.name)) {
            return false;
        }
        if (!profileImageUrl.equals(other.profileImageUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 53;
        int result = 1;
        result = (int) (prime * result + id);
        result = prime * result + reputation;
        return result;
    }


}
