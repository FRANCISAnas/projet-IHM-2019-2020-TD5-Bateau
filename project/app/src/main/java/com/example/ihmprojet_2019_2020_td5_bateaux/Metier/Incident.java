package com.example.ihmprojet_2019_2020_td5_bateaux.Metier;

public class Incident {

    private  int id;
    private String nature;
    private String description;
    private String date;
    private String longitude;
    private String latitude;
    private String android_id;

    public int getId() {
        return id;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDate() {
        return date;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAndroid_id() {
        return android_id;
    }

    /**
     *
     * @param id
     * @param nature
     * @param description
     * @param date
     * @param longitude
     * @param latitude
     * @param android_id
     */
    public Incident(int id, String nature, String description, String date, String longitude, String latitude, String android_id) {
        this.id = id;
        this.nature = nature;
        this.description = description;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.android_id = android_id;
    }

    /**
     ** @param nature
     * @param description
     */
    public Incident(String nature, String description) {
        this.nature = nature;
        this.description = description;
        //
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.id == ((Incident)obj).id) {
            return true;
        }
        return false;
    }
}
