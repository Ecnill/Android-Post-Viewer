package com.example.ecnill.postviewer.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by ecnill on 14.3.17.
 */

@ToString(exclude = {"viewCount", "htmlDetail"})
@EqualsAndHashCode(exclude={"viewCount", "htmlDetail"})
public final class Post {

    @Getter private final long      id;
    @Getter private final String    title;
    @Getter private final Owner     owner;
    @Getter private final int       viewCount;
    @Getter private final String    htmlDetail;

    private Post(final PostBuilder builder) {
        this.id         = builder.id;
        this.title      = builder.title;
        this.owner      = builder.owner;
        this.htmlDetail = builder.htmlDetail;
        this.viewCount  = builder.viewCount;
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