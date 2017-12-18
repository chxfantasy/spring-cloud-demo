package spring.cloud.biz.dataaccess.dataobject;

import spring.cloud.client.model.MomentModel;

public class MomentDo extends MomentModel {

    private static final long serialVersionUID = 1404709910095824920L;

    private Boolean isDeleted;

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}