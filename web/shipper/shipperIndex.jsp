<%-- 
    Document   : shipperIndex
    Created on : 21-Oct-2017, 11:41:32
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<div id="adminMenu" class="alignLeft">
    <p align="center"><strong>Orders</strong></p>
    <p><a href="<c:url value='viewReadyToBeShippedOrders'/>">Orders Awaiting Shipping</a></p>
    <br>
    <p align="center"><a href="<c:url value='logout'/>"><strong>Log Out</strong></a></p>
</div>

<%-- orderList is requested --%>
<c:if test="${!empty readyToBeShippedOrdersList}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="4">Orders</th>
        </tr>
        <tr class="tableHeading">
            <td>Order ID</td>
            <td>Confirmation Number</td>
            <td>Amount</td>
            <td>Date Created</td>
        </tr>
        <c:forEach var="readyToBeShippedOrder" items="${readyToBeShippedOrdersList}" varStatus="iter">
            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow" onclick="document.location.href = 'viewAssociatedSuppliers?customerOrderID=${readyToBeShippedOrder.customerOrderID}'">
                <td><a href="viewAssociatedSuppliers?customerOrderID=${readyToBeShippedOrder.customerOrderID}" class="noDecoration">${readyToBeShippedOrder.customerOrderID}</td>
                <td>${readyToBeShippedOrder.customerOrderConfirmationNumber}</td>
                <td><fmt:formatNumber type="currency" currencySymbol="&pound; " value="${readyToBeShippedOrder.customerOrderAmount}"/></td>
                <td><fmt:formatDate value="${readyToBeShippedOrder.customerOrderCreated}" type="both" dateStyle="short" timeStyle="short"/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<%-- supplierList is requested --%>
<c:if test="${!empty associatedSuppliersList}">
    <table id="adminTable" class="detailsTable">
        <tr class="header">
            <th colspan="4">Supplier Contact for Order Number: <%=request.getParameter("customerOrderID")%></th>
        </tr>
        <tr class="tableHeading">
            <td>ID</td>
            <td>Name</td>
            <td>Phone</td>
            <td>Email</td>
        </tr>
        <c:forEach var="associatedSupplier" items="${associatedSuppliersList}" varStatus="iter">
            <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow">
                <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                <td>${associatedSupplier.supplierID}</a></td>
                <td>${associatedSupplier.supplierCompanyName}</a></td>
                <td>${associatedSupplier.supplierPhone}</a></td>
                <td><a href="mailto:${associatedSupplier.supplierEmail}?Subject=About%20Order%20Number:%20<%=request.getParameter("customerOrderID")%>">${associatedSupplier.supplierEmail}</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>