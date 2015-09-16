package com.worldsay.pojo;


import java.util.List;

/**
 * Created by LXG on 2015/8/29.
 */
public class CommentList {
    private List<CommentEntity> comments;

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public static class CommentEntity {
        private int comment_id;
        private String comment_user_thumb_url;
        private String comment_headimg_url;
        private String comment_uid;
        private String comment_username;
        private double create_at;
        private String comment_content;
        private int reply_id;
        private String reply_username;
        private int likes;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getComment_user_thumb_url() {
            return comment_user_thumb_url;
        }

        public void setComment_user_thumb_url(String comment_user_thumb_url) {
            this.comment_user_thumb_url = comment_user_thumb_url;
        }

        public String getComment_headimg_url() {
            return comment_headimg_url;
        }

        public void setComment_headimg_url(String comment_headimg_url) {
            this.comment_headimg_url = comment_headimg_url;
        }

        public String getComment_uid() {
            return comment_uid;
        }

        public void setComment_uid(String comment_uid) {
            this.comment_uid = comment_uid;
        }

        public String getComment_username() {
            return comment_username;
        }

        public void setComment_username(String comment_username) {
            this.comment_username = comment_username;
        }

        public double getCreate_at() {
            return create_at;
        }

        public void setCreate_at(double create_at) {
            this.create_at = create_at;
        }

        public String getComment_content() {
            return comment_content;
        }

        public void setComment_content(String comment_content) {
            this.comment_content = comment_content;
        }

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public String getReply_username() {
            return reply_username;
        }

        public void setReply_username(String reply_username) {
            this.reply_username = reply_username;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }
    }
}
