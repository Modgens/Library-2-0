package com.example.library20.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final String ABS_PATH = "C:\\Users\\Modgen\\Desktop\\LibraryImg\\";

    public byte[] getImg(String imageName) {
        try {
            InputStream in = new FileInputStream(ABS_PATH + imageName);
            return in.readAllBytes();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String saveImg(MultipartFile file){
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(ABS_PATH, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            return "";
        }
        return fileNames.toString();
    }

    public void deleteImg(String imgName) {
        if(imgName.equals("default.jpg"))
            return;
        File file = new File(ABS_PATH + imgName);
        file.delete();
    }
}
