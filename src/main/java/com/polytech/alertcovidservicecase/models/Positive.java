package com.polytech.alertcovidservicecase.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name="positif")
@Access(AccessType.FIELD)
public class Positive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_user;
    private Date date;

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
