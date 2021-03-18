package com.trinity.dynamicforms.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingModel {
    @SerializedName("mappingId")
    @Expose
    private String mappingId;

    @SerializedName("locationId")
    @Expose
    private String locationId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("latlong")
    @Expose
    private String latlong;

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
