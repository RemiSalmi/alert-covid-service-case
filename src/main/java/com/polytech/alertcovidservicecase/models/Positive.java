package com.polytech.alertcovidservicecase.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name="positif")
@Access(AccessType.FIELD)
@IdClass(PositiveId.class)
public class Positive {

    @Id
    private long id_user;

    @Id
    private Timestamp date;

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Java object to JSON string
        return mapper.writeValueAsString(this);
    }
}
