<%-- 
    Document   : checkout
    Created on : 09-Sep-2017, 11:24:12
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<%-- Ensure that user is logged when navgigating to this page --%>

<div id="singleColumn">

    <h2>checkout</h2>

    <p>In order to purchase the items in your shopping cart, please provide us with the following information:</p>

    <form action="purchase" method="post">
        <table id="checkoutTable">
            <tr>
                <td><label for="title">Title:</label></td>
                <td>
                    <select id="title" name="title">
                        <option value="Mr">Mr</option>
                        <option value="Mrs">Mrs</option>
                        <option value="Miss">Miss</option>
                        <option value="Dr">Dr</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="firstName">First Name:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="firstName"
                           name="firstName"
                           value="${param.firstName}">
                </td>
            </tr>
            <tr>
                <td><label for="lastName">Last Name:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="lastName"
                           name="lastName"
                           value="${param.lastName}">
                </td>
            </tr>
            <tr>
                <td><label for="phone">Phone:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="phone"
                           name="phone"
                           value="${param.phone}">
                </td>
            </tr>
            <tr>
                <td><label for="email">Email:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="email"
                           name="email"
                           value="${param.email}">
                </td>
            </tr>
            <tr>
                <td><label for="addressLine1">Address Line 1:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="addressLine1"
                           name="addressLine1"
                           value="${param.addressLine1}">
                </td>
            </tr>
            <tr>
                <td><label for="addressLine2">Address Line 2:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="addressLine2"
                           name="addressLine2"
                           value="${param.addressLine2}">
                </td>
            </tr>
            <tr>
                <td><label for="city">City:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="city"
                           name="city"
                           value="${param.city}">
                </td>
            </tr>
            <tr>
                <td><label for="state">State:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="email"
                           name="state"
                           value="${param.state}">
                </td>
            </tr>
            <tr>
                <td><label for="postCode">Post Code:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="16"
                           id="postCode"
                           name="postCode"
                           value="${param.postCode}">
                </td>
            </tr>
            <tr>
                <td><label for="country">Country:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="16"
                           id="country"
                           name="country"
                           value="${param.country}">
                </td>
            </tr>
            <tr>
                <td><label for="creditCard">Credit Card:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="19"
                           id="creditCard"
                           name="creditCard"
                           value="${param.creditCard}">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="submit purchase">
                </td>
            </tr>
        </table>
    </form>

    <div id="infoBox">

        <ul>
            <li>3-4 working days delivery is guaranteed.</li>
            <li>A &pound; ${initParam.deliverySurcharge}
                delivery surcharge is applied to all purchase orders</li>
        </ul>

        <table id="priceBox">
            <tr>
                <td>subtotal:</td>
                <td class="checkoutPriceColumn">
                    &pound; ${cart.subtotal}</td>
            </tr>
            <tr>
                <td>delivery surcharge:</td>
                <td class="checkoutPriceColumn">
                    &pound; ${initParam.deliverySurcharge}</td>
            </tr>
            <tr>
                <td class="total">total:</td>
                <td class="total checkoutPriceColumn">
                    &pound; ${cart.total}</td>
            </tr>
        </table>
    </div>
</div>