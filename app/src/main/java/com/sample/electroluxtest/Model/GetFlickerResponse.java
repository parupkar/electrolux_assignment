package com.sample.electroluxtest.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFlickerResponse {

    @SerializedName("stat")
    @Expose
    private String stat;

    public String getStat() {
        return stat;
    }

    @SerializedName("photos")
    @Expose
    private Data photos;

    public Data getPhotos() {
        return photos;
    }

    public static class Data {

        @SerializedName("page")
        @Expose
        private Integer page;

        public Integer getPage() {
            return page;
        }

        @SerializedName("pages")
        @Expose
        private Integer pages;

        public Integer getPages() {
            return pages;
        }

        @SerializedName("perpage")
        @Expose
        private Integer perpage;

        public Integer getPerPages() {
            return perpage;
        }

        @SerializedName("total")
        @Expose
        private Integer total;

        public Integer getTotal() {
            return total;
        }


        @SerializedName("photo")
        @Expose
        private List<PhotoDataItem> photo;

        public List<PhotoDataItem> getPhoto() {
            return photo;
        }


        public static class PhotoDataItem {

            @SerializedName("id")
            @Expose
            private Float id;

            public Float getId() {
                return id;
            }

            @SerializedName("title")
            @Expose
            private String title;

            public String getTitle() {
                return title;
            }

            @SerializedName("url_m")
            @Expose
            private String url_m;

            public String getPhotoUrl() {
                return url_m;
            }
        }
    }
}
