package wu.justa.proxy;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JWTSetting {
	public static final String JWT_SECRET = "sdtuO3495783490";
	public static final SecretKey SecretKey ;
	
	static {
        byte[] stringKey = JWTSetting.JWT_SECRET.getBytes();  
        // JWT_SECRET will be on server setting on prod, better to add salt to avoid both developer and admin to easily get real key         
        SecretKey = new SecretKeySpec(stringKey, 0, stringKey.length, "AES");
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        
    }
}
