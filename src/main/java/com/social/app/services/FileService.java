package com.social.app.services;

import com.social.app.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FileService {
    @Value("${upload.path}")
    private String UPLOAD_DIR;
    public String saveCoverImage(User user,MultipartFile coverFile) throws IOException {
        String currentCoverPath = user.getPerson().getCover();
        File directory = new File(UPLOAD_DIR + user.getId().toString()+"/cover/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        if (currentCoverPath != null && !currentCoverPath.isEmpty()) {
            File oldCoverFile = new File(UPLOAD_DIR + user.getId().toString()+"/cover/"+ currentCoverPath);
            if (oldCoverFile.exists()) {
                oldCoverFile.delete();
            }
        }
        // Save the new cover file
        String fileName = coverFile.getOriginalFilename()+user.getPerson().getId();
        String filePath = Paths.get(UPLOAD_DIR+user.getId().toString()+"/cover/",fileName).toString();
        coverFile.transferTo(new File(filePath));
        return fileName;
    }
    public String saveProfileImage(User user,MultipartFile coverFile) throws IOException {
        String currentCoverPath = user.getPerson().getCover();
        File directory = new File(UPLOAD_DIR+ user.getId().toString()+"/profile/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        if (currentCoverPath != null && !currentCoverPath.isEmpty()) {
            File oldCoverFile = new File(UPLOAD_DIR + user.getId().toString()+"/profile/"+ currentCoverPath);
            if (oldCoverFile.exists()) {
                oldCoverFile.delete();
            }
        }
        String fileName = coverFile.getOriginalFilename()+user.getPerson().getId();
        String filePath = Paths.get(UPLOAD_DIR+user.getId().toString()+"/profile/", fileName).toString();
        coverFile.transferTo(new File(filePath));
        return fileName;
    }
    public String uploadImage(String path, MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR+ path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String name = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
        String filePath = Paths.get(UPLOAD_DIR + path, fileName1).toString();
//        File file1 = new File(path);
//        if(!file1.exists()){
//            file1.mkdir();
//        }
        file.transferTo(new File(filePath));
        return fileName1;
    }

    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = UPLOAD_DIR + path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
