package elements;

public class Address {
    public String street;
    public String suite;
    public String city;
    public String zipcode;
    public Geolocation geo;

    public Address(String street, String suite, String city, String zipcode, Geolocation geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

}