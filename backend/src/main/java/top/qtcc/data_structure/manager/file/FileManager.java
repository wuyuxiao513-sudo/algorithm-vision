package top.qtcc.data_structure.manager.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.qtcc.data_structure.config.FileProperties;

import jakarta.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 文件管理器
 * 支持本地文件存储，兼容Windows和Linux系统
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Slf4j
@Component("fileManager")
public class FileManager {

    @Resource
    private FileProperties fileProperties;

    /**
     * 上传文件到本地存储
     *
     * @param filepath 文件相对路径
     * @param file 文件对象
     */
    public void putObject(String filepath, File file) {
        try {
            // 标准化文件路径，移除开头的斜杠
            String normalizedPath = StringUtils.stripStart(filepath, "/");
            
            // 获取完整的文件路径
            String uploadPath = fileProperties.getNormalizedPath();
            Path targetPath = Paths.get(uploadPath, normalizedPath);
            
            // 确保目标目录存在
            Path parentDir = targetPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            
            // 复制文件到目标位置
            Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            
            log.info("文件上传成功: {}", targetPath.toString());
        } catch (IOException e) {
            log.error("文件上传失败: {}", filepath, e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    /**
     * 获取文件的完整本地路径
     *
     * @param filepath 文件相对路径
     * @return 完整的本地文件路径
     */
    public String getFullFilePath(String filepath) {
        // 标准化文件路径，移除开头的斜杠
        String normalizedPath = StringUtils.stripStart(filepath, "/");
        
        // 拼接完整路径
        String uploadPath = fileProperties.getNormalizedPath();
        return Paths.get(uploadPath, normalizedPath).toString();
    }

    /**
     * 获取文件访问URL
     *
     * @param filepath 文件相对路径
     * @return 文件访问URL
     */
    public String getFileAccessUrl(String filepath) {
        // 标准化文件路径，移除开头的斜杠
        String normalizedPath = StringUtils.stripStart(filepath, "/");
        
        // 拼接访问地址
        String accessUrl = fileProperties.getNormalizedAccessUrl();
        return accessUrl + normalizedPath;
    }

    /**
     * 检查文件是否存在
     *
     * @param filepath 文件相对路径
     * @return 文件是否存在
     */
    public boolean fileExists(String filepath) {
        try {
            String normalizedPath = StringUtils.stripStart(filepath, "/");
            String uploadPath = fileProperties.getNormalizedPath();
            Path targetPath = Paths.get(uploadPath, normalizedPath);
            
            return Files.exists(targetPath);
        } catch (Exception e) {
            log.error("检查文件存在性失败: {}", filepath, e);
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param filepath 文件相对路径
     * @return 是否删除成功
     */
    public boolean deleteFile(String filepath) {
        try {
            String normalizedPath = StringUtils.stripStart(filepath, "/");
            String uploadPath = fileProperties.getNormalizedPath();
            Path targetPath = Paths.get(uploadPath, normalizedPath);
            
            if (Files.exists(targetPath)) {
                Files.delete(targetPath);
                log.info("文件删除成功: {}", filepath);
                return true;
            }
            return false;
        } catch (IOException e) {
            log.error("文件删除失败: {}", filepath, e);
            return false;
        }
    }
}