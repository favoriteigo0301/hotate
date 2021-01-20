package study.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * サンプルロジッククラス
 * ビジネスロジックを当クラスに記述する
 */
@Service
public class SampleMemoService {

    public SampleMemoService() {

    }

    public boolean createDirectory() {
        Path path = Paths.get("/var/tmp");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.err.println(e);
            }
            return true;
        }
        return false;
    }

    private String getExtentionFromUploadFile(MultipartFile image) {
        int dot = image.getOriginalFilename().lastIndexOf(".");
        String extention = "";
        if (dot > 0) {
            extention = image.getOriginalFilename().substring(dot).toLowerCase();
        }
        return extention;
    }

    public void uploadImage(MultipartFile image) {
        String extention = getExtentionFromUploadFile(image);
        String fileName = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        System.out.println(fileName);
        Path uploadFile = Paths.get("/var/tmp/" + fileName + extention);

        try(OutputStream os = Files.newOutputStream(uploadFile, StandardOpenOption.CREATE)) {
            byte[] bytes = image.getBytes();
        } catch (IOException e) {
            System.err.println(e);
        }

    }

}
