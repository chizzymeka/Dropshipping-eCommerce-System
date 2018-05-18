<%-- 
    Document   : index
    Created on : 09-Sep-2017, 10:53:59
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>
<%--<sql:query var="categories" dataSource="jdbc/peripherals">
    SELECT * FROM categories
</sql:query>Commented out because of switch to EJB and JPA--%>

<div id="indexLeftColumn">
    <div id="welcomeText">
        <p>[ welcome text ]</p>
        <!test to access context parameters >
        categoryImagePath: ${initParam.categoryImagePath}
        productImagePath: ${initParam.productImagePath}
    </div>
</div>
<div id="indexRightColumn">
    <c:forEach var="category" items="${categories}">
        <div class="categoryBox">
            <a href="category?${category.categoryID}">
                <span class="categoryLabelText">${category.categoryName}</span>
                <img src="${initParam.categoryImagePath}${category.categoryName}.jpg" alt="${category.categoryName}">
            </a>
        </div>
    </c:forEach>
</div>