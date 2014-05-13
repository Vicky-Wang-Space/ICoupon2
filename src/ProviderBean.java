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

abstract public class ProviderBean implements EntityBean {
  final static boolean VERBOSE = true;
  private EntityContext ctx;

  
  public void setEntityContext(EntityContext ctx) {
    System.out.println("setEntityContext called (" + id() + ")");
    this.ctx = ctx;
  }

  public void unsetEntityContext() {
    this.ctx = null;
    System.out.println("ProviderBean.unsetEntityContext (" + id() + ")");

  }

  /**
   * container managed fields
   */
  abstract public String getId();
  abstract public void setId(String val);  

  abstract public String getProvider_name();
  abstract public void setProvider_name(String val);
   
  abstract public String getPassword();
  abstract public void setPassword(String val);
   
  abstract public String getCommunication1();
  abstract public void setCommunication1(String val);

  abstract public String getCommunicationType1();
  abstract public void setCommunicationType1(String val);

  abstract public String getEmail();
  abstract public void setEmail(String val);
    
  abstract public String getAddress();
  abstract public void setAddress(String val);

  abstract public String getCity();
  abstract public void setCity(String val);

   abstract public String getState();
  abstract public void setState(String val);
  
  abstract public String getZip();
  abstract public void setZip(String val);

  abstract public String getPhone();
  abstract public void setPhone(String val);

  abstract public String getStatus();
  abstract public void setStatus(String val);


  /* Persistent relationships
   * This defines the 1-to-n relationships 
   * between Provider and Coupon
   */
  abstract public Collection getCoupon();
  abstract public void setCoupon(Collection val);

  
  //method to create Data Transfer Object for Provider Bean
  public ProviderTO getProviderData()
  {
    ProviderTO pro = new ProviderTO();
    pro.id = getId();
    pro.provider_name = getProvider_name();
    pro.password = getPassword();
	pro.communication1= getCommunication1();
	pro.communicationType1= getCommunicationType1();
    pro.email = getEmail();
	pro.address= getAddress();
	pro.city= getCity();
	pro.state= getState();
	pro.zip= getZip();
	pro.phone= getPhone();
	pro.status= getStatus();
 
    return pro;
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
  public String  provider_name() {
     System.out.println("ProviderBean. (" + id() + ")");
    return getProvider_name();
  }
  public String password(){
     System.out.println("ProviderBean.password (" + id() + ")");
    return getPassword();
  }

  public String communication1() {
    System.out.println("ProviderBean.communication1 (" + id() + ")");
    return getCommunication1();
  }

  public String communicationType1() {
    System.out.println("ProviderBean.communicationType1 (" + id() + ")");
    return getCommunicationType1();
  }

  public String email() {
    System.out.println("ProviderBean.email (" + id() + ")");
    return getEmail();
  }
  
   public String address() {
    System.out.println("ProviderBean.address (" + id() + ")");
    return getAddress();
  }

  public String city() {
    System.out.println("ProviderBean.city (" + id() + ")");
    return getCity();
  }

  public String state() {
    System.out.println("ProviderBean.state (" + id() + ")");
    return getState();
  }

  public String zip() {
    System.out.println("ProviderBean.zip (" + id() + ")");
    return getZip();
  }
 
  public String phone() {
    System.out.println("ProviderBean.phone (" + id() + ")");
    return getPhone();
  }

	
  public String status() {
    System.out.println("ProviderBean.status (" + id() + ")");
    return getStatus();
  }



  //callback methods
  public void ejbActivate() {
    System.out.println("provider.ejbActivate (" + id() + ")");
  }
  public void ejbPassivate() {
    System.out.println("provider.ejbPassivate (" + id() + ")");
  }

  public void ejbLoad() {
    System.out.println("provider.ejbLoad (" + id() +  ")");
  }
  public void ejbStore() {
    System.out.println("provider.ejbStore (" + id() + ")");
  }
  public void ejbRemove()
    throws RemoveException
    {
      System.out.println("AccountfallBean.ejbRemove (" + id() + ")");
    }

  public String ejbCreate(String id, String pro_nm, String pwd,  String comm1, String commType1,
      String email, String addr, String city, String state, String zip, String phone, String status)
    throws CreateException
    {
      System.out.println("ProviderBean.ejbCreate( id = " 
	  + System.identityHashCode(this) + ", PK = " +
	  id  + ")");

      this.setId(id);
      this.setProvider_name(pro_nm);
      this.setPassword(pwd);
	  this.setCommunication1(comm1);
	  this.setCommunicationType1(commType1);
      this.setEmail(email);
	  this.setAddress(addr);
      this.setCity(city);
      this.setState(state);
	  this.setZip(zip);
      this.setPhone(phone);
      this.setStatus(status);
      
      
      return null;  // See 9.4.2 of the EJB 1.1 specification
    }

  public void ejbPostCreate(String id, String pro_nm, String pwd,  String comm1, String commType1,
      String email, String addr, String city, String state, String zip, String phone, String status)
  {
    System.out.println("provider.ejbPostCreate (" + id + ")");
  }
}



