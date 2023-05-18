package com.cqtalk.service.toc.file;

import org.springframework.stereotype.Service;

@Service
public interface FileService {

    void addUserFileDownloadInfo(Long fileId, Integer userId);

}
