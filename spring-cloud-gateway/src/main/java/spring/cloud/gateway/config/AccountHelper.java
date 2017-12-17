package spring.cloud.gateway.config;

import spring.cloud.client.model.AccountModel;

/**
 * Created by Harry on 14/12/2017.
 */
public class AccountHelper {

    private static ThreadLocal<String> accountHolder = new ThreadLocal<>();

    public static void setUserId( String userId ) {
        accountHolder.set( userId );
    }

    public static String getUserId() {
        return accountHolder.get();
    }

    public static void clear() {
        accountHolder.remove();
    }
}
