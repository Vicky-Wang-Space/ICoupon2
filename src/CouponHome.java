package icoupon;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

public interface CouponHome extends EJBLocalHome {

  public Coupon create(String id, String c_id, String p_id, String name, String str_name, String exp_date, String zip_code, String org_price, String discount)
    throws CreateException;

  public Coupon findByPrimaryKey(String primaryKey) 
    throws FinderException;
  
  public Collection findByZip (String zip_code)
    throws FinderException;

  public Collection findByCategory(String cat_id)
    throws FinderException;

}
