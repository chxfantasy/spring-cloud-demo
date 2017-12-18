package spring.cloud.account.dataaccess.dataobject;

import spring.cloud.client.model.AccountModel;

import java.util.Date;

/**
 * AccountModel should implements Serializable, otherwise userId and userName will not be serialized
 */
public class AccountDo extends AccountModel {

    private static final long serialVersionUID = 8400094943305435805L;

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