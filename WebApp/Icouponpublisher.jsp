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
<li><a href="register.jsp">Customer Registration</a></li>
<li><a href="provider.jsp">Provider Registration</a></li>
<li><a href="login.jsp">Login</a></li>
<li class="current_page_item"><a>Publish Coupons</a></li>
</ul>
</div>
<!-- end #menu -->
<div id="page">
<div id="page-bgtop">
<div id="page-bgbtm">
<div id="content">

<h2 class="title"><a href="#">Coupon Publish</a></h2>
<div style="clear: both;">&nbsp;</div>
<%
String new_cat =request.getParameter("new_cat");

String category_id =request.getParameter("category_id");
String provider_id =request.getParameter("provider_id");
String name = request.getParameter("name");
String store_name = request.getParameter("store_name");
String exp_date = request.getParameter("exp_date");
String zip_code = request.getParameter("zip_code");
String ori_price = request.getParameter("ori_price");
String discount =request.getParameter("discount");

if( (provider_id != null) && ( name != null) && (name.length()!=0)) {
  if( (new_cat != null) && (new_cat.length()!=0)) {
    Collection cat_col = couponManager.findAllCategory();

    // check to see if this category already exists
    for(Iterator it = cat_col.iterator(); it.hasNext();) {
      if ( ( (CategoryTO)(it.next()) ).name.equals(new_cat)) {
	%><h2>This category: <%=new_cat%> exists in the system!</h2><%
	  return;
      }
    }

    //add new category to database
    CategoryTO newCat = couponManager.addCategory(new_cat); 
    %><h2>added new category:  <%=newCat.name%>! </h2><%
    
      // newCat in the above does not contain key info so 
     CategoryTO addedCat = couponManager.findCategory(new_cat);
     
      // use newly added category id
      category_id = addedCat.id;
  }
    CouponTO newCoupon = couponManager.addCoupon(category_id, provider_id,
	name, store_name, exp_date, zip_code, ori_price, discount);
  %><h2>Added new coupon: <%=newCoupon.name%>!</h2><%
    return;  
}
else {

%>
<div id="register" >
<form method="post" action="Icouponpublisher.jsp">
<div>
<span class="style1">Category:</span><br \>
<p>
<select name="category_id">
<% Collection col = couponManager.findAllCategory();
CategoryTO cat = null;
for(Iterator it = col.iterator(); it.hasNext();) {
  cat = (CategoryTO)(it.next());
  %>
    <option value="<%= cat.id %>" > <%= cat.name %></option>
    <%
}
%>
</select>
</p>

<span class="style1">Couldn't find a category? Create a new one here: </span><br \>
<p><input type="text" name="new_cat"> </p>

<span class="style1">*Publisher:</span><br \>
<p><select name="provider_id">
<% Collection prov_col = couponManager.findAllProviders();
ProviderTO prov = null;
for(Iterator it = prov_col.iterator(); it.hasNext();) {
  prov = (ProviderTO)(it.next());
  %>
    <option value="<%= prov.id %>" > <%= prov.provider_name %></option>
    <%
}
%>
</select>
</p>

<p><span class="style1">*Name of the coupon:</span><br /> <input type="text" name="name" /></p>
<p><span class="style1">Store Name:</span><br /> <input type="text" name="store_name"  /></p>
<p><span class="style1">Expiration Date:</span><br /> <input type="text" name="exp_date" /></p>
<p><span class="style1">Zip Code:</span><br /> <input type="text" name="zip_code" /></p>
<p><span class="style1">Priginal Price:</span><br /> <input type="text" name="ori_price" /></p>
<p><span class="style1">Discount Percentage:</span><br /> <input type="text" name="discount" /></p>
<input type="submit" id="search-submit" value="GO" />

</div>
</form>

</div>

<div style="clear: both;">&nbsp;</div>
</div>
<!-- end #content -->
<%
}
%>
<div style="clear: both;">&nbsp;</div>
</div>
</div>
</div>
<!-- end #page -->
</div>
<jsp:include page="footer.html" flush="true"/>
</body>
</html>
