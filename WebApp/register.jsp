<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page import="javax.ejb.*, javax.naming.*, javax.rmi.PortableRemoteObject, java.rmi.RemoteException, icoupon.*" %>
<%!

CouponManager couponManager = null;
public void jspInit() {
  try {
    CouponManagerHome home = lookupHome();
    couponManager = home.create();
  } catch (RemoteException ex) {
    System.out.println("Couldn't create CouponManager bean."+ ex.getMessage());
  } catch (CreateException ex) {
    System.out.println("Couldn't create CouponManger bean."+ ex.getMessage());
  } catch (NamingException ex) {
    System.out.println("Unable to lookup home: "+ "CouponManger "+ ex.getMessage());
  }
}

CouponManagerHome lookupHome()
  throws NamingException
{
  Context ctx = getInitialContext();

  try {
    Object home = ctx.lookup("CouponManagerBean");
    return (CouponManagerHome ) PortableRemoteObject.narrow(home, CouponManagerHome.class);

  } catch (NamingException ne) {
    System.out.println("The client was unable to lookup the EJBHome.  Please make sure " +
	"that you have deployed the ejb with the JNDI name " +
	"icoupon.CouponManagerHome on the WebLogic server at ");

    throw ne;
  }
}

Context getInitialContext() throws NamingException {

  try {
    // Get an InitialContext
    Properties h = new Properties();
    h.put(Context.INITIAL_CONTEXT_FACTORY,
	"weblogic.jndi.WLInitialContextFactory");
    //h.put(Context.PROVIDER_URL, url);
    return new InitialContext(h);
  } catch (NamingException ne) {
    System.out.println("We were unable to get a connection to the WebLogic server at ");
    System.out.println("Please make sure that the server is running.");
    throw ne;
  }
}

public void jspDestroy() {
    couponManager = null;
}
%>
<jsp:include page="header.html" flush="true"/>
<div id="menu">
<ul>
<li><a href="index.jsp">Search iCoupon</a></li>
<li class="current_page_item"><a>Customer Registration</a></li>
<li><a href="provider.jsp">Provider Registration</a></li>
<li><a href="login.jsp">Login</a></li>
<li><a href="Icouponpublisher.jsp">Publish Coupons</a></li>
</ul>
</div>
<!-- end #menu -->
<div id="page">
<div id="page-bgtop">
<div id="page-bgbtm">
<div id="content">

<h2 class="title"><a href="#">Customer Registration</a></h2>
<div style="clear: both;">&nbsp;</div>
<%
String user_name = request.getParameter("user_name");
String email = request.getParameter("email");
String password = request.getParameter("password");
String first_name  = request.getParameter("first_name");
String last_name = request.getParameter("last_name");
String address = request.getParameter("address");

if( user_name != null && user_name.length() !=0 
    && password != null && password.length() !=0){
  CustomerTO new_cust = couponManager.addNewUser(user_name,  password, email,
      first_name, last_name, address);
  if(new_cust != null) {
%>
    <h2>New account created for: <%= new_cust.user_name%> !</h2>
<%
    return;
  }
  else {
%>
  <h2>The user name: <%= user_name %> has been taken, please choose a different one! </h2>
<%
  }
}
else {
%>
		<div id="register" >
		<p><font color="red">Please fill all required fields *</font></p>
		<form method="post" action="register.jsp">
		<div>
		<p><span class="style1">*User Name:</span><br /> <input type="text" name="user_name"  value="" /></p>
		<p><span class="style1">Email:</span><br /> <input type="text" name="email"  value="" /></p>
		<p><span class="style1">*Password:</span><br /> <input type="text" name="password"  value="" /></p>
		<p><span class="style1">First Name:</span><br /> <input type="text" name="first_name"  value="" /></p>
		<p><span class="style1">Last Name:</span><br /> <input type="text" name="last_name"  value="" /></p>
		<p><span class="style1">Address:</span><br /> <input type="text" name="address"  value="" /></p>
		<input type="submit" id="search-submit" value="GO" />

		</div>
		</form>

		</div>
<%
}
%>
		<div style="clear: both;">&nbsp;</div>
		</div>
		<!-- end #content -->

		<div style="clear: both;">&nbsp;</div>
	</div>
	</div>
	</div>
	<!-- end #page -->
</div>
<jsp:include page="footer.html" flush="true"/>
</body>
</html>
