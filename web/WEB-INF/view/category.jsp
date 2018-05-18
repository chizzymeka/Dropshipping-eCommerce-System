<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : category
    Created on : 09-Sep-2017, 11:23:58
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<%--<sql:query var="categories" dataSource="jdbc/peripherals">
    SELECT * FROM categories
</sql:query>
    
<sql:query var="selectedCategory" dataSource="jdbc/peripherals">
    SELECT CategoryName FROM categories WHERE CategoryID = ?
    <sql:param value="${pageContext.request.queryString}"/>
</sql:query>
    
<sql:query var="categoryProducts" dataSource="jdbc/peripherals">
        SELECT * FROM products WHERE ProductID = ?
        <sql:param value="${pageContext.request.queryString}"/>
</sql:query>Commented out because of switch to EJB and JPA--%>

<div id="categoryLeftColumn">
    <c:forEach var="category" items="${categories}">
        <c:choose>
            <c:when test="${category.categoryID == pageContext.request.queryString}">
                <div class="categoryButton" id="selectedCategory">
                    <span class="categoryText">
                        ${category.categoryName}
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="category?${category.categoryID}" class="categoryButton">
                    <div class="categoryText">
                        ${category.categoryName}
                    </div>
                </a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<div id="categoryRightColumn">
    <p id="categoryTitle"><p id="categoryTitle">${selectedCategory.categoryName}</p>

    <table id="productTable">
        <c:forEach var="product" items="${categoryProducts}" varStatus="iter">
            <tr class="${((iter.index % 2) == 0) ? 'lightblue' : 'white'}">
                <td>
                    <img src="${initParam.productImagePath}${product.productName}.jpg" alt="${product.productName}">
                </td>
                <td>
                    ${product.productName}
                    <br>
                    <span class="smallText">${product.productDescription}</span>
                </td>

                <td>&pound; ${product.productPrice}</td>

                <td>
                    <form action="<c:url value='addToCart'/>" method="post">
                        <input type="hidden"
                               name="ProductID"
                               value="${product.productID}">
                        <input type="submit"
                               name="submit"
                               value="add to cart">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>