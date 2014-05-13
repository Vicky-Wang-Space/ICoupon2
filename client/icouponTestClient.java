package icoupon;

import java.rmi.RemoteException;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class icouponTestClient 
{
  private CouponHome couponHome;
  private Coupon coupon;
  private String url =  "t3://bea3.cs.txstate.edu:7075";
  private String accountId = "xw1004";
  
  
  public icouponTestClient() throws NamingException{

      couponHome = lookupHome();
     // try {
//	lookUp = facHome.create();
 //     }
  //    catch (Exception e)
   //   { System.out.println(e);}

  }

  private CouponHome lookupHome() throws NamingException
  {
    Context ctx = getInitialContext();

    try {
      Object home = (CouponHome) ctx.lookup("CouponBean"); 
      return (CouponHome) PortableRemoteObject.narrow(home, CouponHome.class);
    }  catch (NamingException ne) {
      log("The client was unable to lookup the EJBHome.  Please make sure " +
	  "that you have deployed the ejb with the JNDI name " +
	  ".Home on the WebLogic server at ");
      throw ne;
    }
  }

  private Context getInitialContext() throws NamingException {

    try {
      // Get an InitialContext
      Properties h = new Properties();
      h.put(Context.INITIAL_CONTEXT_FACTORY,
	  "weblogic.jndi.WLInitialContextFactory");
      h.put(Context.PROVIDER_URL, url);
      return new InitialContext(h);
    } catch (NamingException ne) {
      log("We were unable to get a connection to the WebLogic server at ");
      log("Please make sure that the server is running.");
      throw ne;
    }
  }

  public static void main(String[] args) {
    System.out.println("\nBeginning icoupon.Client...\n");

    icouponTestClient client = null;

    try{
      client = new icouponTestClient();
    } catch (NamingException ne) {
      log("Unable to look up the beans home: " + ne.getMessage());
      System.exit(1);
    }

    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      System.out.println( "\nWelcome to Coupon  Lookup System...");
      for (;;) {
	String command;
	System.out.println( "\nEnter:\n's' to search \n'a' to add \n'd' to delete \n'exit' to terminate: ");
	try 
	{
	  command = input.readLine();
	  if (command.equals("exit") ) {
	    break;
	  }
	  if (command.equals("S") || command.equals("s") ) {
	    System.out.println( "Enter: \n'id' to search coupon by ID" );

	    command = input.readLine();
	    if (command.equals("id")) {
	      System.out.println( "Enter number:");
	      command = input.readLine();
	      client.find_by_id(command);
	    }
	    else if (command.equals("email")){
	      System.out.println( "Enter email:");
	      command = input.readLine();
	      client.find_by_email(command);
	    }
	    else if (command.equals("last name")) {
	      System.out.println( "Enter last name:");
	      command = input.readLine();
	      client.find_by_last_name(command);
	    }
	    else if (command.equals("all")) {
	      client.find_all();
	    }
	  }
	  else if  (command.equals("A") || command.equals("a") ) {
	    
//	    String input_email;
//	    String input_first_name;
//	    String input_last_name;
//	    String input_room_num;
//	    String input_phone_num;
/*
	    System.out.println( "Required fields...");
	    System.out.println( "Enter Faculty ID:");
	    command = input.readLine();
	    input_id = command;	      

	    System.out.println( "Enter first name:");
	    input_first_name = input.readLine();

	    System.out.println( "Enter last name:");
	    input_last_name = input.readLine();

	    System.out.println( "Enter email:");
	    input_email = input.readLine();

	    System.out.println( "Enter room number:");
	    input_room_num = input.readLine();

	    System.out.println( "Enter phone number:");
	    input_phone_num = input.readLine();
	    */

	    client.add_new("", "1", "1", "All men's haircut 45% Off!", 
		"Jojo's Salon at Round Rock", "2010-12-31", "78721", "", "0.45");
	  }
	  else if (command.equals("D") || command.equals("d") ) {
	    System.out.println( "Enter faculty number to delete:");
	    command = input.readLine();
	    client.delete_by_id(command);
	  }
	} 
	catch (Exception e)
	{ System.out.println(e);}
      }

    } catch (Exception e) {
      log("There was an exception while looking up .");
      log("This indicates that there was a problem communicating with the server: "+e);
    }

    System.out.println("\nEnd facLookUp.Client...\n");
  }
  private void find_by_id(String input_id) {

    try {
      Coupon coupon = couponHome.findByPrimaryKey(input_id);      
      System.out.println("Found!");
      System.out.println("ID: " + coupon.id());
      System.out.println("Name: " + coupon.name());
       
    } catch (ObjectNotFoundException nobe) { 
      System.out.println("This coupon does not exist in the system!"); 
    } catch (Exception e) {
      System.out.println("Client find by ID " + e);
    }
  }

  private void find_all() {
    /*
    try {
        xw1004FacLookUp facLookUp =
	          (xw1004FacLookUp)PortableRemoteObject.narrow(facHome.create(), xw1004FacLookUp.class);
      Collection col = facLookUp.findAll();
      for(Iterator it = col.iterator(); it.hasNext();) {
	display_faculty((Faculty)(it.next()));
      }
    } catch (Exception e)
    { e.printStackTrace();}
     */
  }
  private void find_by_email(String input_email) {
   /* try {
      xw1004FacLookUp facLookUp =
	(xw1004FacLookUp)PortableRemoteObject.narrow(facHome.create(), xw1004FacLookUp.class);

      Faculty fac = facLookUp.findByEmail(input_email);
      display_faculty(fac);

    } catch (Exception e)
    { System.out.println("Client find by Email " + e);}
    */

  }

  // find by last name
  private void find_by_last_name(String input_last_name) {
//    try {
//      xw1004FacLookUp facLookUp =
//	(xw1004FacLookUp)PortableRemoteObject.narrow(facHome.create(), xw1004FacLookUp.class);
//      Collection col = facLookUp.findByLastName(input_last_name);
//      for(Iterator it = col.iterator(); it.hasNext();) {
//	display_faculty((Faculty)(it.next()));
//      }
//    } catch (Exception e)
//    { e.printStackTrace();}
//
  }
  private void add_new(String id, String cat_id, 
      String prov_id, String name, String store_name,
      String exp_date, String zip, String ori_prc, String disc) {
    try {
      Coupon coupon = couponHome.create( id, cat_id, prov_id, name, store_name,
		      exp_date, zip, ori_prc, disc);
      log("\n Add Successful!");

//      System.out.println("ID: "+coupon.id());
      System.out.println("Name: " +coupon.name());
      
    } catch (Exception e)
    { System.out.println(e);}
  }

//  private void display_faculty(Faculty fac){
//    try {
//
//      System.out.println("\n-----faculty info-----");
//      System.out.println("// ID: " + fac.getPrimaryKey() );
//      System.out.println("// email: " + fac.email() );
//      System.out.println("// first name: " + fac.firstName() );
//      System.out.println("// last name: " + fac.lastName() );
//      System.out.println("// room number: " + fac.roomNum() );
//      System.out.println("// phone number: " + fac.phoneNum() );
//    }
//    catch (Exception e)
//    { e.printStackTrace();
//      System.out.println("display faculty: " + e.getMessage());}
//  }

  private void delete_by_id(String input_id) {
//    try {
//      xw1004FacLookUp facLookUp =
//	(xw1004FacLookUp)PortableRemoteObject.narrow(facHome.create(), xw1004FacLookUp.class);
//
//      facLookUp.deleteFac(input_id);
//    }
//    catch (Exception e)
//    { System.out.println(e);}
  }
//
  private static void log(String s) {
    System.out.println(s);
  }
}

