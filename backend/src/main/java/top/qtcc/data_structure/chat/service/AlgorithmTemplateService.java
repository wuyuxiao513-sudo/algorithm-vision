package top.qtcc.data_structure.chat.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 算法模板服务
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Service
public class AlgorithmTemplateService {
    
    private final Map<String, String> templateCache = new HashMap<>();
    
    /**
     * 加载所有算法模板
     */
    public void loadTemplates() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:md/*.md");
            
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename != null) {
                    String content = FileCopyUtils.copyToString(
                        new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
                    );
                    templateCache.put(filename, content);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("加载算法模板失败", e);
        }
    }
    
    /**
     * 获取指定模板内容
     *
     * @param templateName 模板文件名
     * @return 模板内容
     */
    public String getTemplate(String templateName) {
        if (templateCache.isEmpty()) {
            loadTemplates();
        }
        return templateCache.getOrDefault(templateName, "");
    }
    
    /**
     * 获取所有模板内容
     * 
     * @return 所有模板内容的映射
     */
    public Map<String, String> getAllTemplates() {
        if (templateCache.isEmpty()) {
            loadTemplates();
        }
        return new HashMap<>(templateCache);
    }
    
    /**
     * 根据算法名称获取相关模板内容
     * 
     * @param algorithmName 算法名称
     * @return 相关模板内容
     */
    public String getTemplateByAlgorithm(String algorithmName) {
        if (templateCache.isEmpty()) {
            loadTemplates();
        }
        
        // 根据算法名称匹配相关模板
        for (Map.Entry<String, String> entry : templateCache.entrySet()) {
            String filename = entry.getKey().toLowerCase();
            String algorithm = algorithmName.toLowerCase();
            
            if (filename.contains(algorithm) || algorithm.contains(filename.replace(".md", ""))) {
                return entry.getValue();
            }
        }
        
        // 如果没有找到特定算法模板，返回通用模板
        return templateCache.getOrDefault("算法模板.md", "");
    }
}