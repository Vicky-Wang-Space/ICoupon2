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

abstract public class CouponBean implements EntityBean {
  final static boolean VERBOSE = true;
  private EntityContext ctx;

  
  public void setEntityContext(EntityContext ctx) {
    System.out.println("setEntityContext called (" + id() + ")");
    this.ctx = ctx;
  }

  public void unsetEntityContext() {
    this.ctx = null;
    System.out.println("CouponBean.unsetEntityContext (" + id() + ")");

  }

  /**
   * container managed fields
   */
  abstract public String getId();
  abstract public void setId(String val);  

  abstract public String getCategory_id();
  abstract public void setCategory_id(String val);
   
  abstract public String getProvider_id();
  abstract public void setProvider_id(String val);
     
      
  abstract public String getName();
  abstract public void setName(String val);

  abstract public String getStore_name();
  abstract public void setStore_name(String val);

  abstract public String getExpiration_date();
  abstract public void setExpiration_date(String val);

  abstract public String getZip_code();
  abstract public void setZip_code(String val);
  
  abstract public String getOriginal_price();
  abstract public void setOriginal_price(String val);
  
  abstract public String getDiscount();
  abstract public void setDiscount(String val);

  /* Persistent relationships
   * This defines the n-to-1 relationships
   * between  Coupon and Category
   */
  abstract public Category getCategory();
  abstract public void setCategory(Category cat);

 //--------------------------------------------------------------------------------------
  /* Persistent relationships
   * This defines the n-to-1 relationships
   * between  Coupon and Provider
   */
  abstract public Provider1 getProvider();
  abstract public void setProvider(Provider1 pro);
//---------------------------------------------------------------------------------------

  /* Persistent relationships
   * This defines the 1-to-n relationships 
   * between  Coupon and Comments
   */
  abstract public Collection getComments();
  abstract public void setComments(Collection val);

  
  //method to create Data Transfer Object for Coupon Bean
  public CouponTO getCouponData()
  {
    CouponTO cpn = new CouponTO();
    cpn.id = getId();
    cpn.category_id = getCategory_id();
    cpn.provider_id = getProvider_id();
    cpn.name = getName();
    cpn.store_name =  getStore_name();
    cpn.exp_date = getExpiration_date();
    cpn.zip_code = getZip_code();
    cpn.ori_price = getOriginal_price();
    cpn.discount = getDiscount();
    return cpn;
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
  public String category_id() {
     System.out.println("CouponBean.category_id (" + id() + ")");
    return getCategory_id();
  }
  public String provider_id(){
     System.out.println("CouponBean.provider_id (" + id() + ")");
    return getProvider_id();
  }
  public String store_name() {
    System.out.println("CouponBean.store_name (" + id() + ")");
    return getStore_name();
  }
  public String name() {
    System.out.println("CouponBean.lastname (" + id() + ")");
    return getName();
  }

  public String expiration_date() {
    System.out.println("CouponBean.expiration_date (" + id() + ")");
    return getExpiration_date();
  }
  public String zip_code() {
    System.out.println("CouponBean.zip_code (" + id() + ")");
    return getZip_code();
  }
  public String original_price() {
    System.out.println("CouponBean.original_price (" + id() + ")");
    return getOriginal_price();
  }
  public String discount() {
    System.out.println("CouponBean.discount (" + id() + ")");
    return getDiscount();
  }
  public void ejbActivate() {
    System.out.println("icoupon.ejbActivate (" + id() + ")");
  }
  public void ejbPassivate() {
    System.out.println("icoupon.ejbPassivate (" + id() + ")");
  }

  public void ejbLoad() {
    System.out.println("icoupon.ejbLoad (" + id() +  ")");
  }
  public void ejbStore() {
    System.out.println("icoupon.ejbStore (" + id() + ")");
  }
  public void ejbRemove()
    throws RemoveException
    {
      System.out.println("AccountfallBean.ejbRemove (" + id() + ")");
    }

  public String ejbCreate(String id, String cat_id, String prov_id,
      String name, String str_name, String exp_date, String zip_code,
      String ori_price, String discount)
    throws CreateException
    {
      System.out.println("CouponBean.ejbCreate( id = " 
	  + System.identityHashCode(this) + ", PK = " +
	  id + ", " + "name =  " + name + ")");

      this.setId(id);
  //    this.setCategory_id(cat_id);
  //    this.setProvider_id(prov_id);
      this.setName(name);
      this.setStore_name(str_name);
      this.setExpiration_date(exp_date);
      this.setZip_code(zip_code);
      this.setOriginal_price(ori_price);
      this.setDiscount(discount);

      return null;  // See 9.4.2 of the EJB 1.1 specification
    }

  public void ejbPostCreate(String id, String cat_id, String prov_id,
	      String name, String str_name, String exp_date, String zip_code,
	      String ori_price, String discoun)
  {
    try{
      InitialContext i = new InitialContext();
      ProviderHome prov_home = (ProviderHome)i.lookup("ProviderBean");
      CategoryHome cat_home = (CategoryHome)i.lookup("CategoryBean");
      setProvider(prov_home.findByPrimaryKey(prov_id));
      setCategory(cat_home.findByPrimaryKey(cat_id));
    }catch (Exception e){
      System.out.println("icoupon.ejbPostCreate (" + id + ")");
    }
  }
}



