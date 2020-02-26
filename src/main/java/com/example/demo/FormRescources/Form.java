package com.example.demo.FormRescources;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Form {

    private long id;
    private Date creationDate;
    private String userCreated;
    private String host;
    private String context;
    private String ordning;
    private String server;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date datoPilo;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date datoProd;

    protected Form() {

    }

    public Form(long id, Date creationDate, String userCreated, String host, String context, String ordning, String server, Date datoPilo, Date datoProd) {
        this.id = id;
        this.creationDate = creationDate;
        this.userCreated = userCreated;
        this.host = host;
        this.context = context;
        this.ordning = ordning;
        this.server = server;
        this.datoPilo = datoPilo;
        this.datoProd = datoProd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getOrdning() {
        return ordning;
    }

    public void setOrdning(String ordning) {
        this.ordning = ordning;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Date getDatoPilo() {
        return datoPilo;
    }

    public void setDatoPilo(Date datoPilo) {
        this.datoPilo = datoPilo;
    }

    public Date getDatoProd() {
        return datoProd;
    }

    public void setDatoProd(Date datoProd) {
        this.datoProd = datoProd;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Form)) return false;
        Form form = (Form) o;
        return getId() == form.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", userCreated='" + userCreated + '\'' +
                ", host='" + host + '\'' +
                ", context='" + context + '\'' +
                ", ordning='" + ordning + '\'' +
                ", server='" + server + '\'' +
                ", datoPilo=" + datoPilo +
                ", datoProd=" + datoProd +
                '}';
    }
}
