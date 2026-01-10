package com.travis.monolith.module.demo.internal.utils;

import com.travis.monolith.module.demo.internal.config.properties.FileUploadProperties;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author travis
 * @email travis1215@vip.qq.com
 * @date 2020-04-21 21:25
 */
@Component
public class FileUtils {
    private static FileUploadProperties fileUploadProperties;

    @Autowired
    public void setRedisTemplate(FileUploadProperties fileUploadProperties) {
        FileUtils.fileUploadProperties = fileUploadProperties;
    }


    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    public static String save(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String suffix = "";
        var suffixIndex = originalFilename.lastIndexOf(".");
        if (suffixIndex != -1) {
            suffix = originalFilename.substring(suffixIndex);
        }
        String fileName = UUIDUtils.get() + suffix;
        return save(file, fileName);
    }

    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    public static String save(MultipartFile file, String fileName) {
        var filePath = fileUploadProperties.getResourceLocation() + DateTimeUtils.getFormatTime("yyyyMMdd/");
        return save(file, filePath, fileName);
    }

    /**
     * 保存文件
     *
     * @param file
     * @param filePath
     * @param fileName
     * @return
     */
    public static String save(MultipartFile file, String filePath, String fileName) {
        File pathFolder = new File(filePath);
        if (!pathFolder.exists()) {
            if (!pathFolder.mkdirs()) {
                throw new BusinessException(ErrorCodeEnum.IO_EXCEPTION);
            }
        }
        File target = new File(pathFolder, fileName);
        try {
            file.transferTo(target);
            return filePath.replace(fileUploadProperties.getResourceLocation(), fileUploadProperties.getResourceHandler().replace("**", "")) + fileName;
        } catch (IOException e) {
            throw new BusinessException(e, ErrorCodeEnum.IO_EXCEPTION);
        }
    }
}
