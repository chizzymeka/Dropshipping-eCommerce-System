<%-- 
    Document   : supplierIndex
    Created on : 21-Oct-2017, 11:42:25
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<div id="adminMenu" class="alignLeft">
    <p align="center"><strong><%=session.getAttribute("supplierCompanyName")%></strong></p>
    <br>
    <p align="center"><strong>Inventory Management</strong></p>
    <p><a href="<c:url value='viewMyProducts'/>">View My Products</a></p>
    <br>
    <p align="center"><strong>Outbound Orders</strong></p>
    <p><a href="<c:url value='ordersDueForCollection'/>">Due for Collection</a></p>
    <br>
    <p align="center"><a href="<c:url value='supplierLoginForm'/>"><strong>Log Out</strong></a></p>
</div>

<%-- supplierProductList is requested --%>
<c:choose>
    <c:when test="${!empty supplierProductsList}">
        <table id="adminTable" class="detailsTable">
            <tr class="header">
                <th colspan="4">Products and Stock Count</th>
            </tr>
            <tr class="tableHeading">
                <td>ID</td>
                <td>Product Name</td>
                <td>Description</td>
                <td>Inventory Count</td>
                <td></td>
            </tr>
            <c:forEach var="supplierProduct" items="${supplierProductsList}" varStatus="iter">
                    <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow">
                        <%-- Below anchor tags are provided in case JavaScript is disabled --%>
                        <td>${supplierProduct.productID}</td>
                        <td>${supplierProduct.productName}</td>
                        <td>${supplierProduct.productDescription}</td>
                        <td><input type="text" size="5" maxlength="45" id="invenotryCount" name="invenotryCount" value="${supplierProduct.productInventoryCount}"></a></td>
                        <td>
                            <form name="updateInvenoryCount" action="updateInventoryCount" method="POST">
                                <input type="submit" value="Update">
                            </form>
                        </td>
                    </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <!-- if list is empty and error message is not empty, then display error message. -->
        <c:if test="${empty supplierProductsList}">
            <c:if test="${!empty noProductsErrorMessage}">
                <script type="text/javascript">
                    alert("<%=request.getAttribute("noProductsErrorMessage")%>");
                </script>
            </c:if>
        </c:if>
    </c:otherwise>
</c:choose>


<%-- orderList is requested --%>
<c:choose>
    <c:when test="${!empty ordersDueForCollectionList}">
        <table id="adminTable" class="detailsTable">
            <tr class="header">
                <th colspan="4">Orders</th>
            </tr>
            <tr class="tableHeading">
                <td>Order ID</td>
                <td>Confirmation Number</td>
                <td>Product</td>
                <td>Quantity</td>
                <td>Order Placed</td>
            </tr>
            <c:forEach var="orderDueForCollection" items="${ordersDueForCollectionList}" varStatus="iter">
                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow">
                    <td>${orderDueForCollection.customerOrderID}</td>
                    <td>${orderDueForCollection.confirmationNumber}</td>
                    <td>${orderDueForCollection.productName}</td>
                    <td>${orderDueForCollection.quantity}</td>
                    <td><fmt:formatDate value="${orderDueForCollection.date}" type="both" dateStyle="short" timeStyle="short"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <!-- if list is empty and error message is not empty, then display error message. -->
        <c:if test="${empty ordersDueForCollectionList}">
            <c:if test="${!empty noOrdersErrorMessage}">
                <script type="text/javascript">
                    alert("<%=request.getAttribute("noOrdersErrorMessage")%>");
                </script>
            </c:if>
        </c:if>
    </c:otherwise>
</c:choose>