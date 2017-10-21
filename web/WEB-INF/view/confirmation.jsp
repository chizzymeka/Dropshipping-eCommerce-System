<%-- 
    Document   : confirmation
    Created on : 09-Sep-2017, 11:24:27
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<%-- Ensure that user is logged when navgigating to this page --%>

 <div id="singleColumn">

    <p id="confirmationText">
        <strong>Your order has been successfully processed and will be delivered within 24 hours.</strong>
        <br><br>
        Please keep a note of your confirmation number:
        <strong>${orderRecord.confirmationNumber}</strong>
        <br>
        If you have a query concerning your order, feel free to <a href="contact" target="_blank">contact us</a>.
        <br><br>
    </p>

    <div class="summaryColumn" >

        <table id="orderSummaryTable" class="detailsTable">
            <tr class="header">
                <th colspan="3">Order Summary</th>
            </tr>

            <tr class="tableHeading">
                <td>Product</td>
                <td>Quantity</td>
                <td>Price</td>
            </tr>

            <c:forEach var="orderedProduct" items="${orderedProducts}" varStatus="iter">

                <tr class="${((iter.index % 2) != 0) ? 'lightBlue' : 'white'}">
                    <td>${products[iter.index].productName}</td>
                    <td class="quantityColumn">
                        ${orderedProduct.quantity}
                    </td>
                    <td class="confirmationPriceColumn">
                        &pound; ${products[iter.index].price * orderedProduct.quantity}
                    </td>
                </tr>

            </c:forEach>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="2" id="deliverySurchargeCellLeft"><strong>Delivery Surcharge:</strong></td>
                <td id="deliverySurchargeCellRight">&pound; ${initParam.deliverySurcharge}</td>
            </tr>

            <tr class="lightBlue">
                <td colspan="2" id="totalCellLeft"><strong>Total:</strong></td>
                <td id="totalCellRight">&pound; ${orderRecord.amount}</td>
            </tr>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="3" id="dateProcessedRow"><strong>Date Processed:</strong>
                    ${orderRecord.created}
                </td>
            </tr>
        </table>

    </div>

    <div class="summaryColumn" >

        <table id="deliveryAddressTable" class="detailsTable">
            <tr class="header">
                <th colspan="3">Delivery Information</th>
            </tr>

            <tr>
                <td colspan="3" class="lightBlue">
                    ${customer.firstName} ${customer.lastName}
                    <br>
                    ${customer.addressLine1}
                    <br>
                    ${customer.addressLine2}
                    <br>
                    ${customer.city}
                    <br>
                    ${customer.state}
                    <br>
                    ${customer.postCode}
                    <hr>
                    <strong>Phone:</strong> ${customer.phone}
                    <br>
                    <strong>Email:</strong> ${customer.email}
                </td>
            </tr>
        </table>

    </div>
</div>