package icoupon;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

public interface ProviderHome extends EJBLocalHome {

  public Provider1 create(String id, String pro_nm, String pwd,  String comm1, String commType1,
      String email, String addr, String city, String state, String zip, String phone, String status)
    throws CreateException;

  public Provider1 findByPrimaryKey(String primaryKey) 
    throws FinderException;
  
  public Provider1 findByProviderName (String name)
    throws FinderException;
  
  public Collection findAll()
    throws FinderException;


}
