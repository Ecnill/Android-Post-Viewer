package com.example.ecnill.postviewer.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by ecnill on 14.3.17.
 */

@ToString(exclude = {"reputation", "profileImageUrl"})
@EqualsAndHashCode(exclude={"reputation", "profileImageUrl"})
public final class Owner {

    @Getter private final long      id;
    @Getter private final int       reputation;
    @Getter private final String    name;
    @Getter private final String    profileImageUrl;

    private Owner(final OwnerBuilder builder) {
        this.id                 = builder.id;
        this.name               = builder.name;
        this.reputation         = builder.reputation;
        this.profileImageUrl    = builder.profileImageUrl;
    }
    

    public final static class OwnerBuilder {

        private long id;
        private String name;
        private String profileImageUrl;
        private int reputation = 0;

        public OwnerBuilder(final long id, final String name, final String profileImageUrl) {
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