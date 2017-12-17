package spring.cloud.client.uitils;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.common.base.Strings;

/**
 * Created by Harry on 17/3/17.
 */
public class EncryptUtil {
    public static boolean initialized = false;

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};
    
    private static SecureRandom random = null;
    //初始化 SecureRandom，为了线程安全
    static {
    	try {
			random = SecureRandom.getInstance("SHA1PRNG");
			random.nextInt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(1);
		}
    }

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5(String toBeSigned) {
        if ( Strings.isNullOrEmpty(toBeSigned) ) return "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5SignBytes = md5.digest(toBeSigned.getBytes("utf-8"));
            String signResult = EncryptUtil.byteArrayToHexString(md5SignBytes);
            return signResult.toUpperCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sha1(String toBeSigned) {
        if (Strings.isNullOrEmpty(toBeSigned)) {
            return "";
        }
        byte messageDigest[] = DigestUtils.sha1( toBeSigned.getBytes() );
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }

    public static String aes_encrypt(String sKey, String ivParameter, String sSrc) throws Exception {
        if ( Strings.isNullOrEmpty(sKey) || Strings.isNullOrEmpty(ivParameter) || Strings.isNullOrEmpty(sSrc) )
            throw new Exception("param is required");
        initialize();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        String result = byteArrayToHexString(encrypted);
        return result;
    }
    
    public static String aes_encrypt(String sKey, String sSrc) throws Exception {
        if ( Strings.isNullOrEmpty(sKey) || Strings.isNullOrEmpty(sSrc) )
            throw new Exception("param is required");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        String result = byteArrayToHexString(encrypted);
        return result;
    }
    
 // 解密
    public static String aes_decrypt(byte[] sKey, byte[] ivParameter, byte[] sSrc) throws Exception {
        if ( null==sKey || null==ivParameter || null==sSrc )
            throw new Exception("param is required");
        initialize();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        Key sKeySpec = new SecretKeySpec(sKey, "AES");
        AlgorithmParameters iv = generateIV(ivParameter);
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
        byte[] original = cipher.doFinal(sSrc);
        String originalString = new String(original,"utf-8");
        return originalString;
    }

    // 解密
    public static String aes_decrypt(String sKey, String sSrcInHex) throws Exception {
        if ( Strings.isNullOrEmpty(sKey ) || Strings.isNullOrEmpty(sSrcInHex) )
            throw new Exception("param is required");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        
        Key sKeySpec = new SecretKeySpec(sKey.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
        
        byte[] original = cipher.doFinal( EncryptUtil.parseHexStr2Byte(sSrcInHex) );
        String originalString = new String(original,"utf-8");
        return originalString;
    }

    private static void initialize() {
        if (initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    private static AlgorithmParameters generateIV(byte[] iv) throws Exception{
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }

    /**
     * 对输入的密码进行验证
     *
     * @param attemptedPassword
     *            待验证的密码
     * @param encryptedPassword
     *            密文
     * @param salt
     *            盐值
     * @return 是否验证成功
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 用相同的盐值对用户输入的密码进行加密
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }

    /**
     * 生成密文
     *
     * @param password
     *            明文密码
     * @param salt
     *            盐值
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String getEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
    	
        KeySpec spec = new PBEKeySpec(password.toCharArray(), parseHexStr2Byte(salt), 50000, 128*4);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] resultBytes = f.generateSecret(spec).getEncoded();
        return byteArrayToHexString( resultBytes );
    }

    /**
     * 通过提供加密的强随机数生成器 生成盐
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return byteArrayToHexString(salt);
    }
    
    public static String encryptUsingPublicKey(String base64PubKey, String toBeSigned) {
    	if ( Strings.isNullOrEmpty(toBeSigned) || Strings.isNullOrEmpty( base64PubKey ) ) {
			return "";
		}
    	initialize();
    	try {
    		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    		X509EncodedKeySpec keySpec = new X509EncodedKeySpec( Base64.getDecoder().decode(base64PubKey) );
        	PublicKey publicKey = keyFactory.generatePublic(keySpec);
        	
        	Cipher c1 =Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding","BC");
            c1.init(Cipher.ENCRYPT_MODE, publicKey );

            byte[] cipherText = c1.doFinal(toBeSigned.getBytes());
//            System.out.println( byteArrayToHexString(cipherText) );
            return Base64.getEncoder().encodeToString(cipherText);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException 
				| NoSuchPaddingException | InvalidKeyException 
				| IllegalBlockSizeException | BadPaddingException | NoSuchProviderException e) {
			e.printStackTrace();
		}
    	return "";
        
    }
    
    public static String decryptUsingPrivateKey(String base64PriKey, String toBeDecryptBase64) {
    	if ( Strings.isNullOrEmpty(toBeDecryptBase64) || Strings.isNullOrEmpty( base64PriKey ) ) {
			return "";
		}
    	initialize();
    	try {
    		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    		byte[] keyBytes = Base64.getDecoder().decode(base64PriKey);
    		System.out.println( keyBytes.length );
    		byte[] encrypedData = Base64.getDecoder().decode(toBeDecryptBase64);
    		System.out.println("encrypedData length:" + encrypedData.length);
    		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec( keyBytes );
    		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
    		Cipher c1 = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding","BC");
    		c1.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = c1.doFinal( encrypedData );
    		return new String( output );
		} catch (NoSuchAlgorithmException | InvalidKeySpecException 
				| NoSuchPaddingException | InvalidKeyException 
				| IllegalBlockSizeException | BadPaddingException | NoSuchProviderException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
}
