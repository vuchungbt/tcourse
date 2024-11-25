package net.blwsmartware.tcourse.controller.web.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.entity.ImageStorage;
import net.blwsmartware.tcourse.service.SectionService;
import net.blwsmartware.tcourse.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CourseUploadController {
    StorageService storageService;
    SectionService sectionService;

    @PostMapping("/course/upload-videos")
    public ResponseEntity<?> uploadVideos(HttpServletRequest request) {
        try {
            // Lấy số lượng nhóm
            int groupCount = Integer.parseInt(request.getParameter("groupCount"));

            // Xử lý từng nhóm
            for (int i = 1; i <= groupCount; i++) {
                String groupPrefix = "group" + i;
                // Lấy thông tin nhóm
                String groupId = request.getParameter(groupPrefix + "Id");
                String groupName = request.getParameter(groupPrefix + "Name");
                String groupDescription = request.getParameter(groupPrefix + "Description");

                // Xử lý MultipartFiles
                if (request instanceof MultipartHttpServletRequest multipartRequest) {
                    List<MultipartFile> groupVideos = multipartRequest.getFiles(groupPrefix + "Videos");

                    // Xử lý upload và lưu thông tin cho từng video
                    for (MultipartFile video : groupVideos) {
                        if (!video.isEmpty()) {
                                ImageStorage v1 = storageService.saveToStorage(video);

                        }
                    }
                }
            }

            return ResponseEntity.ok().body("Upload successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading videos: " + e.getMessage());
        }
    }
}