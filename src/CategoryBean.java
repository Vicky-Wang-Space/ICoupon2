package icoupon;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.NoSuchEntityException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

abstract public class CategoryBean implements EntityBean {
  final static boolean VERBOSE = true;
  private EntityContext ctx;

  
  public void setEntityContext(EntityContext ctx) {
    System.out.println("setEntityContext called (" + id() + ")");
    this.ctx = ctx;
  }

  public void unsetEntityContext() {
    this.ctx = null;
    System.out.println("CategoryBean.unsetEntityContext (" + id() + ")");

  }

  /**
   * container managed fields
   */
  abstract public String getId();
  abstract public void setId(String val);  
      
  abstract public String getName();
  abstract public void setName(String val);
  


  /* Persistent relationships
   * This defines the 1-to-n relationships 
   * between Category and Coupon
   */
  abstract public Collection getCoupon();
  abstract public void setCoupon(Collection val);
  


  //method to create Data Transfer Object for Category Bean
  public CategoryTO getCategoryData()
  {
    CategoryTO cat = new CategoryTO();
    cat.id = getId();
    cat.name = getName();
    return cat;
  }
  
   /**
   * Returns the Primary Key identifying this EJBean.
   * @return                  String Identification
   */
  public String id() {
    return "" + System.identityHashCode(this) + ", PK = " +
      (String) ((ctx == null) ? "nullctx": ((ctx.getPrimaryKey() == null ?
					     "null" : ctx.getPrimaryKey().toString())));
  }
  public String name() {
    System.out.println("CategoryBean.lastname (" + id() + ")");
    return getName();
  }

  //callbacks
  public void ejbActivate() {
    System.out.println("category.ejbActivate (" + id() + ")");
  }
  public void ejbPassivate() {
    System.out.println("category.ejbPassivate (" + id() + ")");
  }

  public void ejbLoad() {
    System.out.println("category.ejbLoad (" + id() +  ")");
  }
  public void ejbStore() {
    System.out.println("category.ejbStore (" + id() + ")");
  }
  public void ejbRemove()
    throws RemoveException
    {
      System.out.println("AccountfallBean.ejbRemove (" + id() + ")");
    }

  public String ejbCreate(String id, String name)
    throws CreateException
    {
      System.out.println("CategoryBean.ejbCreate( id = " 
	  + System.identityHashCode(this) + ", PK = " +
	  id + ", " + "name =  " + name + ")");

      this.setId(id);
      this.setName(name);

      return null;  // See 9.4.2 of the EJB 1.1 specification
    }

  public void ejbPostCreate(String id, String name)
  {
    System.out.println("category.ejbPostCreate (" + id + ")");
  }
}



