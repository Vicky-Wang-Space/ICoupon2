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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>iCoupon</title>
<link href="style.css" rel="stylesheet" type="text/css" media="screen" />

<Script language="JavaScript">
logout()
{
  HttpSession session = request.getSession();
  session.invalidate();
  response.sendRedirect("index.jsp");
}
</Script>

</head>
<body>
<div id="wrapper">
<div id="header">
<div id="logo">
<h1><a href="#">iCoupon</a></h1>
<p>Your Ultimate Coupon Stop</p>
</div>
</div>
<!-- end #header -->

<div id="menu">
<ul>
<li><a href="index.jsp">Search iCoupon</a></li>
<li><a href="register.jsp">Customer Registration</a></li>
<li><a href="provider.jsp">Provider Registration</a></li>
<li class="current_page_item"><a href="login.jsp">Login</a></li>
<li><a href="Icouponpublisher.jsp">Publish Coupons</a></li>
</ul>
</div>

<!-- end #menu -->

<%
HttpSession sess = request.getSession(true);

//String user = (session.getAttribute("icoupon.login")).toString();
if (session.getAttribute("icoupon.login") != null) {
  session.setAttribute("icoupon.message",
      "<H1>You're already logged on!</h2>"+
      "(as user " + session.getAttribute("icoupon.login").toString() + ").");
  %>
	        <TITLE> Already logged on!</TITLE>
		
		 <H1>Already logged on as: <%= session.getAttribute("icoupon.login").toString() %></H1> 
		 <p><a href="index.jsp" onClick="logout();return false">logout</a></p>
		 <%
//    
//  response.sendRedirect("/icoupon");
  return;
}

String user_name = request.getParameter("user_name");
String password = request.getParameter("password");
if (user_name != null && user_name.length() != 0 
    &&  password != null && password.length() != 0) {

  CustomerTO cust = couponManager.findCustomerByUserName(user_name);
  if(cust == null || !cust.password.equals(password) ) {
    %>
      <TITLE>Incorrect Login!</TITLE>
      <H1>Incorrect Login</H1>
      <h2>Please enter correct name and a password <%=password%> :<%=cust.password%> <h2>
      <%    return;
  } else {
    session.setAttribute("icoupon.login", user_name); // login flag
    %>
      <TITLE>Successful Login!</TITLE>
      <BODY BGCOLOR=WHITE>
      <H1>Successful Login</H1>

      <H1>Welcome back, <%=user_name%> </H1>
      <%
  }
}
else {
  %>

	<div id="page">
	<div id="page-bgtop">
	<div id="page-bgbtm">
	<div id="content">

	<h2 class="title"><a href="#">Login</a></h2>
	<div style="clear: both;">&nbsp;</div>
	<div id="login" >
	<form method="post" action="login.jsp">
	<div>

	<p><span class="style1">User Name:</span><br /><input type="text" name="user_name" id="search-text"  /></p>
	<p><span class="style1">Password:</span><br /> <input type="text" name="password" id="search-text"  /></p>
	<input type="submit" id="search-submit" value="Login" />
	</div>
	</form>
	</div>

	<%
}
	%>
				<div style="clear: both;">&nbsp;</div>
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
