import org.junit.Assert;
import org.junit.Test;

class McryptRijndaleTest {

    @Test
    public void encryptAndDecryptTest() throws Exception{
        String key = "zox6UjIQL3ofKpto";
        String rawData = "sM4AOVdWfPE4DxkXGEs8VO38bSJTiMY9uk5j-ysTqGZu7OYGUhmE1KYlmUEoCoQWWNCZcowCHMG187h7ft3-FA";
        String encryptText = McryptRijndale.encrypt(key, rawData);
        Assert.assertEquals( McryptRijndale.decrypt(key, encryptText),rawData);
    }
}