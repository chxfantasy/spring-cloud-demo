package spring.cloud.account.dataaccess.dataobject;

import spring.cloud.client.model.AccountModel;

import java.io.Serializable;
import java.util.Date;

public class AccountDo extends AccountModel implements Serializable {

    private static final long serialVersionUID = 12345;

    private String password;

    private Date gmtCreated;

    private Date gmtModified;

    private Boolean isDeleted;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}