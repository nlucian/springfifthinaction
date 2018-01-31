package tacos;


import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.util.Date;


@Data
public class Order {

    private  Long id;

    private Date placedAt;

    @NotBlank(message = "nName is required.")
    public String name;

    @NotBlank(message = "City is required.")
    public String city;

    @NotBlank(message = "Address is required.")
    public String address;

    @NotBlank(message = "Street is required.")
    public String street;

    @NotBlank(message = "State is required.")
    public String state;

    @NotBlank(message = "Zip is required")
    public String zip;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    public String ccExpiration;

    @CreditCardNumber
    public String ccNumber;

    @Digits(fraction = 0, integer = 3, message = "Invalid CVV")
    public String ccCVV;

}
