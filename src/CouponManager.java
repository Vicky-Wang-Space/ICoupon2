package icoupon;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;

public interface CouponManager extends EJBObject {

  public Collection findCouponByCategory(String cat_id) throws RemoteException;
  public Collection findCouponByZip(String zip_code) throws RemoteException;
  
  // 
  public CustomerTO findCustomerByUserName(String Usr_name) throws RemoteException;
  public CustomerTO addNewUser(String user_name, String password, String email, 
      String first_name, String last_name, String address) throws RemoteException;
  public  CustomerTO findCustomerById(String id) throws RemoteException;

  public ProviderTO addNewProvider(String provider_name, String password, String comm, String commType, String email, String address, String city, String state, String zip, String phone, String status) throws RemoteException;
  // work-around method to get all customers for user to select when making a comment
  // should be replaced by session implementation, after user login
  public Collection getAllCustomers() throws RemoteException;
  public Collection findCommentsByCoupon(String cpn_id) throws RemoteException;

  public CouponTO addCoupon(String cat_id, String prov_id,
            String name, String str_name, String exp_date, String zip_code,
	          String ori_price, String discount) throws RemoteException;

  public CategoryTO findCategory(String cat_name) throws RemoteException;
  public CategoryTO addCategory(String cat_name) throws RemoteException;
  public Collection findAllCategory() throws RemoteException;
  public Collection findAllProviders() throws RemoteException;

  public void addComment(String coupon_id, String user_id, String content) 
    throws RemoteException;

  
}

