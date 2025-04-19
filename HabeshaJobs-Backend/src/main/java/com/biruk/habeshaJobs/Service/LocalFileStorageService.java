package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.Interfaces.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    private final String uploadDirectory = "uploads";     // base Directory to save files. it is inside the project directory. next to src folder.

    //directory is the sub-directory inside the base directory where the file will be saved.
    public String saveFile(MultipartFile file, String directory) {

        try {
            //This generates a unique file name using UUID and appends the original file name to avoid file name collisions.
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID()+"_"+originalFilename;

            //combines the base directory and the directory name (where the file will be saved) to create the full path.
            Path folderPath = Paths.get(uploadDirectory, directory);
            //Ensures that the directory exists. If it doesn't, it will create it along with any necessary parent directories.
            Files.createDirectories(folderPath);

            //Add the unique file name to the directory path to create the full path for the file.
            Path filePath = folderPath.resolve(fileName);

            //take the uploaded file's data and save it as a new file at the path provided by filePath. If the file with that name already exists, replace it.
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return directory + "/" + fileName;      //return the relative path of the file.
        }catch (IOException e){
            throw new RuntimeException("failed to store file", e);
        }
    }

}
