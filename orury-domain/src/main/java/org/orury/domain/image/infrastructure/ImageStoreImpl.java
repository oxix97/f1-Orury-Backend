package org.orury.domain.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.orury.common.error.code.FileExceptionCode;
import org.orury.common.error.exception.FileException;
import org.orury.common.util.ImageUrlConverter;
import org.orury.common.util.ImageUtil;
import org.orury.common.util.S3Folder;
import org.orury.domain.image.domain.ImageStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageStoreImpl implements ImageStore {
    private final AmazonS3 amazonS3;
    private final AsyncTaskExecutor asyncTaskExecutor;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.default-image.user}")
    private String USER_DEFAULT_IMAGE;

    @Value("${cloud.aws.s3.default-image.crew}")
    private String CREW_DEFAULT_IMAGE;

    @Override
    public List<String> upload(S3Folder domain, List<MultipartFile> files) {
        // fixme 게시판만 일괄 적용
        if (S3Folder.POST.equals(domain) && ImageUtil.filesValidation(files)) return null;
        if (ImageUtil.filesValidation(files)) return List.of();

        var fileNames = ImageUtil.createFileName(files.size());

        asyncTaskExecutor.submit(() -> {
            var tempFiles = files.stream().map(this::convert).toList();
            upload(domain, tempFiles, fileNames);
            tempFiles.forEach(this::thumbnailConvert);
            upload(S3Folder.THUMBNAIL, tempFiles, fileNames);
            tempFiles.forEach(this::removeFile);
        });

        return fileNames;
    }

    @Override
    public String upload(S3Folder domain, MultipartFile file) {
        if (ImageUtil.fileValidation(file)) return null;
        return upload(domain, List.of(file)).get(0);
    }

    private File convert(MultipartFile multipartFile) {
        // MultipartFile을 File로 변환합니다.
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new FileException(FileExceptionCode.FILE_NOT_FOUND);
        }
        return file;
    }

    private void thumbnailConvert(File file) {
        try {
            Thumbnails.of(file)
                    .size(96, 128)
                    .toFile(file);
        } catch (IOException e) {
            throw new FileException(FileExceptionCode.FILE_NOT_FOUND);
        }
    }

    private void upload(S3Folder domain, List<File> files, List<String> fileNames) {
        for (int idx = 0; idx < files.size(); idx++) {
            // S3에 파일들을 업로드
            amazonS3.putObject(new PutObjectRequest(bucket + domain.getName(), fileNames.get(idx), files.get(idx))
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }
    }

    private void removeFile(File file) {
        // 임시 파일을 삭제합니다.
        if (file.delete()) return;
        throw new FileException(FileExceptionCode.FILE_DELETE_ERROR);
    }

    @Override
    public void delete(S3Folder domain, String profile) {
        var image = ImageUtil.splitUrlToImage(profile);
        if (StringUtils.equals(image, USER_DEFAULT_IMAGE)) return;
        if (StringUtils.equals(image, CREW_DEFAULT_IMAGE)) return;
        asyncTaskExecutor.submit(() -> deleteS3Image(domain, image));
    }

    @Override
    public void delete(S3Folder domain, List<String> links) {
        if (ImageUtil.imagesValidation(links)) return;
        links.stream()
                .map(ImageUrlConverter::splitUrlToImage)
                .forEach(image -> asyncTaskExecutor.submit(
                        () -> deleteS3Image(domain, image)
                ));
    }

    private void deleteS3Image(S3Folder domain, String image) {
        amazonS3.deleteObject(bucket + domain.getName(), image);
    }
}
