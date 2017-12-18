package spring.cloud.client.model;

import java.io.Serializable;

/**
 * Created by Harry on 13/12/2017.
 */
public class AccountModel implements Serializable {

    private static final long serialVersionUID = 1409585292972743405L;

    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
