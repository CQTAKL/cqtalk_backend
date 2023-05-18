package com.cqtalk.controller.toc;

import com.cqtalk.annotation.AuthRequired;
import com.cqtalk.annotation.LoginRequired;
import com.cqtalk.aop.UserRequired;
import com.cqtalk.entity.file.dto.*;
import com.cqtalk.entity.file.vo.FileDownloadVO;
import com.cqtalk.entity.file.vo.FileRecommendVO;
import com.cqtalk.service.toc.file.FileFunctionService;
import com.cqtalk.util.file.FileDfsUtil;
import com.cqtalk.util.returnObject.ObjectResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
public class FileFunctionController {

    private static final Logger logger = LoggerFactory.getLogger(FileFunctionController.class);

    @Autowired
    private FileDfsUtil fileDfsUtil;

    @Autowired
    private FileFunctionService fileFunctionService;

    @LoginRequired
    @AuthRequired
    // 此处调试的时候多加留意 @RequestParam("file") MultipartFile file
    @ApiOperation(value = "用户上传文件", notes = "尚未测试")
    @PostMapping("/upload")
    public ObjectResult<String> uploadFile(@RequestParam("files") MultipartFile file,
                                               @RequestParam Integer typeId,
                                               @RequestParam Integer comeFromId) throws Exception {
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setTypeId(typeId);
        fileUploadDTO.setComeFromId(comeFromId);
        fileFunctionService.fileUpload(file, fileUploadDTO);
        return ObjectResult.success("上传文件成功");
    }

    @LoginRequired
    @UserRequired
    @ApiOperation(value = "用户删除上传过的文件", notes = "尚未测试")
    @PostMapping("/delete")
    public ObjectResult<String> deleteByPath(/*String filePathName*/@RequestBody FileDeleteDTO fileDeleteDTO) {
        fileFunctionService.deleteFile(fileDeleteDTO);
        return ObjectResult.success("删除成功。");
    }

    @LoginRequired
    // fileUrl涉及到全部和部分的区别，所以我们此处要调试过程中完善代码
    @ApiOperation(value = "用户下载文件", notes = "尚未测试")
    @PostMapping("/download")
    public ObjectResult<String> download(HttpServletResponse response, @RequestBody FileDownloadDTO fileDownloadDTO) {
        FileDownloadVO fileDownloadVO = fileFunctionService.fileDownload(fileDownloadDTO);
        String wholeName = fileDownloadVO.getWholeName();
        byte[] bytes = fileDownloadVO.getBytes();
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(wholeName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(bytes);
        } catch (IOException e) {
            return ObjectResult.error("服务器异常, 请稍后重试");
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ObjectResult.success("文件下载成功");
    }

    @ApiOperation(value = "获取文件的推荐文件信息", notes = "尚未测试")
    @PostMapping("/recommend")
    public ObjectResult<FileRecommendVO> recommend(@RequestBody FileRecommendDTO fileRecommendDTO) {
        FileRecommendVO recommendInfo = fileFunctionService.getRecommendInfo(fileRecommendDTO);
        return ObjectResult.success(recommendInfo);
    }

    @LoginRequired
    @UserRequired(entity = 4, name = "fileId")
    @ApiOperation(value = "新增文件的推荐文件", notes = "尚未测试")
    @PostMapping("/addRecommend")
    public ObjectResult<String> addRecommendFileInfo(@RequestBody FileAddRecommendDTO fileAddRecommendDTO) {
        fileFunctionService.addRecommendFileInfo(fileAddRecommendDTO);
        // 前端在后端返回成功的数据之后一定要记得重定向页面
        return ObjectResult.success("推荐文件信息上传成功。");
    }

    @LoginRequired
    @UserRequired(entity = 4, name = "fileId")
    @ApiOperation(value = "删除文件的推荐文件", notes = "尚未测试")
    @PostMapping("/deleteRecommend")
    public ObjectResult<String> deleteRecommendFileInfo(@RequestBody FileDeleteRecommendDTO fileDeleteRecommendDTO) {
        fileFunctionService.deleteRecommendFileInfo(fileDeleteRecommendDTO);
        // 前端在后端返回成功的数据之后一定要记得重定向页面
        return ObjectResult.success("推荐文件信息删除成功。");
    }

    @LoginRequired
    @UserRequired(entity = 4, name = "fileId")
    @ApiOperation(value = "删除文件的推荐文件", notes = "尚未测试")
    @PostMapping("/upRecommend")
    public ObjectResult<String> upRecommendFileInfo(@RequestBody FileUpAndDownRecommendDTO fileUpAndDownRecommendDTO) {
        fileFunctionService.upRecommendFileInfo(fileUpAndDownRecommendDTO);
        // 前端在后端返回成功的数据之后一定要记得重定向页面
        return ObjectResult.success("推荐文件信息上移成功。");
    }

    @LoginRequired
    @UserRequired(entity = 4, name = "fileId")
    @ApiOperation(value = "删除文件的推荐文件", notes = "尚未测试")
    @PostMapping("/downRecommend")
    public ObjectResult<String> downRecommendFileInfo(@RequestBody FileUpAndDownRecommendDTO fileUpAndDownRecommendDTO) {
        fileFunctionService.downRecommendFileInfo(fileUpAndDownRecommendDTO);
        // 前端在后端返回成功的数据之后一定要记得重定向页面
        return ObjectResult.success("推荐文件信息下移成功。");
    }



}
