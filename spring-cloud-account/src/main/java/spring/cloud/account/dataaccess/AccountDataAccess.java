package spring.cloud.account.dataaccess;

import spring.cloud.account.dataaccess.dataobject.AccountDo;

import java.util.Optional;

/**
 * Created by Harry on 2017/6/19.
 */
public interface AccountDataAccess {

    int insert(AccountDo record);

    Optional<AccountDo> selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(AccountDo record);

}
