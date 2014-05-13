package icoupon;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.util.Collection;

public interface CategoryHome extends EJBLocalHome {

  public Category create(String id, String name)
    throws CreateException;

  public Category findByPrimaryKey(String primaryKey) 
    throws FinderException;
  public Category findByName(String name) throws FinderException;

  public Collection findAll()
    throws FinderException;
}
