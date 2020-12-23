创建证书语句：
keytool -storepass 123456 -genkeypair -keyalg RSA -keysize 1024 -sigalg SHA1withRSA -validity 3650 -alias test -keystore test.keystore -dname "CN=www.inossem.com, OU=inossem, O=inossem, L=SY, ST=SY, C=CN"
证书信息：
证书密码：123456
别名：test
别名密码：123456