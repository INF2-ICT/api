package com.quintor.api.mongo;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/mt940")
public class Mt940Controller {
    private final Mt940Service mt940Service;

    public Mt940Controller(Mt940Service mt940Service) {
        this.mt940Service = mt940Service;
    }

    public Mt940Service getMt940Service() {
        return this.mt940Service;
    }

    /**
     * This method can called when a file should be uploaded to the MongoDB. It inserts the file with a userId.
     *
     * @param file   The file which should be uploaded.
     * @param userId The user id of the user who uploads the file.
     * @return A string with succes when the file has been uploaded. A string with no_file when there is no file. And a string with wrong_user_id when the user id is invalid.
     * @throws IOException
     */
    @PostMapping("/insert")
    public String insertMt940(@RequestParam("file") File file, @RequestParam("userId") int userId) throws IOException {
        MultipartFile multipartFile = convertFileToMultiPartFile(file);
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "no_file";
        }
        if (userId <= 0) {
            return "wrong_user_id";
        }
        String document = new String(multipartFile.getBytes(), StandardCharsets.UTF_8);
        mt940Service.insertMt940(new mt940(document, userId));
        return "success";
    }

    /**
     * This method converts a File object to a MultiPartFile object.
     *
     * @param file The file which should be converted.
     * @return a MultiPartFile object.
     * @throws IOException
     */
    private MultipartFile convertFileToMultiPartFile(File file) throws IOException {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        InputStream stream = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);
    }
}
