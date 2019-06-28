package com.jaykapadia.nytimes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

    @SerializedName("section")
    private String section;

    @SerializedName("subsection")
    private String subsection;

    @SerializedName("title")
    private String title;

    @SerializedName("abstract")
    private String abstrac;

    @SerializedName("url")
    private String url;

    @SerializedName("byline")
    private String byline;

    @SerializedName("item_type")
    private String item_type;

    @SerializedName("updated_date")
    private String updated_date;

    @SerializedName("created_date")
    private String created_date;

    @SerializedName("published_date")
    private String published_date;

    @SerializedName("material_type_facet")
    private String material_type_facet;

    @SerializedName("kicker")
    private String kicker;

    @SerializedName("des_facet")
    private List<String> des_facet;

    @SerializedName("org_facet")
    private List<String> org_facet;

    @SerializedName("per_facet")
    private List<String> per_facet;

    @SerializedName("geo_facet")
    private List<String> geo_facet;

    @SerializedName("multimedia")
    private List<Multimedia> multimedia;

    @SerializedName("short_url")
    private String short_url;

    public Article(String section, String subsection, String title, String abstrac, String url, String byline, String item_type, String updated_date, String created_date, String published_date, String material_type_facet, String kicker, List<String> des_facet, List<String> org_facet, List<String> per_facet, List<String> geo_facet, List<Multimedia> multimedia, String short_url) {
        this.section = section;
        this.subsection = subsection;
        this.title = title;
        this.abstrac = abstrac;
        this.url = url;
        this.byline = byline;
        this.item_type = item_type;
        this.updated_date = updated_date;
        this.created_date = created_date;
        this.published_date = published_date;
        this.material_type_facet = material_type_facet;
        this.kicker = kicker;
        this.des_facet = des_facet;
        this.org_facet = org_facet;
        this.per_facet = per_facet;
        this.geo_facet = geo_facet;
        this.multimedia = multimedia;
        this.short_url = short_url;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstrac() {
        return abstrac;
    }

    public void setAbstrac(String abstrac) {
        this.abstrac = abstrac;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getMaterial_type_facet() {
        return material_type_facet;
    }

    public void setMaterial_type_facet(String material_type_facet) {
        this.material_type_facet = material_type_facet;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    public List<String> getDes_facet() {
        return des_facet;
    }

    public void setDes_facet(List<String> des_facet) {
        this.des_facet = des_facet;
    }

    public List<String> getOrg_facet() {
        return org_facet;
    }

    public void setOrg_facet(List<String> org_facet) {
        this.org_facet = org_facet;
    }

    public List<String> getPer_facet() {
        return per_facet;
    }

    public void setPer_facet(List<String> per_facet) {
        this.per_facet = per_facet;
    }

    public List<String> getGeo_facet() {
        return geo_facet;
    }

    public void setGeo_facet(List<String> geo_facet) {
        this.geo_facet = geo_facet;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }
}
