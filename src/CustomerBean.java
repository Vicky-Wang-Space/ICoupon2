package icoupon;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.*;

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

abstract public class CustomerBean implements EntityBean {
  final static boolean VERBOSE = true;
  private EntityContext ctx;

  
  public void setEntityContext(EntityContext ctx) {
    System.out.println("setEntityContext called (" + id() + ")");
    this.ctx = ctx;
  }

  public void unsetEntityContext() {
    this.ctx = null;
    System.out.println("CustomerBean.unsetEntityContext (" + id() + ")");

  }

  /**
   * container managed fields
   */
  abstract public String getId();
  abstract public void setId(String val);  

  abstract public String getUser_name();
  abstract public void setUser_name(String val);
   
  abstract public String getPassword();
  abstract public void setPassword(String val);
  
  abstract public String getEmail();
  abstract public void setEmail(String val);
    
  abstract public String getFirst_name();
  abstract public void setFirst_name(String val);

  abstract public String getLast_name();
  abstract public void setLast_name(String val);
  
  abstract public String getAddress();
  abstract public void setAddress(String val);

  abstract public String getReceive_email();
  abstract public void setReceive_email(String val);

  //one-to-many relationship from Customer table to Comments table
  
  abstract public Collection getComments();
  abstract public void setComments(Collection cmts);
  
  //method to create Data Transfer Object for Customer Bean
  public CustomerTO getCustomerData()
  {
    CustomerTO cust = new CustomerTO();
    cust.id = getId();
    cust.user_name = getUser_name();
    cust.password = getPassword();
    cust.email = getEmail();
    cust.first_name = getFirst_name();
    cust.last_name =  getLast_name();
    cust.address = getAddress();
    cust.receive_email = getReceive_email();
    return cust;
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
  public String user_name() {
     System.out.println("CustomerBean.user_name (" + id() + ")");
    return getUser_name();
  }
  public String password(){
     System.out.println("CustomerBean.password (" + id() + ")");
    return getPassword();
  }

  public String email() {
    System.out.println("CustomerBean.email (" + id() + ")");
    return getEmail();
  }
  
  public String first_name() {
    System.out.println("CustomerBean.first_name (" + id() + ")");
    return getFirst_name();
  }

  public String last_name() {
    System.out.println("CustomerBean.last_name (" + id() + ")");
    return getLast_name();
  }
 
  public String address() {
    System.out.println("CustomerBean.address (" + id() + ")");
    return getAddress();
  }
  public String receive_email() {
    System.out.println("CustomerBean.receive_email (" + id() + ")");
    return getReceive_email();
  }

  //callback methods
  public void ejbActivate() {
    System.out.println("customer.ejbActivate (" + id() + ")");
  }
  public void ejbPassivate() {
    System.out.println("customer.ejbPassivate (" + id() + ")");
  }

  public void ejbLoad() {
    System.out.println("customer.ejbLoad (" + id() +  ")");
  }
  public void ejbStore() {
    System.out.println("customer.ejbStore (" + id() + ")");
  }
  public void ejbRemove()
    throws RemoveException
    {
      System.out.println("AccountfallBean.ejbRemove (" + id() + ")");
    }

  public String ejbCreate(String id, String u_nm, String pwd,
      String email, String first_name, String last_name, String addr,
      String rcv_email)
    throws CreateException
    {
      System.out.println("CustomerBean.ejbCreate( id = " 
	  + System.identityHashCode(this) + ", PK = " +
	  id  + ")");

      this.setId(id);
      this.setUser_name(u_nm);
      this.setPassword(pwd);
      this.setEmail(email);
      this.setFirst_name(first_name);
      this.setLast_name(last_name);
      this.setAddress(addr);
      this.setReceive_email(rcv_email);
      
      return null;  // See 9.4.2 of the EJB 1.1 specification
    }

  public void ejbPostCreate(String id, String u_nm, String pwd,
      String email, String first_name, String last_name, String addr,
      String rcv_email)
  {
    System.out.println("customer.ejbPostCreate (" + id + ")");
  }
}



