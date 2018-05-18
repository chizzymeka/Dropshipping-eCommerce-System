<%-- 
    Document   : index
    Created on : 12-Oct-2017, 10:36:12
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<div id="adminMenu" class="alignLeft">
    <p align="center"><strong>Products</strong></p>
    <p><a href="<c:url value='viewProducts'/>">View all Products</a></p>
    <p><a href="<c:url value='addNewProductForm'/>">Add New Product</a></p>
    <br>
    <p align="center"><strong>Customers & Orders</strong></p>
    <p><a href="<c:url value='viewCustomers'/>">View all Customers</a></p>
    <p><a href="<c:url value='viewOrders'/>">View all Orders</a></p>
    <br>
    <p align="center"><strong>Partners</strong></p>
    <p><a href="<c:url value='viewSuppliers'/>">View all Suppliers</a></p>
    <p><a href="<c:url value='viewShippers'/>">View all Shippers</a></p>
    <p><a href="<c:url value='addNewSupplierForm'/>">Add New Supplier</a></p>
    <p><a href="<c:url value='addNewShipperForm'/>">Add New Shipper</a></p>
    <br>
    <p align="center"><a href="<c:url value='logout'/>"><strong>Log Out</strong></a></p>
</div>
  
<%-- customerList is requested --%>
<c:if test="${!empty customerList}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="4">Customers</th>
        </tr>
        <tr class="tableHeading">
            <td>ID</td>
            <td>First Name</td>
            <td>Last Name</td>
            <td>Phone</td>
            <td>Email</td>
        </tr>
        <c:forEach var="customer" items="${customerList}" varStatus="iter">
            <c:if test="product.SupplierID == 1">
                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow" onclick="document.location.href = 'customerRecord?${customer.customerID}'">
                <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="customerRecord?${customer.customerID}" class="noDecoration">${customer.customerID}</a></td>
                <td><a href="customerRecord?${customer.customerID}" class="noDecoration">${customer.firstName}</a></td>
                <td><a href="customerRecord?${customer.customerID}" class="noDecoration">${customer.lastName}</a></td>
                <td><a href="customerRecord?${customer.customerID}" class="noDecoration">${customer.phone}</a></td>
                <td><a href="customerRecord?${customer.customerID}" class="noDecoration">${customer.email}</a></td>
            </tr>
            </c:if>
        </c:forEach>
    </table>
</c:if>

<%-- productList is requested --%>
<c:if test="${!empty productList}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="4">Products</th>
        </tr>
        <tr class="tableHeading">
            <td>ID</td>
            <td>Product Name</td>
            <td>Description</td>
            <td>Inventory Count</td>
        </tr>
        <c:forEach var="product" items="${productList}" varStatus="iter">
            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow" onclick="document.location.href = 'productRecord?${product.productID}'">
                <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="productRecord?${product.productID}" class="noDecoration">${product.productID}</a></td>
                <td><a href="productRecord?${product.productID}" class="noDecoration">${product.productName}</a></td>
                <td><a href="productRecord?${product.productID}" class="noDecoration">${product.productDescription}</a></td>
                <td><a href="productRecord?${product.productID}" class="noDecoration">${product.productInventoryCount}</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<%-- shipperList is requested --%>
<c:if test="${!empty shipperList}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="4">Shippers</th>
        </tr>
        <tr class="tableHeading">
            <td>ID</td>
            <td>Name</td>
            <td>Phone</td>
            <td>Email</td>
        </tr>
        <c:forEach var="shipper" items="${shipperList}" varStatus="iter">
            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow" onclick="document.location.href = 'supplierRecord?${shipper.shipperID}'">
                <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="shipperRecord?${shipper.shipperID}" class="noDecoration">${shipper.shipperID}</a></td>
                <td><a href="shipperRecord?${shipper.shipperID}" class="noDecoration">${shipper.shipperCompanyName}</a></td>
                <td><a href="shipperRecord?${shipper.shipperID}" class="noDecoration">${shipper.shipperPhone}</a></td>
                <td><a href="shipperRecord?${shipper.shipperID}" class="noDecoration">${shipper.shipperEmail}</a></td>
                <td><input type="radio" class="nominated" value="Y"></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<%-- supplierList is requested --%>
<c:if test="${!empty supplierList}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="4">Suppliers</th>
        </tr>
        <tr class="tableHeading">
            <td>ID</td>
            <td>Name</td>
            <td>Phone</td>
            <td>Email</td>
        </tr>
        <c:forEach var="supplier" items="${supplierList}" varStatus="iter">
            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow" onclick="document.location.href = 'supplierRecord?${supplier.supplierID}'">
                <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="productRecord?${supplier.supplierID}" class="noDecoration">${supplier.supplierID}</a></td>
                <td><a href="productRecord?${supplier.supplierID}" class="noDecoration">${supplier.supplierCompanyName}</a></td>
                <td><a href="productRecord?${supplier.supplierID}" class="noDecoration">${supplier.supplierPhone}</a></td>
                <td><a href="productRecord?${supplier.supplierID}" class="noDecoration">${supplier.supplierEmail}</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<%-- orderList is requested --%>
<c:if test="${!empty orderList}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="4">Orders</th>
        </tr>
        <tr class="tableHeading">
            <td>Order ID</td>
            <td>Confirmation Number</td>
            <td>Amount</td>
            <td>Date Created</td>
            <td>Remove Order</td>
        </tr>
        <c:forEach var="order" items="${orderList}" varStatus="iter">
            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow" onclick="document.location.href = 'orderRecord?${order.customerOrderID}'">
                <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td><a href="orderRecord?${order.customerOrderID}" class="noDecoration">${order.customerOrderID}</a></td>
                <td><a href="orderRecord?${order.customerOrderID}" class="noDecoration">${order.customerOrderConfirmationNumber}</a></td>
                <td><a href="orderRecord?${order.customerOrderID}" class="noDecoration"><fmt:formatNumber type="currency" currencySymbol="&pound; " value="${order.customerOrderAmount}"/></a></td>
                <td><a href="orderRecord?${order.customerOrderID}" class="noDecoration"><fmt:formatDate value="${order.customerOrderCreated}" type="both" dateStyle="short" timeStyle="short"/></a></td>
                <td><input type="submit" value="Remove  "></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<%-- customerRecord is requested --%>
<c:if test="${!empty customerRecord}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="2">Customer Details</th>
        </tr>
        <tr>
            <td style="width: 290px"><strong>Customer ID:</strong></td>
            <td>${customerRecord.customerID}</td>
        </tr>
        <tr>
            <td><strong>Title:</strong></td>
            <td>${customerRecord.title}</td>
        </tr>
        <tr>
            <td><strong>First Name:</strong></td>
            <td>${customerRecord.firstName}</td>
        </tr>
        <tr>
            <td><strong>Last Name:</strong></td>
            <td>${customerRecord.lastName}</td>
        </tr>
        <tr>
            <td><strong>Phone:</strong></td>
            <td>${customerRecord.phone}</td>
        </tr>
        <tr>
            <td><strong>Email Address:</strong></td>
            <td>${customerRecord.email}</td>
        </tr>
        <tr>
            <td><strong>Address Line 1:</strong></td>
            <td>${customerRecord.addressLine1}</td>
        </tr>
        <tr>
            <td><strong>Address Line 2:</strong></td>
            <td>${customerRecord.addressLine2}</td>
        </tr>
        <tr>
            <td><strong>City:</strong></td>
            <td>${customerRecord.city}</td>
        </tr>
        <tr>
            <td><strong>State:</strong></td>
            <td>${customerRecord.state}</td>
        </tr>
        <tr>
            <td><strong>Post Code:</strong></td>
            <td>${customerRecord.postCode}</td>
        </tr>
        <tr>
            <td><strong>Country:</strong></td>
            <td>${customerRecord.country}</td>
        </tr>
        <tr>
            <td><strong>Credit Card:</strong></td>
            <td>${customerRecord.creditCard}</td>
        </tr>

        <tr><td colspan="2" style="padding: 0 20px"><hr></td></tr>

        <tr class="tableRow" onclick="document.location.href = 'orderRecord?${order.customerOrderID}'">
            <%-- Anchor tag is provided in case JavaScript is disabled --%>
            <td colspan="2"><a href="orderRecord?${order.customerOrderID}" class="noDecoration"><strong>View Order Summary &#x279f;</strong></a></td>
        </tr>
    </table>
</c:if>

<%-- orderRecord is requested --%>
<c:if test="${!empty orderRecord}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="2">Order Summary</th>
        </tr>
        <tr>
            <td><strong>Order ID:</strong></td>
            <td>${orderRecord.customerOrderID}</td>
        </tr>
        <tr>
            <td><strong>confirmation number:</strong></td>
            <td>${orderRecord.confirmationNumber}</td>
        </tr>
        <tr>
            <td><strong>Date Processed:</strong></td>
            <td><fmt:formatDate value="${orderRecord.created}" type="both" dateStyle="short" timeStyle="short"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <table class="embedded detailsTable">
                    <tr class="tableHeading">
                        <td class="rigidWidth">Product</td>
                        <td class="rigidWidth">Quantity</td>
                        <td>Price</td>
                    </tr>
                    <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>
                    <c:forEach var="orderedProduct" items="${orderedProducts}" varStatus="iter">
                        <tr>
                            <td>
                                <fmt:message key="${products[iter.index].productName}"/>
                            </td>
                            <td>
                                ${orderedProduct.quantity}
                            </td>
                            <td class="confirmationPriceColumn"><fmt:formatNumber type="currency" currencySymbol="&pound; " value="${products[iter.index].price * orderedProduct.quantity}"/></td>
                        </tr>
                    </c:forEach>
                    <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>
                    <tr>
                        <td colspan="2" id="deliverySurchargeCellLeft"><strong>Delivery Surcharge:</strong></td>
                        <td id="deliverySurchargeCellRight"><fmt:formatNumber type="currency" currencySymbol="&pound; " value="${initParam.deliverySurcharge}"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" id="totalCellLeft"><strong>Total Amount:</strong></td>
                        <td id="totalCellRight"><fmt:formatNumber type="currency" currencySymbol="&pound; " value="${orderRecord.amount}"/></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>
        <tr class="tableRow" onclick="document.location.href = 'customerRecord?${customer.customerID}'">
            <%-- Anchor tag is provided in case JavaScript is disabled --%>
            <td colspan="2"><a href="customerRecord?${customer.customerID}" class="noDecoration"><strong>View Customer Details &#x279f;</strong></a></td>
        </tr>
    </table>
</c:if>

