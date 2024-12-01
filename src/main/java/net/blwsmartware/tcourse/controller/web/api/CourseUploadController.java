package net.blwsmartware.tcourse.controller.web.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.blwsmartware.tcourse.dto.request.account.PasswordUserUpdate;
import net.blwsmartware.tcourse.dto.request.comment.CommentRequest;
import net.blwsmartware.tcourse.entity.ImageStorage;
import net.blwsmartware.tcourse.entity.Item;
import net.blwsmartware.tcourse.entity.Section;
import net.blwsmartware.tcourse.service.*;
import org.hibernate.boot.Metadata;
import org.jcodec.containers.mp4.MP4Util;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CourseUploadController {
    StorageService storageService;
    SectionService sectionService;
    ItemService itemService;
    CommentService commentService;

    @PostMapping("/course/upload-videos")
    public ResponseEntity<?> uploadVideos(HttpServletRequest request)   {
        try {

            int groupCount = Integer.parseInt(request.getParameter("groupCount"));


            for (int i = 1; i <= groupCount; i++) {
                String groupPrefix = "group" + i;

                Section section = new Section();

                String groupName = request.getParameter(groupPrefix + "Name");
                String groupDescription = request.getParameter(groupPrefix + "Description");
                String _id = request.getParameter(groupPrefix + "Id" );
                log.info("groupName: {}",groupName);
                log.info("_id: {}",_id);
                log.info("groupDescription: {}",groupDescription);

                long groupId = Long.parseLong(_id);
                section= sectionService.getByID(groupId);
                section.setName(groupName);
                section.setName(groupDescription);
                Set<Item> curse =new HashSet<>();
                // Xử lý MultipartFiles
                if (request instanceof MultipartHttpServletRequest multipartRequest) {
                    List<MultipartFile> groupVideos = multipartRequest.getFiles(groupPrefix + "Videos");
                    for (MultipartFile video : groupVideos) {
                        if (!video.isEmpty()) {
                            ImageStorage v1 = storageService.saveToStorage(video);

                            LocalTime ti = getVideoDuration(video);
                            log.info("Timeeeeeeeeeeee :{}",ti.toString());
                            Item vd = Item.builder()
                                    .itemOrder(i)
                                    .time(ti)
                                    .description(video.getOriginalFilename())
                                    .name(v1.getId()+"").build();
                            curse.add(itemService.save(vd));
                        }
                    }

                }
                section.setItems(curse);
                sectionService.save(section);
            }

            return ResponseEntity.ok().body("Upload successful");
        } catch (Exception e) {
            log.error("e: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading videos: " + e.getMessage());
        }
    }
    private LocalTime getVideoDuration(MultipartFile video) {
        try {
            File tempFile = File.createTempFile("temp", null);
            video.transferTo(tempFile);
            MovieBox movieBox = MP4Util.parseMovie(tempFile);
            double durationInSeconds = movieBox.getDuration() / movieBox.getTimescale();

            tempFile.delete();

            return convertSecondsToLocalTime(durationInSeconds);
        } catch (Exception e) {
            e.printStackTrace();
            return LocalTime.of(0, 0, 0);
        }
    }
    private LocalTime convertSecondsToLocalTime(double seconds) {
        long totalSeconds = (long) seconds;
        int hours = (int) (totalSeconds / 3600);
        int minutes = (int) ((totalSeconds % 3600) / 60);
        int secs = (int) (totalSeconds % 60);

        return LocalTime.of(hours, minutes, secs);
    }

    @PostMapping("/course/comment/{id}" )
    public ResponseEntity<?> changePassword(@Valid @RequestBody CommentRequest cmt,
                                            BindingResult result, Model model ,
                                            @PathVariable long id,
                                            @RequestHeader(value = "Referer", required = false) String referer) {
            try{
                cmt.setPostId(id);
                commentService.createComment(cmt);
                return ResponseEntity.ok()
                        .body(Map.of(
                                "success", true,
                                "message", "User updated successfully"
                ));
            } catch (Exception e) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "success", false,
                                "message", e.getMessage()
                        ));
            }

    }
    VoteService voteService;
    UserService userService;

    @PostMapping("/course/vote/{postId}")
    public ResponseEntity<?> votePost(@PathVariable Long postId,
                                           Authentication authentication,
                                           @RequestParam int stars) {

            if(authentication!=null) {
                String username = authentication.getName();
                try {

                    voteService.addVote(postId, userService.getUserByUsername(username).getId(), stars);
                }
                catch (Exception e) {
                    return ResponseEntity.badRequest()
                            .body(Map.of(
                                    "success", false,
                                    "message", e.getMessage()
                            ));
                }
                return ResponseEntity.ok()
                        .body(Map.of(
                                "success", true,
                                "message", "User updated successfully"
                        ));
            }    else {

                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "success", false,
                                "message", "Please login"
                        ));
             }
    }
}