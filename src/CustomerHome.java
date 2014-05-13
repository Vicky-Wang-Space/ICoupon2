package icoupon;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

public interface CustomerHome extends EJBLocalHome {

  public Customer create(String id, String u_nm, String pwd,
            String email, String first_name, String last_name, String addr,
	          String rcv_email)
    throws CreateException;

  public Customer findByPrimaryKey(String primaryKey) 
    throws FinderException;
  
  public Customer findByUserName (String name)
    throws FinderException;
  
  public Collection findAll()
    throws FinderException;


}
