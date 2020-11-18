package com.inossem.encrypt.en_decrypt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inossem.BaseActivity;
import com.inossem.R;
import com.inossem.util.Utils;

public class DecryptActivity extends BaseActivity {
    /**
     * 数据
     */
    String data = "123abcABC壹贰叁";
    /**
     * 盐值
     */
    String salt = "salt";
    /**
     * 密钥 AES key需要为16位
     */
    String key = "keykeykey";
    String keyDES = "keykeyke";
    String keyAES = "keykeykeykeykeyk";
    /**
     * 向量 AES iv需要为16位
     */
    String iv = "iviviviv";
    String ivDES = "iviviviv";
    String ivAES = "iviviviviviviviv";
    TextView description;
    TextView show;
    private byte[] bytesDES;

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_decrypt);
        description = findViewById(R.id.description);
        show = findViewById(R.id.show);
        LinearLayout buttonLayout = findViewById(R.id.buttonLayout);
//        Utils.createButtons(DecryptActivity.this, buttonLayout, 20, new Utils.ButtonListener() {
//            @Override
//            public void onCreated(Button button, int position) {
//                switch (position) {
//                    case 0:
//                        button.setText("MD5 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("MD5:\n" + EncryptUtils.encryptMD5ToString(data));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 1:
//                        button.setText("MD5加盐加密 盐值(salt) 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("MD5加盐加密:\n" + EncryptUtils.encryptMD5ToString(data, salt));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 2:
//                        button.setText("SHA1加密 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("SHA1加密:\n" + EncryptUtils.encryptSHA1ToString(data));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 3:
//                        button.setText("SHA224加密 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("SHA224加密:\n" + EncryptUtils.encryptSHA224ToString(data));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 4:
//                        button.setText("SHA256加密 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("SHA256加密:\n" + EncryptUtils.encryptSHA256ToString(data));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 5:
//                        button.setText("SHA384加密 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("SHA384加密:\n" + EncryptUtils.encryptSHA384ToString(data));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 6:
//                        button.setText("SHA512加密 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("SHA512加密:\n" + EncryptUtils.encryptSHA512ToString(data));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 7:
//                        button.setText("HmacMD5加密 密钥(key) 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("HmacMD5加密:\n" + EncryptUtils.encryptHmacMD5ToString(data, key));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 8:
//                        button.setText("HmacSHA1加密 密钥(key) 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("HmacSHA1加密:\n" + EncryptUtils.encryptHmacSHA1ToString(data, key));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 9:
//                        button.setText("HmacSHA224加密 密钥(key) 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("HmacSHA224加密:\n" + EncryptUtils.encryptHmacSHA224ToString(data, key));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 10:
//                        button.setText("HmacSHA256加密 密钥(key) 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("HmacSHA256加密:\n" + EncryptUtils.encryptHmacSHA256ToString(data, key));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 11:
//                        button.setText("HmacSHA384加密 密钥(key) 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("HmacSHA384加密:\n" + EncryptUtils.encryptHmacSHA384ToString(data, key));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 12:
//                        button.setText("HmacSHA512加密 密钥(key) 数据(123abcABC壹贰叁)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    show.setText("HmacSHA512加密:\n" + EncryptUtils.encryptHmacSHA512ToString(data, key));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 13:
//                        button.setText("DES加密 密钥(keykeyke) 数据(123abcABC壹贰叁) 向量(iviviviv)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    String s = "";
//                                    bytesDES = EncryptUtils.encryptDES2Base64(data.getBytes(), keyDES.getBytes(), "DES/CBC/PKCS5Padding", ivDES.getBytes());
//                                    s += "DES加密:\n";
//                                    for (byte bytesDE : bytesDES) {
//                                        s += (bytesDE + ",");
//                                    }
//                                    show.setText(s);
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 14:
//                        button.setText("DES解密 密钥(keykeyke) 数据(123abcABC壹贰叁) 向量(iviviviv)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    byte[] bytes = EncryptUtils.decryptBase64DES(bytesDES, keyDES.getBytes(), "DES/CBC/PKCS5Padding", ivDES.getBytes());
//                                    show.setText("DES解密:\n" + new String(bytes));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 15:
//                        button.setText("AES加密 密钥(keykeykeykeykeyk) 数据(123abcABC壹贰叁) 向量(iviviviviviviviv)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    StringBuilder s = new StringBuilder();
//                                    bytesDES = EncryptUtils.encryptAES2Base64(data.getBytes(), keyAES.getBytes(), "AES/CBC/PKCS5Padding", ivAES.getBytes());
//                                    s.append("AES加密:\n");
//                                    for (byte bytesDE : bytesDES) {
//                                        s.append(bytesDE).append(",");
//                                    }
//                                    show.setText(s.toString());
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                    case 16:
//                        button.setText("AES解密 密钥(keykeykeykeykeyk) 数据(123abcABC壹贰叁) 向量(iviviviviviviviv)");
//                        button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    byte[] bytes = EncryptUtils.decryptBase64AES(bytesDES, keyAES.getBytes(), "AES/CBC/PKCS5Padding", ivAES.getBytes());
//                                    show.setText("AES解密:\n" + new String(bytes));
//                                } catch (Throwable throwable) {
//                                    throwable.printStackTrace();
//                                }
//                            }
//                        });
//                        break;
//                }
//            }
//        });
    }
}