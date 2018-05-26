<%-- 
    Document   : supplierLogin
    Created on : 21-Oct-2017, 11:41:57
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<div id="loginBox">
    <form id="supplierLogin" name="supplierLogin" action="supplierConsole" method="POST">
        <p><strong>Supplier Username:</strong>
        <input type="text" size="20" id="supplierUsername" name="supplierUsername" value="${supplierUsername}"></p>
        <p><strong>Supplier Password:</strong>
        <input type="password" size="20" id="supplierPassword" name="supplierPassword" value="${supplierPassword}"></p>
        <p><input type="submit" value="submit"></p>
    </form>
</div>