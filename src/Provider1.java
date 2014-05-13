package icoupon;

import javax.ejb.*;

public interface Provider1 extends EJBLocalObject {
  public String id();
  public String provider_name();
  public String password();
  public String communication1();
  public String communicationType1();
  public String email();
  public String address();
  public String city();
  public String state();
  public String zip();
  public String phone();
  public String status();

  public ProviderTO getProviderData();
}
