package com.rashit.tiugaev.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {
    @SerializedName("totalHits")
    @Expose
    private Integer totalHits;
    @SerializedName("hits")
    @Expose
    private List<Hit> hits = null;
    @SerializedName("total")
    @Expose
    private Integer total;


    public Post() {
    }

    public Post(Integer totalHits, List<Hit> hits, Integer total) {
        super();
        this.totalHits = totalHits;
        this.hits = hits;
        this.total = total;
    }

    public Integer getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    public Post withTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
        return this;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    public Post withHits(List<Hit> hits) {
        this.hits = hits;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Post withTotal(Integer total) {
        this.total = total;
        return this;
    }
}
