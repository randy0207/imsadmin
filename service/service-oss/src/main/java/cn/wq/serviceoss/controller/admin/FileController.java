package cn.wq.serviceoss.controller.admin;

import cn.wq.result.R;
import cn.wq.result.ResultCodeEnum;
import cn.wq.base.exception.MyException;
import cn.wq.serviceoss.service.FileService;
import cn.wq.util.ExceptionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author LWQ
 * @since 2022/4/15
 */
@Api(tags = "阿里云文件管理")
// @CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file,

            @ApiParam(value = "模块", required = true) @RequestParam("module") String module) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, module, originalFilename);

            return R.ok().message("文件上传成功").data("url", uploadUrl);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new MyException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "文件删除")
    @DeleteMapping("remove")
    public R removeFile(
            @ApiParam(value = "要删除的文件url路径", required = true) @RequestBody String url) {
        fileService.removeFile(url);
        return R.ok().message("文件删除成功");
    }

    @ApiOperation(value = "测试")
    @GetMapping("test")
    public R test() {
        log.info("oss test被调用");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.ok();
    }
}
