package spring.cloud.client.uitils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

public class ParamUtils {
	
	public static int doubleToInt(double value, int rate) {
        return new BigDecimal(value*rate).setScale(0, BigDecimal.ROUND_HALF_UP).toBigInteger().intValue();
    }
	
	public static String readBodyFromRequest( HttpServletRequest request ) {
        if ( null == request ) return "";
        StringBuilder sBuiler = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line = null;
            while((line = reader.readLine()) != null){
                sBuiler.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sBuiler.toString();
    }
	
}
