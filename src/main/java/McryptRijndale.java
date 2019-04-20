import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * 用java实现php的Rijndael_256加解密
 * @author: demo2doc
 * mail: demo2doc@dao-hang.cn
 * time: 2019-04-20 10:06
 * description:
 */
public class McryptRijndale {

    /**
     * 加密
     * @param giveKey
     * @param rawData
     * @return
     */
    public static String encrypt(String giveKey, String rawData) throws Exception {

        PaddedBufferedBlockCipher pbbc = buildCipher();

        KeyParameter key = buildKeyParameter(giveKey);

        pbbc.init(true, key);

        byte[] plainText = rawData.getBytes(Charset.forName("UTF8"));

        byte[] cipherText = new byte[pbbc.getOutputSize(plainText.length)];

        int offset = 0;

        offset += pbbc.processBytes(plainText, 0, plainText.length, cipherText, offset);
        pbbc.doFinal(cipherText, offset);

        return Base64.getEncoder().encodeToString(cipherText);
    }

    private static KeyParameter buildKeyParameter(String giveKey) {
        byte[] keyBytes = giveKey.getBytes(Charset.forName("UTF8"));

        return generateKey(calculateKeySize(keyBytes), keyBytes);
    }

    private static PaddedBufferedBlockCipher buildCipher() {

        BlockCipher rijndael = new RijndaelEngine(256);

        ZeroBytePadding c = new ZeroBytePadding();

        return new PaddedBufferedBlockCipher(rijndael, c);
    }

    /**
     * 解密
     *
     * @param giveKey
     * @param rawData
     * @return
     */
    public static String decrypt(String giveKey, String ciphertextBase64Encoded) throws Exception {

        PaddedBufferedBlockCipher pbbc = buildCipher();

        KeyParameter key = buildKeyParameter(giveKey);

        pbbc.init(false, key);

        byte[] ciphertext = Base64.getDecoder().decode(ciphertextBase64Encoded);
        byte[] decrypted = new byte[pbbc.getOutputSize(ciphertext.length)];
        int offset = 0;
        offset += pbbc.processBytes(ciphertext, 0, ciphertext.length, decrypted, offset);
        pbbc.doFinal(decrypted, offset);

        // 按照php的解密模式，解密后的字符串不包含空的结束符，java中有，需要去除
        return new String(decrypted, Charset.forName("UTF8")).replaceAll("\\x00+$", "");
    }

    private static KeyParameter generateKey(int keysize, byte[] givenKey) {

        byte[] keyData = new byte[keysize / Byte.SIZE];
        System.arraycopy(givenKey, 0, keyData, 0, Math.min(givenKey.length, keyData.length));
        KeyParameter key = new KeyParameter(keyData);

        return key;
    }

    /**
     * 计算key的大小
     * @param givenKey
     * @return
     */
    private static int calculateKeySize(byte[] givenKey) {
        final int keysize;
        if (givenKey.length <= 128 / Byte.SIZE) {
            keysize = 128;
        } else if (givenKey.length <= 192 / Byte.SIZE) {
            keysize = 192;
        } else {
            keysize = 256;
        }

        return keysize;

    }


}
