package proect.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "gas_station")
public class GasStation {
    @Id()
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
    private Boolean isOpen;
    @Size(max = 255)
    private String houseNumber;
    @Positive
    private Integer postCode;

    public String  getId() {
        return id;
    }

    public GasStation setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GasStation setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public GasStation setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public GasStation setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public GasStation setPlace(String place) {
        this.place = place;
        return this;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public GasStation setLat(BigDecimal lat) {
        this.lat = lat;
        return this;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public GasStation setLng(BigDecimal lng) {
        this.lng = lng;
        return this;
    }

    public BigDecimal getDiesel() {
        return diesel;
    }

    public GasStation setDiesel(BigDecimal diesel) {
        this.diesel = diesel;
        return this;
    }

    public BigDecimal getE5() {
        return e5;
    }

    public GasStation setE5(BigDecimal e5) {
        this.e5 = e5;
        return this;
    }

    public BigDecimal getE10() {
        return e10;
    }

    public GasStation setE10(BigDecimal e10) {
        this.e10 = e10;
        return this;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public GasStation setOpen(Boolean open) {
        isOpen = open;
        return this;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public GasStation setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public GasStation setPostCode(Integer postCode) {
        this.postCode = postCode;
        return this;
    }
}
