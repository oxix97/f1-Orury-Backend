package org.orury.domain.image.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.IntStream;

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
        if (ImageUtil.filesValidation(files)) return null;

        var fileNames = ImageUtil.createFileName(files.size());
        IntStream.range(0, files.size())
                .forEach(idx -> asyncTaskExecutor.submit(() -> {
                    var tempFile = convert(files.get(idx));
                    amazonS3.putObject(new PutObjectRequest(bucket + domain.getName(), fileNames.get(idx), tempFile)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                    removeFile(tempFile);
                }));

        return fileNames;
    }

    @Override
    public String upload(S3Folder domain, MultipartFile file) {
        if (ImageUtil.fileValidation(file)) return null;
        return upload(domain, List.of(file)).get(0);
    }

    private File convert(MultipartFile multipartFile) {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new FileException(FileExceptionCode.FILE_NOT_FOUND);
        }
        return file;
    }

    private void removeFile(File file) {
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
