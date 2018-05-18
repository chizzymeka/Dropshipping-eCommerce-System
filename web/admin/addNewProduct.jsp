<%-- 
    Document   : addNewProduct
    Created on : 23-Oct-2017, 11:47:08
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<div>
    <form name="addNewProduct" action="addNewProduct" method="POST">
        <table>
            <tr>
                <td><label for="productCategory">Product Category:</label></td>
                <td>
                    <select id="productCategory" name="productCategory">
                        <option selected value>Select a Product Category</option>
                        <c:forEach var="category" items="${productCategories}">
                            <option value="${category.categoryID}">${category.categoryName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="productSupplier">Product Supplier:</label></td>
                <td>
                    <select id="productSupplier" name="productSupplier">
                        <option selected value>Select the Product Supplier</option>
                        <c:forEach var="supplier" items="${productSuppliers}">
                            <option value="${supplier.supplierID}">${supplier.supplierCompanyName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="productName">Product Name:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="productName"
                           name="productName"
                           value="${param.productName}">
                </td>
            </tr>
            <tr>
                <td><label for="price">Price:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="price"
                           name="price"
                           value="${param.price}">
                </td>
            </tr>
            <tr>
                <td><label for="productDescription">Product Description:</label></td>
                <td>
                    <textarea id="productDescription" name="productDescription" rows="4" cols="33" value="${param.productDescription}">
                    </textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Add New Product">
                </td>
            </tr>
        </table>
    </form>
</div>