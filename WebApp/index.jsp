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
<jsp:include page="nav_menu.html" flush="true"/>
<div id="page">
	<div id="page-bgtop">
	<div id="page-bgbtm">
	  <div id="content">
	    <h2 class="title"><a href="#">Search Coupons</a></h2>
	      <div style="clear: both;">&nbsp;</div>
	        <div id="search" >
	          <form method="get" action="index.jsp">
	          <div>
	            Choose a category:
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
	            <input type="submit" id="search-submit" value="GO" />
	          </div>
	        </form>
	    </div>
<%
String cpn_id_cmt = request.getParameter("coupon_id");
String cmmt = request.getParameter("comments");
String cmmtr_id = request.getParameter("user_id");

if( (cpn_id_cmt != null) && (cmmt != null) && (cmmtr_id != null) ) {
  couponManager.addComment(cpn_id_cmt, cmmtr_id, cmmt);
}

String cat_id = request.getParameter("category_id");
if (cat_id != null) {
  col = couponManager.findCouponByCategory(cat_id);
  if (!col.isEmpty()) {
%>
   <div style="clear: both;">&nbsp;<hr \></div>
   
	    <h1>Coupons</h1>
<%
    CouponTO cpn = null;
    for(Iterator it = col.iterator(); it.hasNext();) {
      cpn = (CouponTO)(it.next());			  
%>
         <div id="table" >
  	    <table width="430" height="216" border="1">
	    <tr>
	    <th height="47"><span class="style1">Coupon Name: <%= cpn.name %></span></th> 
	    </tr>
	    <tr>
	    <td height="28"><span class="style1">Store Name: <%= cpn.store_name %></span></td>
	    </tr>
	    <tr>
	    <td><span class="style1">Expiration Date: <%= cpn.exp_date %></span></td>
	    </tr>
	    <tr>
	    <td><span class="style1">zip code: <%= cpn.zip_code %></span></td>
	    </tr>
	    <tr>
	    <td><span class="style1">Original Price: <%= cpn.ori_price %></span></td>
	    </tr>
	    <tr>
	    <td><span class="style1">Discount: <%= cpn.discount %></span></td>
	    </tr>
	    <tr>
	    <td>
	      <span class="style1"><b>Comments:</b><br \><br \>
<%            
               Collection cmtsCol = couponManager.findCommentsByCoupon(cpn.id);
               CommentsTO cmt = null;
               for(Iterator cmtIt = cmtsCol.iterator(); cmtIt.hasNext();) {
		 cmt = (CommentsTO)(cmtIt.next());
		 CustomerTO cmtCust = couponManager.findCustomerById(cmt.customer_id);
%>
	         <i> -- <%=cmtCust.user_name%>: "<%= cmt.content %>"</i><br \>
<%
               }
%>
	      </span>
	    </td>
	    </tr>
	    <% HttpSession sess = request.getSession(true);
               if(  session.getAttribute("icoupon.login")!= null) {               
		 String u_name = session.getAttribute("icoupon.login").toString();
		 CustomerTO cus = couponManager.findCustomerByUserName(u_name);
	     %>

	    <tr>
	    <td>
	    <span class="style1">Leave a comment: 
	    <form method="post" action="index.jsp?category_id=<%=cat_id%>">
	    <input type="hidden" name="coupon_id" value="<%= cpn.id %>" > 
	    <textarea name="comments" cols="40" rows="2">Enter your comments here...
	    </textarea><br />
	    <input type="hidden" name="user_id" value="<%=cus.id%>" >
	    <input type="submit" value="Submit" />
	    </form>
	    
	    </span>
	    
	    </td>
	    </tr>
	    <%
	       }
	       else {
	    %>
	      <tr>
	      <td>
	      <span class="style1">Leave a comment: <br \>
	      <p>Please log in first to leave a comment</p>
	      </span>
	      </td>
	      </tr>
	      <%
	       }
	       %>


	      </table>
	    </div>
<%
    }
  }
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
