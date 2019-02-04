package com.rashit.tiugaev.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hit {
    @SerializedName("largeImageURL")
    @Expose
    private String largeImageURL;
    @SerializedName("webformatHeight")
    @Expose
    private Integer webformatHeight;
    @SerializedName("webformatWidth")
    @Expose
    private Integer webformatWidth;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("imageWidth")
    @Expose
    private Integer imageWidth;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("pageURL")
    @Expose
    private String pageURL;
    @SerializedName("imageHeight")
    @Expose
    private Integer imageHeight;
    @SerializedName("webformatURL")
    @Expose
    private String webformatURL;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("previewHeight")
    @Expose
    private Integer previewHeight;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("downloads")
    @Expose
    private Integer downloads;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("favorites")
    @Expose
    private Integer favorites;
    @SerializedName("imageSize")
    @Expose
    private Integer imageSize;
    @SerializedName("previewWidth")
    @Expose
    private Integer previewWidth;
    @SerializedName("userImageURL")
    @Expose
    private String userImageURL;
    @SerializedName("previewURL")
    @Expose
    private String previewURL;

    public Hit() {
    }

    public Hit(String largeImageURL, Integer webformatHeight, Integer webformatWidth, Integer likes, Integer imageWidth, Integer id, Integer userId, Integer views, Integer comments, String pageURL, Integer imageHeight, String webformatURL, String type, Integer previewHeight, String tags, Integer downloads, String user, Integer favorites, Integer imageSize, Integer previewWidth, String userImageURL, String previewURL) {
        super();
        this.largeImageURL = largeImageURL;
        this.webformatHeight = webformatHeight;
        this.webformatWidth = webformatWidth;
        this.likes = likes;
        this.imageWidth = imageWidth;
        this.id = id;
        this.userId = userId;
        this.views = views;
        this.comments = comments;
        this.pageURL = pageURL;
        this.imageHeight = imageHeight;
        this.webformatURL = webformatURL;
        this.type = type;
        this.previewHeight = previewHeight;
        this.tags = tags;
        this.downloads = downloads;
        this.user = user;
        this.favorites = favorites;
        this.imageSize = imageSize;
        this.previewWidth = previewWidth;
        this.userImageURL = userImageURL;
        this.previewURL = previewURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public Hit withLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
        return this;
    }

    public Integer getWebformatHeight() {
        return webformatHeight;
    }

    public void setWebformatHeight(Integer webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    public Hit withWebformatHeight(Integer webformatHeight) {
        this.webformatHeight = webformatHeight;
        return this;
    }

    public Integer getWebformatWidth() {
        return webformatWidth;
    }

    public void setWebformatWidth(Integer webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    public Hit withWebformatWidth(Integer webformatWidth) {
        this.webformatWidth = webformatWidth;
        return this;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Hit withLikes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Hit withImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Hit withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Hit withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Hit withViews(Integer views) {
        this.views = views;
        return this;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Hit withComments(Integer comments) {
        this.comments = comments;
        return this;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public Hit withPageURL(String pageURL) {
        this.pageURL = pageURL;
        return this;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Hit withImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
        return this;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public Hit withWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hit withType(String type) {
        this.type = type;
        return this;
    }

    public Integer getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(Integer previewHeight) {
        this.previewHeight = previewHeight;
    }

    public Hit withPreviewHeight(Integer previewHeight) {
        this.previewHeight = previewHeight;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Hit withTags(String tags) {
        this.tags = tags;
        return this;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Hit withDownloads(Integer downloads) {
        this.downloads = downloads;
        return this;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Hit withUser(String user) {
        this.user = user;
        return this;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public Hit withFavorites(Integer favorites) {
        this.favorites = favorites;
        return this;
    }

    public Integer getImageSize() {
        return imageSize;
    }

    public void setImageSize(Integer imageSize) {
        this.imageSize = imageSize;
    }

    public Hit withImageSize(Integer imageSize) {
        this.imageSize = imageSize;
        return this;
    }

    public Integer getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(Integer previewWidth) {
        this.previewWidth = previewWidth;
    }

    public Hit withPreviewWidth(Integer previewWidth) {
        this.previewWidth = previewWidth;
        return this;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public Hit withUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
        return this;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public Hit withPreviewURL(String previewURL) {
        this.previewURL = previewURL;
        return this;
    }
}
