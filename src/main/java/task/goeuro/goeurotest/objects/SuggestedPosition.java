/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.goeuro.goeurotest.objects;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author developer
 */
public class SuggestedPosition {

    @SerializedName("_id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("geo_position")
    private GeographicalPosition geographicalPosition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeographicalPosition getGeographicalPosition() {
        return geographicalPosition;
    }

    public void setGeographicalPosition(GeographicalPosition geographicalPosition) {
        this.geographicalPosition = geographicalPosition;
    }

}
