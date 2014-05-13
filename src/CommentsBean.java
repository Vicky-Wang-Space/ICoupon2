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

abstract public class CommentsBean implements EntityBean {
  final static boolean VERBOSE = true;
  private EntityContext ctx;


  public void setEntityContext(EntityContext ctx) {
    System.out.println("setEntityContext called (" + id() + ")");
    this.ctx = ctx;
  }

  public void unsetEntityContext() {
    this.ctx = null;
    System.out.println("CommentsBean.unsetEntityContext (" + id() + ")");

  }

  /**
   * container managed fields
   */
  abstract public String getId();
  abstract public void setId(String val);  

  abstract public String getCustomer_id();
  abstract public void setCustomer_id(String val);
  
  abstract public String getCoupon_id();
  abstract public void setCoupon_id(String val);
  
  abstract public String getContent();
  abstract public void setContent(String val);


  /* Persistent relationships
   * This defines the n-to-1 relationships 
   * between Comments and Coupon
   */
  abstract public Coupon getCoupon();
  abstract public void setCoupon(Coupon val);

  /* Persistent relationships
   * This defines the n-to-1 relationships
   * between Comments and Customer
   */
  abstract public Customer getCustomer();
  abstract public void setCustomer(Customer val);


  //method to create Data Transfer Object for Comments Bean
  public CommentsTO getCommentsData()
  {
    CommentsTO cmt = new CommentsTO();
    cmt.id = getId();
    cmt.customer_id = getCustomer_id();
    cmt.coupon_id = getCoupon_id();
    cmt.content = getContent();
    return cmt;
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
  public String customer_id() {
    System.out.println("CommentsBean.customer_id(" + id() + ")");
    return getCustomer_id();
  }
  public String coupon_id() {
    System.out.println("CommentsBean.coupon_id(" + id() + ")");
    return getCoupon_id();
  }
  
  public String content() {
    System.out.println("CommentsBean.content(" + id() + ")");
    return getContent();
  }
      

  //callbacks
  public void ejbActivate() {
    System.out.println("comments.ejbActivate (" + id() + ")");
  }
  public void ejbPassivate() {
    System.out.println("comments.ejbPassivate (" + id() + ")");
  }

  public void ejbLoad() {
    System.out.println("comments.ejbLoad (" + id() +  ")");
  }
  public void ejbStore() {
    System.out.println("comments.ejbStore (" + id() + ")");
  }
  public void ejbRemove()
    throws RemoveException
    {
      System.out.println("CommentsBean.ejbRemove (" + id() + ")");
    }

  public String ejbCreate(String id, String customer_id, 
            String coupon_id, String content)
    throws CreateException
    {
      System.out.println("CommentsBean.ejbCreate( id = " 
	  + System.identityHashCode(this) + ", PK = " +
	  id +  ")");

      this.setId(id);
      // Cannot call setter Container-Managed-Relationship in the create
    //  this.setCustomer_id( customer_id);
    //  this.setCoupon_id( coupon_id);
      this.setContent( content);

      return null;  // See 9.4.2 of the EJB 1.1 specification
    }

  public void ejbPostCreate(String id, String customer_id, 
            String coupon_id, String content)
  {
    try {
      InitialContext i = new InitialContext();
      CustomerHome custHome = (CustomerHome) i.lookup("CustomerBean");
      CouponHome cpnHome = (CouponHome) i.lookup("CouponBean");
      System.out.println("comments.ejbPostCreate (" + id + ")");
      setCustomer( custHome.findByPrimaryKey(customer_id));
      setCoupon( cpnHome.findByPrimaryKey(coupon_id));
    } catch (NamingException nm_e) {
      System.out.println("create comments failed at setting foreign key!");
    } catch (FinderException fn_e){
      System.out.println("create comments failed at setting foreign key!");
    } catch (Exception e ){
      System.out.println("create comments failed at setting foreign key!");
    }
  }
}




