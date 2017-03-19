package com.example.ecnill.postviewer.Entities;

/**
 * Created by ecnill on 14.3.17.
 */

public final class Owner {

    private final long id;
    private final int reputation;
    private final String name;
    private final String profileImageUrl;

    private Owner(OwnerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.reputation = builder.reputation;
        this.profileImageUrl = builder.profileImageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @Override
    public String toString() {
        return this.id + " " + this.reputation + " " + this.name + " " + this.profileImageUrl;
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


    public static class OwnerBuilder {

        private long id;
        private String name;
        private String profileImageUrl;
        private int reputation = 0;

        public OwnerBuilder(long id, String name, String profileImageUrl) {
            this.id = id;
            this.name = name;
            this.profileImageUrl = profileImageUrl;
        }

        public OwnerBuilder setReputation(int reputation) {
            this.reputation = reputation;
            return this;
        }

        public Owner build() {
            return new Owner(this);
        }
    }

}
