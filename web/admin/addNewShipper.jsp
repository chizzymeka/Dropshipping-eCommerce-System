<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 
    Document   : addNewShipper
    Created on : 23-Oct-2017, 11:47:39
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<div>
    <form name="addNewShipper" action="addNewShipper" method="POST">
        <table>
            <tr>
                <td><label for="shipperCompanyName">Shipper Company Name:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperCompanyName"
                           name="shipperCompanyName"
                           value="${param.shipperCompanyName}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperContactFirstName">Shipper Contact First Name:</label></td>
                <td>
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperContactFirstName"
                           name="shipperContactFirstName"
                           value="${param.shipperContactFirstName}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperContactLastName">Shipper Contact Last Name:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperContactLastName"
                           name="shipperContactLastName"
                           value="${param.shipperContactLastName}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperContactTitle">Shipper Contact Title:</label></td>
                <td class="inputField">
                    <select id="shipperContactTitle" name="shipperContactTitle">
                        <option selected value>Select Title</option>
                        <option value="Mr.">Mr.</option>
                        <option value="Mrs.">Mrs.</option>
                        <option value="Ms.">Ms.</option>
                        <option value="Dr.">Dr.</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="shipperAddressLine1">Shipper Address Line 1:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperAddressLine1"
                           name="shipperAddressLine1"
                           value="${param.shipperAddressLine1}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperAddressLine2">Shipper Address Line 2:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperAddressLine2"
                           name="shipperAddressLine2"
                           value="${param.shipperAddressLine2}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperCity">Shipper City:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperCity"
                           name="shipperCity"
                           value="${param.shipperCity}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperState">Shipper State:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperState"
                           name="shipperState"
                           value="${param.shipperState}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperPostCode">Shipper Post Code:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperPostCode"
                           name="shipperPostCode"
                           value="${param.shipperPostCode}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperCountry">Shipper Country:</label></td>
                <td class="inputField">
                    <select id="shipperCountry" name="shipperCountry">
                        <option selected value>Select Country</option>
                        <option value="UK">UK</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="shipperPhone">Shipper Phone:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperPhone"
                           name="shipperPhone"
                           value="${param.shipperPhone}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperEmail">Shipper Email:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperEmail"
                           name="shipperEmail"
                           value="${param.shipperEmail}">
                </td>
            </tr>
            <tr>
                <td><label for="shipperWebsite">Shipper Website:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="shipperWebsite"
                           name="shipperWebsite"
                           value="${param.shipperWebsite}">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Add New Shipper">
                </td>
            </tr>
        </table>
    </form>
</div>
