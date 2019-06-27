package com.jaykapadia.nytimes.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section {
    @SerializedName("status")
    private String status;

    @SerializedName("copyright")
    private String copyright;

    @SerializedName("section")
    private String section;

    @SerializedName("last_updated")
    private String last_updated;

    @SerializedName("num_results")
    private Integer num_results;

    @SerializedName("results")
    private List<Article> results;

    public Section(String status, String copyright, String section, String last_updated, Integer num_results, List<Article> results) {
        this.status = status;
        this.copyright = copyright;
        this.section = section;
        this.last_updated = last_updated;
        this.num_results = num_results;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public Integer getNum_results() {
        return num_results;
    }

    public void setNum_results(Integer num_results) {
        this.num_results = num_results;
    }

    public List<Article> getResults() {
        return results;
    }

    public void setResults(List<Article> results) {
        this.results = results;
    }
}

