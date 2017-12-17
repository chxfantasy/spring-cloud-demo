package spring.cloud.client.uitils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Harry on 17/3/23.
 */
public class RandomGenerator {
    private static String charCollection = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";
    private static Random random = new Random();

    public static String generateRandomString( int len ) {
        if ( len <= 0 ) return "";
        StringBuilder sb = new StringBuilder(len);
        int size = charCollection.length();
        for ( int i = 0 ; i < len; i++ ) {
            int index = random.nextInt(size);
            sb.append( charCollection.charAt(index) );
        }
        return sb.toString();
    }
    
    public static String generateVarifyCode() {
        return generateNumerString(4);
	}

    public static String generateNumerString(int len) {
        if ( len<=0 ) return "";
        String numberCollection = "1234567890";
        StringBuilder sb = new StringBuilder(len);
        int size = numberCollection.length();
        for ( int i = 0 ; i < len; i++ ) {
            int index = random.nextInt(size);
            sb.append( numberCollection.charAt(index) );
        }
        return sb.toString();
    }
    
    /**
     * 需要保证cookie不能重复
     * @return
     */
    public static String getCookieString() {
        String tmp = UUID.randomUUID().toString().replace("-","");
        return tmp+generateRandomString(32);
    }

}
