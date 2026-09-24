package top.qtcc.data_structure.controller;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.qtcc.data_structure.common.BaseResponse;
import top.qtcc.data_structure.common.ResultUtils;
import top.qtcc.data_structure.constant.FileConstant;
import top.qtcc.data_structure.domain.dto.file.UploadFileRequest;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.enums.FileUploadBizEnum;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.manager.file.FileManager;
import top.qtcc.data_structure.service.UserService;

import jakarta.annotation.Resource;
import java.io.File;
import java.util.Arrays;

/**
 * 文件接口
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private UserService userService;

   @Resource(name = "fileManager")
   private FileManager fileManager;

    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @param uploadFileRequest 上传文件请求
     * @return 文件地址
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile multipartFile,
                                           UploadFileRequest uploadFileRequest) {
        String biz = uploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        validFile(multipartFile, fileUploadBizEnum);
        User loginUser = userService.getCurrentUser();
        
        // 生成唯一文件名
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String originalFilename = multipartFile.getOriginalFilename();
        String filename = uuid + "-" + originalFilename;
        
        // 文件目录：根据业务、用户来划分
        String filepath = String.format("/%s/%s/%s", fileUploadBizEnum.getValue(), loginUser.getId(), filename);
        
        File file = null;
        try {
            // 创建临时文件
            file = File.createTempFile("upload_", "_" + originalFilename);
            multipartFile.transferTo(file);
            
            // 上传文件到本地存储
            fileManager.putObject(filepath, file);
            
            // 返回可访问地址（使用配置化的访问地址）
            String accessUrl = fileManager.getFileAccessUrl(filepath);
            return ResultUtils.success(accessUrl);
        } catch (Exception e) {
            log.error("文件上传失败, filepath = {}", filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("临时文件删除失败, filepath = {}", filepath);
                }
            }
        }
    }

    /**
     * 校验文件
     *
     * @param multipartFile 文件
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        // 用户头像
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
        // 用户文件
        if (FileUploadBizEnum.USER_FILE.equals(fileUploadBizEnum)) {
            if (fileSize > 10 * ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 10M");
            }
            if (!Arrays.asList("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
        // 用户图片
        if (FileUploadBizEnum.USER_IMAGE.equals(fileUploadBizEnum)) {
            if (fileSize > 5 * ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 5M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}
