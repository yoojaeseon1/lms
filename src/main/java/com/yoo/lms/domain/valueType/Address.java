package com.yoo.lms.domain.valueType;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Address {

//    private String city;
//    private String street;
//    private String zipcode;

    private String postNumber;
    private String roadAddress;
    private String detailAddress;

    protected Address() {
    }

    public Address(String postNumber, String roadAddress, String detailAddress) {
        this.postNumber = postNumber;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(postNumber, address.postNumber) && Objects.equals(roadAddress, address.roadAddress) && Objects.equals(detailAddress, address.detailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postNumber, roadAddress, detailAddress);
    }

    //    public Address(String city, String street, String zipcode) {
//        this.city = city;
//        this.street = street;
//        this.zipcode = zipcode;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Address address = (Address) o;
//        return Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(city, street, zipcode);
//    }
}
