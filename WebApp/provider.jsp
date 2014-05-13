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
<li><a href = "register.jsp">Customer Registration</a></li>
<li class="current_page_item"><a href="provider.jsp">Provider Registration</a></li>
<li><a href="login.jsp">Login</a></li>
<li><a href="Icouponpublisher.jsp">Publish Coupons</a></li>
</ul>
</div>
<!-- end #menu -->
<div id="page">
<div id="page-bgtop">
<div id="page-bgbtm">
<div id="content">
<h2 class="title"><a href="#">Provider Registration</a></h2>
<div style="clear: both;">&nbsp;</div>
<%
String provider_name = request.getParameter("provider_name");
String password = request.getParameter("password");
String comm = request.getParameter("comm");
String commType = request.getParameter("commType");
String email = request.getParameter("email");
String address = request.getParameter("address");
String city = request.getParameter("city");
String state = request.getParameter("state");
String zip = request.getParameter("zip");
String phone= request.getParameter("phone");
//String status= request.setParameter("0");

if( provider_name != null && provider_name.length() !=0 
    && password != null && password.length() !=0){
  ProviderTO new_pro = couponManager.addNewProvider(provider_name,  password, comm, commType, email, address, city, state, zip, phone, "0");
  if(new_pro != null) {
%>
 <h2>New account created for: <%= new_pro.provider_name%>!</h2>
<%
    return;
  }
  else {
%>
  <h2>The provider with name: <%= provider_name %> is already registered! Please contact customer care. </h2>
<%
  }
}
else {
%>

<div id="register" >
	<form method="post" action="provider.jsp">
	<div>
	<p><span class="style1">Provider Name/company:</span><br /> <input type="text" name="provider_name" id="search-text" value="" /></p>
	<p><span class="style1">Password:</span><br /> <input type="text" name="password" id="search-text" value="" /></p>
	<p><span class="style1">Communication:</span><br /> <input type="text" name="comm" id="search-text" value="" /></p>
	<p><span class="style1">Communication type:</span><br /> <input type="text" name="commType" id="search-text" value="" /></p>
	<p><span class="style1">Email:</span><br /> <input type="text" name="email" id="search-text" value="" /></p>
	<p><span class="style1">Address:</span><br /> <input type="text" name="address" id="search-text" value="" /></p>	
	<p><span class="style1">City:</span><br /> <input type="text" name="city" id="search-text" value="" /></p>
	<p><span class="style1">State:</span><br /></p>
	<select name="state" size="1">
	<option value="AL">Alabama</option>
	<option value="AK">Alaska</option>
	<option value="AZ">Arizona</option>
	<option value="AR">Arkansas</option>
	<option value="CA">California</option>
	<option value="CO">Colorado</option>
	<option value="CT">Connecticut</option>
	<option value="DE">Delaware</option>
	<option value="DC">Dist of Columbia</option>
	<option value="FL">Florida</option>
	<option value="GA">Georgia</option>
	<option value="HI">Hawaii</option>
	<option value="ID">Idaho</option>
	<option value="IL">Illinois</option>
	<option value="IN">Indiana</option>
	<option value="IA">Iowa</option>
	<option value="KS">Kansas</option>
	<option value="KY">Kentucky</option>
	<option value="LA">Louisiana</option>
	<option value="ME">Maine</option>
	<option value="MD">Maryland</option>
	<option value="MA">Massachusetts</option>
	<option value="MI">Michigan</option>
	<option value="MN">Minnesota</option>
	<option value="MS">Mississippi</option>
	<option value="MO">Missouri</option>
	<option value="MT">Montana</option>
	<option value="NE">Nebraska</option>
	<option value="NV">Nevada</option>
	<option value="NH">New Hampshire</option>
	<option value="NJ">New Jersey</option>
	<option value="NM">New Mexico</option>
	<option value="NY">New York</option>
	<option value="NC">North Carolina</option>
	<option value="ND">North Dakota</option>
	<option value="OH">Ohio</option>
	<option value="OK">Oklahoma</option>
	<option value="OR">Oregon</option>
	<option value="PA">Pennsylvania</option>
	<option value="RI">Rhode Island</option>
	<option value="SC">South Carolina</option>
	<option value="SD">South Dakota</option>
	<option value="TN">Tennessee</option>
	<option value="TX">Texas</option>
	<option value="UT">Utah</option>
	<option value="VT">Vermont</option>
	<option value="VA">Virginia</option>
	<option value="WA">Washington</option>
	<option value="WV">West Virginia</option>
	<option value="WI">Wisconsin</option>
	<option value="WY">Wyoming</option>
	</select>

	<p><span class="style1">Zip:</span><br /> <input type="text" name="zip" id="search-text" value="" /></p>
	<p><span class="style1">Contact Number:</span><br /> <input type="text" name="phone" id="search-text" value="" /></p>
	<p><span class="style1"><input type="hidden" name="Language" value="0" /></p>
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
	<div id="footer">
		<p>Copyright (c) 2008 Sitename.com. All rights reserved. Design by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
	</div>
	<!-- end #footer -->
</body>
</html>
