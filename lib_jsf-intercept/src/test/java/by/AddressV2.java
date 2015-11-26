package by;

import java.util.Arrays;

/**
 * User: Alexey.Koyro
 */
public class AddressV2
{
    private Integer houseNumber;
    private String street;
    private String city;
    private String stateOrProvince;
    private String country;


    @Override
    public boolean equals(Object obj)
    {


        boolean result;

        if (this == obj)
            result = true;
        else if (obj!=null && getClass() == obj.getClass())
        {
            AddressV2 other = (AddressV2) obj;

            Object[] fields =
                    {houseNumber, street, city, stateOrProvince, country};

            Object[] otherFields =
                    {other.houseNumber, other.street, other.city,
                            other.stateOrProvince, other.country};

            result = Arrays.equals(fields, otherFields);
        }
        else result = false;

        return result;
    }

    public Nested getNested()
    {
        class NestedImpl implements Nested
        {

            @Override
            public void doSmth() {

            }
        }

        return new NestedImpl();
    }

    public Integer getHouseNumber() {return houseNumber;}
    public void setHouseNumber(Integer houseNumber)
    {this.houseNumber = houseNumber;}

    public String getStreet() {return street;}
    public void setStreet(String street) {this.street = street;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getStateOrProvince() {return stateOrProvince;}
    public void setStateOrProvince(String stateOrProvince)
    {this.stateOrProvince = stateOrProvince;}

    public String getCountry() {return country;}
    public void setCountry(String country)
    {this.country = country;}

    private interface Nested
    {


        void doSmth();
    }
}
