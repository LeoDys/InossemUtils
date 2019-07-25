package com.inossem_library.callback;

import java.io.File;
import java.util.List;

/**
 * library 解耦接口
 *
 * @author Leo E-mail:changwen.sun@inossem.com 2019/7/24 11:41
 * @version 1.0.0
 * @since JDK-1.8
 */

public interface LibraryLinstener {
    List<String> compressPicCallBack(List<File> files);
}
