package com.kailangye.langhuaaiagent.constant;

/**
 * 文件保存目录
 */
public interface FileConstant {

    /**
     * System.getProperty("user.dir") - 这是获取系统属性的方法调用
     * "user.dir" 表示当前工作目录（用户运行Java程序时的目录）
     */
    String FILE_SAVE_DIR = System.getProperty("user.dir") + "/tmp";
}
