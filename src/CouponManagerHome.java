package icoupon;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface CouponManagerHome extends EJBHome {

       CouponManager create() throws RemoteException, CreateException;
}

