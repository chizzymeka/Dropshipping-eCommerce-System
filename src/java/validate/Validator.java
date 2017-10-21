/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
public class Validator {

    // ensures that quantity input is number between 0 and 99
    // applies to quantity fields in cart page
    public boolean validateQuantity(String productId, String quantity) {

        boolean errorFlag = false;

        if (!productId.isEmpty() && !quantity.isEmpty()) {

            int i = -1;

            try {

                i = Integer.parseInt(quantity);
            } catch (NumberFormatException nfe) {

                System.out.println("User did not enter a number in the quantity field");
            }

            if (i < 0 || i > 99) {

                errorFlag = true;
            }
        }
        return errorFlag;
    }

    // performs simple validation on checkout form
    public boolean validateForm(String title,
            String firstName,
            String lastName,
            String phone,
            String email,
            String addressLine1,
            String addressLine2,
            String city,
            String state,
            String postCode,
            String country,
            String creditCard,
            HttpServletRequest request) {

        boolean errorFlag = false;
        
        boolean titleError;
        boolean firstNameError;
        boolean lastNameError;
        boolean phoneError;
        boolean emailError;
        boolean addressLine1Error;
        boolean addressLine2Error;
        boolean cityError;
        boolean stateError;
        boolean postCodeError;
        boolean countryError;
        boolean creditCardError;

        if (title == null || title.equals("") || title.length() > 45) {
            errorFlag = true;
            titleError = true;
            request.setAttribute("titleError", titleError);
        }

        if (firstName == null || firstName.equals("") || firstName.length() > 45) {
            errorFlag = true;
            firstNameError = true;
            request.setAttribute("firstNameError", firstNameError);
        }

        if (lastName == null || lastName.equals("") || lastName.length() > 45) {
            errorFlag = true;
            lastNameError = true;
            request.setAttribute("lastNameError", lastNameError);
        }

        if (phone == null || phone.equals("") || phone.length() < 9) {
            errorFlag = true;
            phoneError = true;
            request.setAttribute("phoneError", phoneError);
        }

        if (email == null || email.equals("") || !email.contains("@")) {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }

        if (addressLine1 == null || addressLine1.equals("") || addressLine1.length() > 45) {
            errorFlag = true;
            addressLine1Error = true;
            request.setAttribute("addressLine1Error", addressLine1Error);
        }

        if (addressLine2 == null || addressLine2.equals("") || addressLine2.length() > 45) {
            errorFlag = true;
            addressLine2Error = true;
            request.setAttribute("addressLine2Error", addressLine2Error);
        }

        if (city == null || city.equals("") || city.length() > 2) {
            errorFlag = true;
            cityError = true;
            request.setAttribute("cityError", cityError);
        }
        
        if (state == null || state.equals("") || state.length() > 2) {
            errorFlag = true;
            stateError = true;
            request.setAttribute("stateError", stateError);
        }
        
        if (postCode == null || postCode.equals("") || postCode.length() > 8) {
            errorFlag = true;
            postCodeError = true;
            request.setAttribute("postCodeError", postCodeError);
        }
        
        if (country == null || country.equals("") || country.length() > 50) {
            errorFlag = true;
            countryError = true;
            request.setAttribute("countryError", countryError);
        }

        if (creditCard == null || creditCard.equals("") || creditCard.length() > 19) {
            errorFlag = true;
            creditCardError = true;
            request.setAttribute("ccNumberError", creditCardError);
        }
        return errorFlag;
    }
}
