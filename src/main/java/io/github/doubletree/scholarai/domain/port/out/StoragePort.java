package io.github.doubletree.scholarai.domain.port.out;

import java.io.InputStream;
import java.io.IOException;

public interface StoragePort {
    /**
     * 保存文件
     * @param inputStream 文件流
     * @param fileName 文件名
     * @return 存储后的唯一标识或路径
     */
    String save(InputStream inputStream, String fileName) throws IOException;

    /**
     * 读取文件
     * @param storedFileName 存储的文件名
     * @return 文件输入流
     */
    InputStream load(String storedFileName) throws IOException;
}
