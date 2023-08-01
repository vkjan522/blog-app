package com.vikash.blog.services.impl;

import com.vikash.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name
        String name = file.getOriginalFilename();
        //random name generate file
        String randomName = UUID.randomUUID().toString();
        String concatName = randomName.concat(name.substring(name.lastIndexOf(".")));

        //Full Path
        String filePath = path + File.separator + concatName;
        //create Folder if folder is not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        //File Copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return concatName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        //DB logic to return input stream
        return is;
    }
}
