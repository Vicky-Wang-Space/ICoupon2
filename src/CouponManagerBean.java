package icoupon;
import java.rmi.RemoteException;
import javax.ejb.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class CouponManagerBean implements SessionBean {

  protected SessionContext sctx;
  Context ctx;

  public Collection findCouponByCategory(String cat_id) throws RemoteException {
    try
    {
      Collection result = new ArrayList();
      CouponHome home = (CouponHome) ctx.lookup("CouponBean");
      Iterator cpns =  home.findByCategory(cat_id).iterator();
      while(cpns.hasNext()) {
	Coupon cpn = (Coupon)cpns.next();
	result.add(cpn.getCouponData());	
      }
      return result;
    } catch (Exception e) {
      System.out.println("get coupon by category: " +e);
      return null;
    }
  }

  public Collection findCouponByZip(String zip_code) throws RemoteException {
    Collection col = null;
    try
    {
      CouponHome home = (CouponHome) ctx.lookup("CouponBean");
      col =  home.findByZip(zip_code);
      return col;
    } catch (Exception e) {
      System.out.println("get coupon by category: " +e);
      return null;
    }
  }

  public Collection findAllCategory() throws RemoteException {
    try
    {
      CategoryHome home = (CategoryHome) ctx.lookup("CategoryBean");
      Collection result = new ArrayList();
      Iterator cats =  home.findAll().iterator();

      while(cats.hasNext()) {
	Category cat = (Category)cats.next();
	result.add(cat.getCategoryData());
      }
      return result;
    } catch (Exception e) {
      System.out.println("get all categories: " +e);
      return null;
    }
  }

  public Collection findAllProviders() throws RemoteException {
    try
    {
      ProviderHome home = (ProviderHome) ctx.lookup("ProviderBean");
      Collection result = new ArrayList();
      Iterator provs =  home.findAll().iterator();

      while(provs.hasNext()) {
	Provider1 prov = (Provider1)provs.next();
	result.add(prov.getProviderData());
      }
      return result;
    } catch (Exception e) {
      System.out.println("get all providers: " +e);
      return null;
    }
  }

  public CustomerTO findCustomerByUserName(String user_name)
    throws RemoteException {
      try
      {
	CustomerHome home = (CustomerHome)ctx.lookup("CustomerBean");
	CustomerTO cust = new CustomerTO();
	cust = (home.findByUserName(user_name)).getCustomerData();

	return cust;

      } catch (Exception e) {
	System.out.println("get customer by name: " +e);
	return null;
      }
    }
// Add new Customer
  public CustomerTO addNewUser(String user_name, String password, String email,
            String first_name, String last_name, String address) throws RemoteException {
    
    CustomerHome home = null;
    Customer custTry = null;
    CustomerTO cust = null;
    
    try {
      home =  (CustomerHome) ctx.lookup("CustomerBean");
      custTry = home.findByUserName(user_name);
    } 
    catch (ObjectNotFoundException e) {  
      try {
      cust= (home.create("", user_name, password, email,
	    first_name, last_name, address, "0")).getCustomerData();
      return cust;
      } 
      catch (Exception e1) {
	System.out.println("Create Customer Bean: " +e1);
	CustomerTO dummy = new CustomerTO();
	return dummy;
      }
    }
    catch (Exception e2) {
      System.out.println("Create Customer Bean: " +e2);
      CustomerTO dummy = new CustomerTO();
      return dummy;
    }
    if (custTry != null ) {
      return null;
    }
    return cust;

  }
  public  CustomerTO findCustomerById(String id) throws RemoteException {
    try
    {
      CustomerHome home = (CustomerHome)ctx.lookup("CustomerBean");
      CustomerTO cust = (home.findByPrimaryKey(id)).getCustomerData();
      return cust;
    }catch (Exception e) {
      System.out.println("find customer by id: " +e);
      return null;
    }
  }

    
//---------------------------------------------------------------------------------------
// Add new Provider   
  public ProviderTO addNewProvider(String provider_name, String password, String comm, String commType, String email,
            String address, String city, String state, String zip, String phone, String status) throws RemoteException {
    
    ProviderHome home = null;
    Provider1 proTry = null;
    ProviderTO pro = null;
    
    try {
      home =  (ProviderHome) ctx.lookup("ProviderBean");
      proTry = home.findByProviderName(provider_name);
    } 
    catch (ObjectNotFoundException e) {  
      try {
      pro= (home.create("", provider_name, password, comm, commType, email, address, city, state, zip, phone, "0")).getProviderData();
      return pro;
      } 
      catch (Exception e1) {
	System.out.println("Create Provider Bean: " +e1);
	ProviderTO dummy = new ProviderTO();
	return dummy;
      }
    }
    catch (Exception e2) {
      System.out.println("Create Provider Bean: " +e2);
      ProviderTO dummy = new ProviderTO();
      return dummy;
    }
    if (proTry != null ) {
      return null;
    }
    return pro;

  }
//-----------------------------------------------------------------------------------  
  public Collection getAllCustomers() throws RemoteException {
    try {CustomerHome home =  (CustomerHome) ctx.lookup("CustomerBean");
      Collection result = new ArrayList();
      Iterator custs =  home.findAll().iterator();
      while(custs.hasNext()) {
	Customer cust = (Customer)custs.next();
	result.add(cust.getCustomerData());
      }
      return result;
    } catch (Exception e) {
      System.out.println("get all customers: " + e);
      return null;
    }
  }
      
  public Collection findCommentsByCoupon(String cpn_id) 
    throws RemoteException {
      try {
	CommentsHome home =  (CommentsHome) ctx.lookup("CommentsBean");
	Collection result = new ArrayList();
	Iterator cmts =  home.findByCouponId(cpn_id).iterator();
	while(cmts.hasNext()) {
	  Comments cmt = (Comments)cmts.next();
	  result.add(cmt.getCommentsData());
	}
	return result;
      } catch (Exception e) {
	System.out.println("get comments by cpn id: " + e);
	return null;
      }
    }

  
  public CouponTO addCoupon(String cat_id, String prov_id,
      String name, String str_name, String exp_date, String zip_code,
      String ori_price, String discount) throws RemoteException {
    try {
      CouponHome home = (CouponHome) ctx.lookup("CouponBean");
      Coupon cpn = home.create("", cat_id,prov_id, name, str_name, exp_date, zip_code,
	  ori_price, discount);
      return cpn.getCouponData();
    } catch (Exception e) {
      System.out.println("get comments by cpn id: " + e);
      return null;
    }
      
  }

  public void addComment(String coupon_id, String user_id, String content) 
    throws RemoteException {
      try {
	CommentsHome home =  (CommentsHome) ctx.lookup("CommentsBean");
	home.create("", user_id, coupon_id, content);
      }
      catch (Exception e) {
	System.out.println("Add new comments: " + e);
     }      
  }
  

  public CategoryTO addCategory(String cat_name) throws RemoteException {
    try {
      CategoryHome home = (CategoryHome) ctx.lookup("CategoryBean");
      Category cat = home.create("", cat_name);
      CategoryTO cat_to = cat.getCategoryData();
      return cat_to;
    } catch (Exception e) {
      System.out.println("Add new category: " + e);
      return null;
    }

  }

  public CategoryTO findCategory(String cat_name) throws RemoteException {
    try {

      CategoryHome home = (CategoryHome) ctx.lookup("CategoryBean");
      Category cat = home.findByName(cat_name);
      return cat.getCategoryData();
    }  catch (Exception e) {
      System.out.println("Add new category: " + e);
      return null;
    }
  }
    
  public CouponManagerBean () {
    System.out.println("CouponManagerBean constructor called");
  }

  public void ejbCreate() {
    System.out.println("session bean ejbCreate called");
    try {
      ctx = new InitialContext();
    } catch (Exception e) {
      System.out.println("Exception was thrown: " + e.getMessage());
    }
  }
  public void ejbRemove() {
    System.out.println("CouponManagerBean.ejbRemove called");
  }
  public void ejbActivate() {
    System.out.println("CouponManagerBean.ejbActivate called");
  }
  public void ejbPassivate() {
    System.out.println("CouponManagerBean.ejbPassivate called");
  }
  public void setSessionContext(SessionContext sc) {
    System.out.println("CouponManagerBean.setSessionContext called");
    this.sctx = sc;
  }
}
