package com.worldsay.pojo;

import java.util.List;

/**
 * Created by LXG on 2015/8/29.
 */
public class MessageList {

    /**
     * country_info : [{"article_id":20,"article_img_url":"http://123.59.15.61/image/US20_cover.jpg","article_resource":"世界说","article_title":"16岁退学的美国\u201c穷二代\u201d如何逆袭","comments":0,"create_at":1.440749824E9,"flag":1,"likes":0},{"article_id":19,"article_img_url":null,"article_resource":"新华网","article_title":"美国人怎么花钱？生活开销有多大？","comments":0,"create_at":1.440749579E9,"flag":0,"likes":0},{"article_id":18,"article_img_url":"http://123.59.15.61/image/US18a_cover.jpg,http://123.59.15.61/image/US18b_cover.jpg","article_resource":"新华网","article_title":"串烧\u2014\u2014美国人的旧爱和新宠","comments":0,"create_at":1.440749492E9,"flag":0,"likes":0},{"article_id":17,"article_img_url":null,"article_resource":"新华网","article_title":"美国加州北部百年来首次出现狼群踪影","comments":0,"create_at":1.44074916E9,"flag":0,"likes":0},{"article_id":16,"article_img_url":"http://123.59.15.61/image/US16_cover.jpg","article_resource":"新华网","article_title":"市场动荡之际美国国债再受青睐","comments":0,"create_at":1.440749059E9,"flag":0,"likes":0},{"article_id":15,"article_img_url":"http://123.59.15.61/image/US15_cover.jpg","article_resource":"新华网","article_title":"租借美国大熊猫美香诞下双胞幼崽","comments":0,"create_at":1.440748944E9,"flag":0,"likes":0},{"article_id":14,"article_img_url":"http://123.59.15.61/image/US14a_cover.jpg,http://123.59.15.61/image/US14b_cover.jpg","article_resource":"新华网","article_title":"新闻人物：美国记者帕克尔和摄影师瓦尔德","comments":0,"create_at":1.440748813E9,"flag":0,"likes":0},{"article_id":13,"article_img_url":null,"article_resource":"新华网","article_title":"美驻日大使陷\u201c邮件门\u201d 内容涉及敏感信息","comments":0,"create_at":1.440748677E9,"flag":0,"likes":0},{"article_id":12,"article_img_url":"http://123.59.15.61/image/US12a_cover.jpg,http://123.59.15.61/image/US12b_cover.jpg","article_resource":"新华网","article_title":"美国枪杀记者事件:枪手或是电视台前雇员","comments":0,"create_at":1.440748523E9,"flag":0,"likes":0},{"article_id":11,"article_img_url":"http://123.59.15.61/image/US11_cover.jpg","article_resource":"世界说","article_title":"美国大选，民调到底几成可信？| 布什竞选周记","comments":0,"create_at":1.440748086E9,"flag":0,"likes":0}]
     */

    private List<CountryInfoEntity> country_info;

    public void setCountry_info(List<CountryInfoEntity> country_info) {
        this.country_info = country_info;
    }

    public List<CountryInfoEntity> getCountry_info() {
        return country_info;
    }

    public static class CountryInfoEntity {
        /**
         * article_id : 20
         * article_img_url : http://123.59.15.61/image/US20_cover.jpg
         * article_resource : 世界说
         * article_title : 16岁退学的美国“穷二代”如何逆袭
         * comments : 0
         * create_at : 1.440749824E9
         * flag : 1
         * likes : 0
         */

        private int article_id;
        private String article_img_url;
        private String article_resource;
        private String article_title;
        private int comments;
        private double create_at;
        private int flag;
        private int likes;
        private int liked;
        
        public int getLiked() {
            return liked;
        }

        public void setLiked(int liked) {
            this.liked = liked;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public void setArticle_img_url(String article_img_url) {
            this.article_img_url = article_img_url;
        }

        public void setArticle_resource(String article_resource) {
            this.article_resource = article_resource;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public void setCreate_at(double create_at) {
            this.create_at = create_at;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getArticle_id() {
            return article_id;
        }

        public String getArticle_img_url() {
            return article_img_url;
        }

        public String getArticle_resource() {
            return article_resource;
        }

        public String getArticle_title() {
            return article_title;
        }

        public int getComments() {
            return comments;
        }

        public double getCreate_at() {
            return create_at;
        }

        public int getFlag() {
            return flag;
        }

        public int getLikes() {
            return likes;
        }
    }
}
