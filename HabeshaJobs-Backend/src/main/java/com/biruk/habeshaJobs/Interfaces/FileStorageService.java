package com.biruk.habeshaJobs.Interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String saveFile(MultipartFile file, String directory);

}
