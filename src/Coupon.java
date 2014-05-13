package icoupon;

import javax.ejb.*;

public interface Coupon extends EJBLocalObject {
  public String id();
  public String category_id();
  public String provider_id();
  public String name();
  public String store_name();
  public String expiration_date();
  public String zip_code();
  public String original_price();
  public String discount();

  public CouponTO getCouponData();
}
