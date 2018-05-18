<%-- 
    Document   : cart
    Created on : 09-Sep-2017, 11:23:41
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<div id="singleColumn">

    <c:choose>
        <c:when test="${cart.numberOfItems > 1}">
            <p>Your shopping cart contains ${cart.numberOfItems} items.</p>
        </c:when>
        <c:when test="${cart.numberOfItems == 1}">
            <p>Your shopping cart contains ${cart.numberOfItems} item.</p>
        </c:when>
        <c:otherwise>
            <p>Your shopping cart is empty.</p>
        </c:otherwise>
    </c:choose>

    <div id="actionBar">
        <%-- clear cart widget --%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">
            <c:url var="url" value="viewCart">
                <c:param name="clear" value="true"/>
            </c:url>
            <a href="${url}" class="bubble hMargin">Clear Cart</a>
        </c:if>

        <%-- continue shopping widget --%>
        <c:set var="value">
            <c:choose>
                <%-- if 'selectedCategory' session object exists, send user to previously viewed category --%>
                <c:when test="${!empty selectedCategory}">
                    category
                </c:when>
                <%-- otherwise send user to welcome page --%>
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>

        <a href="${value}" class="bubble hMargin">Continue Shopping</a>

        <%-- checkout widget --%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">
            <a href="checkout" class="bubble hMargin">Checkout &#x279f;</a>
        </c:if>
    </div>

    <c:if test="${!empty cart && cart.numberOfItems != 0}">

        <h4 id="subtotal">subtotal: &pound; ${cart.subtotal}</h4>

        <table id="cartTable">

            <tr class="header">
                <th>Product</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>

            <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">

                <c:set var="product" value="${cartItem.product}"/>

                <tr class="${((iter.index % 2) == 0) ? 'lightBlue' : 'white'}">
                    <td>
                        <img src="${initParam.productImagePath}${product.productName}.jpg"
                             alt="${product.productName}">
                    </td>

                    <td>${product.productName}</td>

                    <td>
                        &pound; ${cartItem.total}
                        <br>
                        <span class="smallText">( &pound; ${product.productPrice} / unit )</span>
                    </td>

                    <td>
                        <form action="updateCart" method="post">
                            <input type="hidden"
                                   name="productId"
                                   value="${product.productID}">
                            <input type="text"
                                   maxlength="2"
                                   size="2"
                                   value="${cartItem.quantity}"
                                   name="quantity"
                                   style="margin:5px">
                            <input type="submit"
                                   name="submit"
                                   value="update">
                        </form>
                    </td>
                </tr>

            </c:forEach>

        </table>

    </c:if>
</div>