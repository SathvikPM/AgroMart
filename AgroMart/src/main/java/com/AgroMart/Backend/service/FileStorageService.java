package com.AgroMart.Backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    // Reads upload folder name from application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Returns the full absolute path for the upload folder
     * (e.g., C:\Users\sathv\eclipse-workspace\ShopSphere-uploads\category)
     */
    private String getAbsoluteUploadPath(String subFolder) {
        String projectPath = System.getProperty("user.dir");  // ✅ gets your project folder dynamically
        return projectPath + File.separator + uploadDir + File.separator + subFolder;
    }

    /**
     * Saves the uploaded file in a specific subfolder (like "category" or "product")
     *
     * @param file      uploaded file
     * @param subFolder subfolder name (example: "category")
     * @return the relative image path (to store in DB)
     */
    public String saveFile(MultipartFile file, String subFolder) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty!");
        }

        String fullPath = getAbsoluteUploadPath(subFolder);
        File dir = new File(fullPath);
        if (!dir.exists()) {
            dir.mkdirs(); // create folder if missing
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File destination = new File(dir, fileName);

        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
        }

        // Return a clean relative path for DB (frontend will use this)
        return "/uploads/" + subFolder + "/" + fileName;
    }

    /**
     * Deletes a stored file
     *
     * @param relativePath file path as stored in DB
     * @return true if successfully deleted, false otherwise
     */
    public boolean deleteFile(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) return false;

        String projectPath = System.getProperty("user.dir");
        File file = new File(projectPath + File.separator + relativePath);
        return file.exists() && file.delete();
    }
}
