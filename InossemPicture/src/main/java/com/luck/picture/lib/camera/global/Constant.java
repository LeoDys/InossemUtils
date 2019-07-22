package com.luck.picture.lib.camera.global;

import com.luck.picture.lib.camera.utils.FileUtils;

import java.io.File;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/10
 * Desc	        ${常量}
 */
public class Constant {
    public static final String ROOT_DIR_NAME = "Inossem_Camera";
    public static final String BASE_DIR = ROOT_DIR_NAME + File.separator;//Inossem_Camera/
    public static final String DIR_ROOT = FileUtils.getRootPath() + File.separator + Constant.BASE_DIR;//文件夹根目录 /storage/emulated/0/WildmaIDCardCamera/
}