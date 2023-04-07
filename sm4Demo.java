import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.util.Date;

public class sm4Demo {
    final static String key = "sm4demo123456789";
    //指明加密算法和秘钥
    static SymmetricCrypto sm4 = new SymmetricCrypto ("SM4/ECB/PKCS5Padding", key.getBytes());

    //加密为16进制，也可以加密成base64/字节数组
    public static String encryptSm4(String plaintext) {
        return sm4.encryptHex(plaintext);
    }
    //解密
    public static String decryptSm4(String ciphertext) {
        return sm4.decryptStr(ciphertext);
    }
    public static void main(String[] args) {
        String content = "hello sm4";
        Long time0 = System.currentTimeMillis ();
        String plain = encryptSm4(content);
        String cipher = decryptSm4(plain);
        Long time1 = System.currentTimeMillis ();
        System.out.println (time1-time0);
        System.out.println(plain + "\n" + cipher);
    }
}
