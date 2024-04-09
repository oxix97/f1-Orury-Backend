package org.orury.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public class ImageUtil {
    public static boolean imagesValidation(List<String> images) {
        return images == null || images.isEmpty();
    }

    public static boolean filesValidation(List<MultipartFile> files) {
        return files == null || files.isEmpty();
    }

    public static boolean fileValidation(MultipartFile profile) {
        return profile == null || profile.isEmpty();
    }

    public static List<String> createFileName(int size) {
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            names.add(UUID.randomUUID().toString().substring(0, 15));
        }
        return names;
    }

    public static String domainToThumbnail(List<String> image, S3Folder domain) {
        if (imagesValidation(image)) return null;
        return image.get(0).replace(domain.getName(), S3Folder.THUMBNAIL.getName());
    }

    public static String splitUrlToImage(String url) {
        if (Objects.isNull(url) || url.isEmpty()) return null;
        return Arrays.stream(url.split("/"))
                .reduce((first, second) -> second)
                .orElse("");
    }
}
