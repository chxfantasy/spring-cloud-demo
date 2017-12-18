package spring.cloud.biz.dataaccess.dataobject;

import spring.cloud.client.model.CommentModel;

import java.io.Serializable;

public class CommentDo extends CommentModel {

    private static final long serialVersionUID = 4716466311760136418L;

    private Boolean isDeleted;

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}