package com.opendatatuscany.api.Restaurant;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    private long id;
    private String nome;
    private double lat;
    private double lon;
    private String tipologia;
    private String indirizzo;
    private int cap;
    private String comune;
    private String provincia;
    private String email;
    private String telefono;
    private String fax;
    
    public Restaurant() {
    }

    public Restaurant(long id, String nome, double lat, double lon, String tipologia, String indirizzo, int cap,
            String comune, String provincia, String email, String telefono, String fax) {
        this.id = id;
        this.nome = nome;
        this.lat = lat;
        this.lon = lon;
        this.tipologia = tipologia;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.comune = comune;
        this.provincia = provincia;
        this.email = email;
        this.telefono = telefono;
        this.fax = fax;
    }

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "lat")
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Column(name = "lon")
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Column(name = "tipologia")
    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Column(name = "indirizzo")
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Column(name = "cap")
    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    @Column(name = "comune")
    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    @Column(name = "provincia")
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setAllColums(Restaurant newRestaurant) {
        this.nome = newRestaurant.nome;
        this.lat = newRestaurant.lat;
        this.lon = newRestaurant.lon;
        this.tipologia = newRestaurant.tipologia;
        this.indirizzo = newRestaurant.indirizzo;
        this.cap = newRestaurant.cap;
        this.comune = newRestaurant.comune;
        this.provincia = newRestaurant.comune;
        this.email = newRestaurant.email;
        this.telefono = newRestaurant.telefono;
        this.fax = newRestaurant.fax;
    }

    @Transient
    private double distance;

    public void calculateDistance(double userLat, double userLon) {
        double earthRadius = 6371;
        double lat1 = Math.toRadians(lat);
        double lon1 = Math.toRadians(lon);
        double lat2 = Math.toRadians(userLat);
        double lon2 = Math.toRadians(userLon);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        this.distance = earthRadius * c;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }
}
