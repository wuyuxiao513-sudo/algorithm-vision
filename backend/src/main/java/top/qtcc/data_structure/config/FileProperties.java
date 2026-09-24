package top.qtcc.data_structure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 文件上传配置属性类
 * 读取application.yml中的文件上传相关配置
 * 
 * @author qiutuan
 * @date 2024/11/02
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {

    /**
     * 文件上传路径
     * 支持Windows和Linux系统路径
     */
    private String path;

    /**
     * 文件访问地址前缀
     * 支持自动获取本机IP或配置固定地址
     */
    private String accessUrl;

    /**
     * 获取标准化的文件上传路径
     * 确保路径分隔符正确，并创建目录
     * 
     * @return 标准化的文件上传路径
     */
    public String getNormalizedPath() {
        // 标准化路径分隔符
        String normalizedPath = path.replace("\\", "/");
        
        // 确保路径以分隔符结尾
        if (!normalizedPath.endsWith("/")) {
            normalizedPath += "/";
        }
        
        // 创建目录
        File directory = new File(normalizedPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("无法创建文件上传目录: " + normalizedPath);
            }
        }
        
        return normalizedPath;
    }

    /**
     * 获取标准化的文件访问地址
     * 确保地址格式正确
     * 
     * @return 标准化的文件访问地址
     */
    public String getNormalizedAccessUrl() {
        // 确保访问地址以斜杠结尾
        if (!accessUrl.endsWith("/")) {
            return accessUrl + "/";
        }
        return accessUrl;
    }
}