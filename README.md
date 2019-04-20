# phpRijndale256JavaImpl
用java语言实现php的Mcrypt_Rijndale_256加解密。比如php的mcrypt加解密方法如下
```
 <?php 
 function encrypt($encrypt,$key="") {
  $iv = mcrypt_create_iv ( mcrypt_get_iv_size ( MCRYPT_RIJNDAEL_256, MCRYPT_MODE_ECB ), MCRYPT_RAND );
  $passcrypt = mcrypt_encrypt ( MCRYPT_RIJNDAEL_256, $key, $encrypt, MCRYPT_MODE_ECB, $iv );
  $encode = base64_encode ( $passcrypt );
  return $encode;
 }
  function decrypt($encrypt,$key="") {
  $iv = mcrypt_create_iv ( mcrypt_get_iv_size ( MCRYPT_RIJNDAEL_256, MCRYPT_MODE_ECB ), MCRYPT_RAND );
  $encrypt=base64_decode($encrypt);
  $passcrypt = mcrypt_decrypt ( MCRYPT_RIJNDAEL_256, $key, $encrypt, MCRYPT_MODE_ECB, $iv );
  return $passcrypt;
 }
 ?>
```
受[stackflow帖子](https://stackoverflow.com/questions/18101545/encryption-in-android-equivalent-to-phps-mcrypt-rijndael-256/18114859#18114859)启发，实现了java版的Rijndale 256加解密
