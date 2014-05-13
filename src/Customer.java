package icoupon;

import javax.ejb.*;

public interface Customer extends EJBLocalObject {
  public String id();
  public String user_name();
  public String password();
  public String email();
  public String first_name();
  public String last_name();
  public String address();
  public String receive_email();

  public CustomerTO getCustomerData();
}
