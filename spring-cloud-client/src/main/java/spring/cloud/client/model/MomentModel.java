package spring.cloud.client.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Harry on 16/12/2017.
 */
public class MomentModel implements Serializable {

    private static final long serialVersionUID = -4265065943301487413L;

    private Long id;

    private String userId;

    private AccountModel userModel;

    private String content;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccountModel getUserModel() {
        return userModel;
    }

    public void setUserModel(AccountModel userModel) {
        this.userModel = userModel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
