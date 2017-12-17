package spring.cloud.gateway.config;

/**
 * Created by wangxiaohu on 2017/6/19.
 */
public class GlobalConstants {

    public static final String ACCOUNT_SERVICE_NAME = "spring.cloud.account";

    public static final String BIZ_SERVICE_NAME = "spring.cloud.biz";

    public final static int LOGIN_TOKEN_EXPIRE = 60*60;//登陆的token保持1h

    public final static String LOGIN_TOKEN_KEY = "demo-loginToken";

    public static final int MAX_LIST_SIZE = 100000;
}
