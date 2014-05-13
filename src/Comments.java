package icoupon;

import javax.ejb.*;

public interface Comments extends EJBLocalObject {
    public String id();
    public String customer_id();
    public String coupon_id();
    public String content();

    public CommentsTO getCommentsData();
}


