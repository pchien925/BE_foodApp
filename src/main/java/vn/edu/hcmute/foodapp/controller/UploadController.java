package vn.edu.hcmute.foodapp.controller;

import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {


    private final CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseData<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Upload success")
                .data(cloudinaryService.uploadFile(file))
                .build();
    }
}