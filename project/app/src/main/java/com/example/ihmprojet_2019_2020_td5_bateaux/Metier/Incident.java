package com.example.ihmprojet_2019_2020_td5_bateaux.Metier;

public class Incident {

    private final int id;
    private String nature;
    private String description;

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



    public Incident(int id, String nature, String description) {
        this.id = id;
        this.nature = nature;
        this.description = description;
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
