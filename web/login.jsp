
<%@ include file="header.jsp" %>

<form method="POST" action='<%= response.encodeURL("j_acegi_security_check") %>' >
  <table border="0" cellspacing="5">
    <tr>
      <th align="right">Username:</th>
      <td align="left"><input type="text" name="j_username"></td>
    </tr>
    <tr>
      <th align="right">Password:</th>
      <td align="left"><input type="password" name="j_password"></td>
    </tr>
    <tr>
      <td align="right"><input type="submit" value="LogIn"></td>
      <td align="left"><input type="reset"></td>
    </tr>
  </table>
</form>

<%@ include file="footer.jsp" %>
