package icoupon;

import javax.ejb.*;

public interface Category extends EJBLocalObject {
  public String id();
  public String name();

  public CategoryTO getCategoryData();
}
