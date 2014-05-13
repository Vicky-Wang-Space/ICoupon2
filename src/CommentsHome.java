package icoupon;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

public interface CommentsHome extends EJBLocalHome {

  public Comments create(String id, String customer_id, 
      String coupon_id, String content)
    throws CreateException;

  public Comments findByPrimaryKey(String primaryKey) 
    throws FinderException;

  public Collection findByCouponId(String coupon_id)
    throws FinderException;
  
  public Collection findByCustomerId(String customer_id)
    throws FinderException;

}

