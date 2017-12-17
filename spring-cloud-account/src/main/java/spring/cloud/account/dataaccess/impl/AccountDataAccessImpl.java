package spring.cloud.account.dataaccess.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;
import spring.cloud.account.config.GlobalCacheHelper;
import spring.cloud.account.dataaccess.AccountDataAccess;
import spring.cloud.account.dataaccess.dataobject.AccountDo;
import spring.cloud.account.dataaccess.mapper.AccountDoMapper;
import spring.cloud.demo.cache.CacheService;

import java.util.Optional;

/**
 * Created by Harry on 2017/6/19.
 */
@Repository("userDataAccess")
@RefreshScope
public class AccountDataAccessImpl implements AccountDataAccess {

    @Autowired private CacheService cacheService;
    @Autowired private AccountDoMapper accountDoMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger( AccountDataAccessImpl.class );
    private static final String accountCachePre = "spring#cloud#demo#account#userId#";
    private static final int cache_time = 30*60;    //30min

    private String getCacheKeyByUserId( String userId ) {
        return accountCachePre + userId;
    }

    private void deleteCacheByUserId(String userId) {
        String cacheKey = this.getCacheKeyByUserId( userId );
        try {
            this.cacheService.deleteObjectByKey( cacheKey );
        } catch (Exception e) {
            e.printStackTrace();
            GlobalCacheHelper.putDeleteKeys( cacheKey );
        }
    }

    @Override
    public int insert(AccountDo record) {
        return this.accountDoMapper.insert(record);
    }

    @Override
    public Optional<AccountDo> selectByPrimaryKey(String userId) {
        if (Strings.isNullOrEmpty(userId) ) {
            return Optional.empty();
        }
        String cacheKey = this.getCacheKeyByUserId(userId);
        AccountDo accountDo = null;
        try {
            accountDo = (AccountDo) this.cacheService.getObject( cacheKey );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ( null!=accountDo ) return Optional.of( accountDo );

        accountDo = this.accountDoMapper.selectByPrimaryKey(userId);
        if ( null!=accountDo ) {
            try {
                this.cacheService.putObject( cacheKey, accountDo, cache_time );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Optional.ofNullable( accountDo );
    }

    @Override
    public int updateByPrimaryKeySelective(AccountDo record) {
        int size = this.accountDoMapper.updateByPrimaryKeySelective( record );
        if ( size > 0 ) {
            this.deleteCacheByUserId( record.getUserId() );
        }
        return size;
    }
}
