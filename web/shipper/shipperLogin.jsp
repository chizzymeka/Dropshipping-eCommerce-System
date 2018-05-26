<%-- 
    Document   : shipperLogin
    Created on : 21-Oct-2017, 11:40:22
    Author     : Chizzy Meka | 16036630 | MSc. Computing
--%>

<div id="loginBox">
    <form id="shipperLogin" name="shipperLogin" action="shipperConsole" method="POST">
        <p><strong>Shipper Username:</strong>
        <input type="text" size="20" id="shipperUsername" name="shipperUsername" value="${shipperUsername}"></p>
        <p><strong>Supplier Password:</strong>
        <input type="password" size="20" id="shipperPassword" name="shipperPassword" value="${shipperPassword}"></p>
        <p><input type="submit" value="submit"></p>
    </form>
</div>