package proect.dto;

import jakarta.validation.constraints.*;
import org.hibernate.id.factory.internal.UUIDGenerationTypeStrategy;

import java.math.BigDecimal;
import java.util.UUID;

public class GasStationDTO {

    private String id;
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String brand;
    @Size(max = 255)
    private String street;
    @Size(max = 255)
    private String place;
    @Min(-90)
    @Max(90)
    private BigDecimal lat;
    @Min(-180)
    @Max(180)
    private BigDecimal lng;
    @Min(0)
    private BigDecimal diesel;
    @Min(0)
    private BigDecimal e5;
    @Min(0)
    private BigDecimal e10;
    @NotNull
    private boolean isOpen;
    @Size(max = 255)
    private String houseNumber;
    @Positive
    private int postCode;

    public String  getId() {
        return id;
    }

    public GasStationDTO setId(String  id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GasStationDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public GasStationDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public GasStationDTO setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public GasStationDTO setPlace(String place) {
        this.place = place;
        return this;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public GasStationDTO setLat(BigDecimal lat) {
        this.lat = lat;
        return this;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public GasStationDTO setLng(BigDecimal lng) {
        this.lng = lng;
        return this;
    }

    public BigDecimal getDiesel() {
        return diesel;
    }

    public GasStationDTO setDiesel(BigDecimal diesel) {
        this.diesel = diesel;
        return this;
    }

    public BigDecimal getE5() {
        return e5;
    }

    public GasStationDTO setE5(BigDecimal e5) {
        this.e5 = e5;
        return this;
    }

    public BigDecimal getE10() {
        return e10;
    }

    public GasStationDTO setE10(BigDecimal e10) {
        this.e10 = e10;
        return this;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public GasStationDTO setIsOpen(Boolean open) {
        isOpen = open;
        return this;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public GasStationDTO setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public GasStationDTO setPostCode(Integer postCode) {
        this.postCode = postCode;
        return this;
    }

    @Override
    public String toString() {
        return "GasStationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", street='" + street + '\'' +
                ", place='" + place + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", diesel=" + diesel +
                ", e5=" + e5 +
                ", e10=" + e10 +
                ", isOpen=" + isOpen +
                ", houseNumber='" + houseNumber + '\'' +
                ", postcode=" + postCode +
                '}';
    }
}
