package top.qtcc.data_structure.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.qtcc.data_structure.manager.file.FileManager;

import java.io.File;
import java.nio.file.Files;

/**
 * 文件访问控制器
 * 支持通过HTTP访问上传的文件
 */
@RestController
@RequestMapping("/files")
@Slf4j
public class FileAccessController {

    @Autowired
    private FileManager fileManager;

    /**
     * 访问上传的文件
     *
     * @param request HTTP请求
     * @return 文件资源
     */
    @GetMapping("/**")
    public ResponseEntity<Resource> accessFile(HttpServletRequest request) {
        try {
            // 获取请求路径，去掉/files前缀
            String requestUri = request.getRequestURI();
            String filePath = requestUri.substring("/files".length());
            
            // 处理路径为空的情况
            if (filePath.isEmpty() || "/".equals(filePath)) {
                return ResponseEntity.notFound().build();
            }
            
            // 检查文件是否存在
            if (!fileManager.fileExists(filePath)) {
                log.warn("文件不存在: {}", filePath);
                return ResponseEntity.notFound().build();
            }
            
            // 获取文件路径
            String fullPath = fileManager.getFullFilePath(filePath);
            File file = new File(fullPath);
            
            // 创建资源对象
            Resource resource = new FileSystemResource(file);
            
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            
            // 设置Content-Type
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            headers.setContentType(MediaType.parseMediaType(contentType));
            
            // 设置Content-Disposition为inline（浏览器内显示）
            headers.setContentDispositionFormData("inline", file.getName());
            
            // 设置缓存控制
            headers.setCacheControl("public, max-age=3600");
            
            log.debug("文件访问成功: {}", filePath);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
                    
        } catch (Exception e) {
            log.error("文件访问失败: {}", request.getRequestURI(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    /**
     * 下载文件（强制下载）
     * @param request HTTP请求
     * @return 文件资源
     */
    @GetMapping("/download/**")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request) {
        try {
            // 获取请求路径，去掉/files/download前缀
            String requestUri = request.getRequestURI();
            String filePath = requestUri.substring("/files/download".length());
            
            // 处理路径为空的情况
            if (filePath.isEmpty() || "/".equals(filePath)) {
                return ResponseEntity.notFound().build();
            }
            
            // 检查文件是否存在
            if (!fileManager.fileExists(filePath)) {
                log.warn("文件不存在: {}", filePath);
                return ResponseEntity.notFound().build();
            }
            
            // 获取文件路径
            String fullPath = fileManager.getFullFilePath(filePath);
            File file = new File(fullPath);
            
            // 创建资源对象
            Resource resource = new FileSystemResource(file);
            
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            
            // 设置Content-Type
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            headers.setContentType(MediaType.parseMediaType(contentType));
            
            // 设置Content-Disposition为attachment（强制下载）
            headers.setContentDispositionFormData("attachment", file.getName());
            
            log.debug("文件下载成功: {}", filePath);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
                    
        } catch (Exception e) {
            log.error("文件下载失败: {}", request.getRequestURI(), e);
            return ResponseEntity.status(500).build();
        }
    }
}