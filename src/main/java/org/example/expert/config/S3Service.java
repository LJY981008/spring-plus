package org.example.expert.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${AWS_S3_BUCKET}")
    private String bucketName;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif"};

    public String uploadProfileImage(MultipartFile file, Long userId) {
        validateFile(file);

        String fileName = generateFilename(file, userId);
        String key = "profile-image/" + userId + "/" + fileName;

        try {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build(), RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return getFileUrl(key);
        } catch (IOException e) {
            log.error("Failed to upload file to S3", e);
            throw new InvalidRequestException("파일 업로드 실패");
        }
    }

    public void deleteProfileImage(String imageUrl, Long userId) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }

        String key = extractKeyFromUrl(imageUrl);

        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build());
        } catch (Exception e) {
            log.error("Failed to delete file from S3", e);
            throw new InvalidRequestException("파일 삭제에 실패했습니다.");
        }
    }

    private String extractKeyFromUrl(String imageUrl) {
        String prefix = "https://" + bucketName + ".s3.amazonaws.com/";
        return imageUrl.substring(prefix.length());
    }

    private String getFileUrl(String key) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }

    private String generateFilename(MultipartFile file, Long userId) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return userId + "_" + UUID.randomUUID().toString() + extension;
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidRequestException("파일이 없습니다.");
        }
        else if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidRequestException("파일이 5MB를 초과합니다");
        }

        String originalFilename = Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new InvalidRequestException("파일명이 없습니다"));

        boolean isValidExtension = false;
        for(String extension : ALLOWED_EXTENSIONS) {
            if (originalFilename.toLowerCase().endsWith(extension)) {
                isValidExtension = true;
                break;
            }
        }

        if(!isValidExtension) {
            throw new InvalidRequestException("지원하지 않는 파일형식 입니다.");
        }
    }

}
