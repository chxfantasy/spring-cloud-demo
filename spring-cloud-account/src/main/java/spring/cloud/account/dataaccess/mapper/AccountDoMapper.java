package spring.cloud.account.dataaccess.mapper;

import org.apache.ibatis.annotations.Select;
import spring.cloud.account.dataaccess.dataobject.AccountDo;

import java.util.List;

public interface AccountDoMapper {
    int deleteByPrimaryKey(String userId);

    int insert(AccountDo record);

    int insertSelective(AccountDo record);

    AccountDo selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(AccountDo record);

    int updateByPrimaryKey(AccountDo record);

    @Select("select * from account where is_deleted = 0")
    List<AccountDo> listAllUsers();
}