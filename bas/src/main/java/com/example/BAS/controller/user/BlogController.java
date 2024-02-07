package com.example.BAS.controller.user;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.dto.About.AboutResponseDTO;
import com.example.BAS.dto.collection.CollectionDataDTO;
import com.example.BAS.dto.collection.CollectionRequestDTO;
import com.example.BAS.dto.collection.CollectionResponseDTO;
import com.example.BAS.dto.collection.CommentDTO;
import com.example.BAS.dto.membership.MembershipTierRequestDTO;
import com.example.BAS.entitiy.blog.*;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.auth.AuthService;
import com.example.BAS.service.blog.BlogService;
import com.example.BAS.service.boostHistory.BoostHistoryService;
import com.example.BAS.service.collection.CollectionService;
import com.example.BAS.service.collection.CommentService;
import com.example.BAS.service.membership.MembershipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class BlogController {

    private final MembershipService membershipService;
    private final BlogService blogService;
    private final AuthService authService;
    private final CollectionService collectionService;
    private final BoostHistoryService boostHistoryService;
    private final CommentService commentService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @GetMapping("/blog/{blogId}")
    public String showBlogDetail(@PathVariable("blogId") Long blogId,
                                 Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Blogs blog = blogService.findByBlogId(blogId);
        model.addAttribute("booster", principalDetails.getUsers());

        Long membershipId = null;
        Memberships memberships = membershipService.findByBlogId(blogId);
        if (memberships != null) {
            membershipId = memberships.getMembershipId();
        }

        Long aboutId = null;
        BlogAbout about = blogService.findByBlogs_BlogId(blogId);
        if (about != null){
            aboutId = about.getAboutId();
        }
        System.out.println("about ======================= " + about);

        String blogCategory;
        if (blog != null && blog.getCategory() != null) {
            blogCategory = String.valueOf(blog.getCategory());
        } else {
            blogCategory = "카테고리를 선택해주세요";
        }

        int boostersCount = 0;
        int collectionCount = 0;
        if (blog != null) {
            boostersCount = boostHistoryService.getBoostersCount(blogId);
            collectionCount = collectionService.getCollectionCount(blogId);
        }

        Long ownerId = blog.getUsers().getUserId();
        Users owner = authService.findByUserId(ownerId);


        List<BlogCategory> categories = Arrays.asList(BlogCategory.values());

        String category = String.valueOf(blog.getCategory());

        if (owner.getCurrentRole() == null) {
            return "user/main";
        }

        if (owner.getProfileImage() != null) {
            model.addAttribute("profileImageUrl", owner.getProfileImage().getFileUrl());
        }


        model.addAttribute("owner", owner);
        model.addAttribute("booster",principalDetails.getUsers());
        model.addAttribute("blog", blog);
        model.addAttribute("titleImageUrl",blog.getBlogTitleImages().getFileUrl());
        model.addAttribute("categories",categories);
        model.addAttribute("boostersCount",boostersCount);
        model.addAttribute("collectionCount",collectionCount);
        model.addAttribute("blogCategory",blogCategory);
        model.addAttribute("membershipId",membershipId);
        model.addAttribute("aboutId",aboutId);
        return "blog/blog";
    }

    @GetMapping("/blogHome")
    public String showBlogHome() {
        return "blog/blog-home";
    }

    @GetMapping("/blogCollection/{blogId}")
    public String showBlogCollection(@PathVariable("blogId") Long blogId,
                                     @AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @PageableDefault(size = 2)
                                     Pageable pageable,
                                     Model model) {

        String rolePage = principalDetails.rolePage();

        Long boostId = principalDetails.getUsers().getUserId();




        Blogs blog = blogService.findByBlogId(blogId);

        Long creatorId = blog.getUsers().getUserId();

        String category = String.valueOf(blog.getCategory());

        Long userId = principalDetails.getUsers().getUserId();
        List<BoostHistory> boostHistories = boostHistoryService.findByUserId(userId);
        Long tierId = null;
        for (BoostHistory boostHistory : boostHistories) {
            if (boostHistory.getBlogs().getBlogId().equals(blogId)) {
                tierId = boostHistory.getMembership_tier().getTierId();
                break;
            }
        }


        List<List<CommentDTO>> commentList = new ArrayList<>();
        List<Long> collectionIdList = new ArrayList<>();
        List<List<String>> collectionImagesUrlsList = new ArrayList<>();
        List<String> collectionFileNames = new ArrayList<>();
        List<String> collectionUuids = new ArrayList<>();

        Page<CollectionRequestDTO> collections = collectionService.getCollectionList(blogId,pageable);
        for (CollectionRequestDTO collection : collections.getContent()) {
            Long collectionId = collection.getCollectionId();
            collectionIdList.add(collectionId);

            List<CommentDTO> comments = new ArrayList<>();
            for (CollectionComment comment : commentService.findByCollectionId(collectionId)) {
                Long commentId = comment.getCommentId();
                Users users = comment.getUsers();
                String content = comment.getContent();
                String profileUrl = users.getProfileImage().getFileUrl();
                String nickName = users.getNickName();

                CommentDTO commentDTO = new CommentDTO();

                commentDTO.setContent(content);
                commentDTO.setProfileImageUrl(profileUrl);
                commentDTO.setNickName(nickName);

                comments.add(commentDTO);
            }
            commentList.add(comments);

            List<String> collectionImagesUrls = collectionService.getCollectionImagesUrls(collectionId);
            collectionImagesUrlsList.add(collectionImagesUrls);

            String collectionFileName = collectionService.getCollectionFileName(collectionId);
            collectionFileNames.add(collectionFileName);

            String collectionUuid = collectionService.getCollectionFileUuid(collectionId);
            collectionUuids.add(collectionUuid);
        }
        CollectionDataDTO blogCollectionDTO = new CollectionDataDTO();
        blogCollectionDTO.setProfileImageUrl(principalDetails.profileImageUrl());
        blogCollectionDTO.setBooster(principalDetails.getUsers());
        blogCollectionDTO.setTierId(tierId);
        blogCollectionDTO.setBlog(blog);
        blogCollectionDTO.setCollections(collections);
        blogCollectionDTO.setCollectionFileNames(collectionFileNames);
        blogCollectionDTO.setCollectionIdList(collectionIdList);
        blogCollectionDTO.setCollectionImagesUrlsList(collectionImagesUrlsList);
        blogCollectionDTO.setCollectionUuids(collectionUuids);
        blogCollectionDTO.setCategory(category);
        blogCollectionDTO.setComments(commentList);
        System.out.println("blogCollectionDTO =================== " + blogCollectionDTO);
        if (rolePage == null) {
            model.addAttribute("blogCollectionDTO",blogCollectionDTO);
            System.out.println("blogCollectionDTO ========================================================= " + blogCollectionDTO);
            model.addAttribute("userId",userId);
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("blog",blog);
            model.addAttribute("booster",boostId);
            model.addAttribute("creator",creatorId);
            return "blog/blog-collection";
        }else{
            return "user/main";
        }
    }


    @GetMapping("/download/img/collectionFiles/{uuid}_{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("uuid") String uuid,
                                                 @PathVariable("fileName") String fileName) throws IOException {
        String filePath = Paths.get(uploadPath, "collectionFiles", uuid + "_" + fileName).toString();
        System.out.println("filePath = " + filePath);

        Resource resource = collectionFilePath(filePath);
        System.out.println("resource = " + resource);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

       return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    private Resource collectionFilePath(String filePath) {
        Path path = Paths.get(filePath);
        return new FileSystemResource(path.toFile());
    }

    @GetMapping("/blogMembership/{blogId}")
    public String showMembership(@PathVariable("blogId") Long blogId,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model) {
        List<MembershipTierRequestDTO> membershipList = membershipService.getAllMemberships(blogId);
        String rolePage = principalDetails.rolePage();

        Long userId = principalDetails.getUsers().getUserId();
        List<BoostHistory> boostHistories = boostHistoryService.findBlogIdByUserId(userId);

        boolean isBoosted = false;
        for (BoostHistory boostHistory : boostHistories) {
            if (boostHistory.getBlogs().getBlogId().equals(blogId)) {
                isBoosted = true;
                break;
            }
        }
        Blogs blog = blogService.findByBlogId(blogId);
        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            model.addAttribute("membershipTier",membershipList);
            model.addAttribute("isBoosted", isBoosted);

            return "blog/blog-membership";
        }else{
            return "user/main";
        }
    }

    @GetMapping("/blogWrite/{blogId}")
    public String showBlogWrite(@PathVariable("blogId") Long blogId,
                                @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();
        Blogs blog = blogService.findByBlogId(blogId);

        Memberships membership = membershipService.findByMembershipId(blogId);
        System.out.println("membership = " + membership);
        if(membership == null){
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            model.addAttribute("membershipTiers", null);
            return "blog/blog-write";
        }
        Long membershipId = membership.getMembershipId();
        List<MembershipTierRequestDTO> membershipTiers = membershipService.findTierInfoByMembershipId(membershipId);
        System.out.println("membershipTiers ===================== " + membershipTiers);
        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            model.addAttribute("membershipTiers",membershipTiers);
            System.out.println("membershipTiers =================== " + membershipTiers);
            return "blog/blog-write";
        }else{
            return "user/main";
        }
    }

    @PostMapping("/blogWrite/{blogId}")
    public String uploadBlog(@PathVariable("blogId") Long blogId,
                             @ModelAttribute CollectionResponseDTO collectionResponseDTO,
                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                             @RequestParam("collectionImages") List<MultipartFile> imageFiles,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("membershipType") String membershipType,
                             @RequestParam("tierId") String tierIdStr
    ) throws IOException {

        Long tierId = null;
        String tierName = "전체공개";

        if (tierIdStr != null && !tierIdStr.isEmpty()) {
            try {
                tierId = Long.parseLong(tierIdStr);
                Membership_tier membershipTier = membershipService.findByTierId(tierId);
                tierName = membershipTier.getTierName();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        Long userId = principalDetails.getUsers().getUserId();
        collectionResponseDTO.setCollectionImages(imageFiles);
        collectionResponseDTO.setCollectionFiles(file);

        collectionService.save(blogId, collectionResponseDTO, membershipType, tierId, tierName);
        return "redirect:/blog/" + blogId + "?userId=" + userId;
    }

    @GetMapping("/blogAbout/{blogId}")
    public String showBlogAbout(@PathVariable("blogId") Long blogId,
                                @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();
        Blogs blog = blogService.findByBlogId(blogId);
        BlogAbout blogAbout = blogService.findByAbout(blogId);
        System.out.println("blogAbout ========================================== " + blogAbout);
        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            model.addAttribute("about",blogAbout);
            return "blog/blog-about";
        }else{
            return "user/main";
        }
    }

    @GetMapping("/aboutWrite/{blogId}")
    public String showBlogAboutWrite(@PathVariable("blogId") Long blogId,
                                     @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();
        Blogs blog = blogService.findByBlogId(blogId);
        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            return "blog/blog-aboutWrite";
        }else{
            return "user/main";
        }
    }
    @PostMapping("/aboutWrite/{blogId}")
    public String BlogAboutWrite(@PathVariable("blogId") Long blogId,
                                 @ModelAttribute AboutResponseDTO aboutResponseDTO,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        blogService.save(blogId,aboutResponseDTO);

        return "redirect:/blog/"+blogId;
    }
    @GetMapping("/updateAbout/{blogId}")
    public String showUpdateBlogAbout(@PathVariable("blogId") Long blogId,
                                  @ModelAttribute AboutResponseDTO aboutResponseDTO,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails,
                                      Model model){
        String rolePage = principalDetails.rolePage();
        Blogs blog = blogService.findByBlogId(blogId);
        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            return "blog/uploadBlogAbout";
        }else{
            return "user/main";
        }
    }

    @PostMapping("/updateAbout/{blogId}")
    public String updateBlogAbout(@PathVariable("blogId") Long blogId,
                                 @ModelAttribute AboutResponseDTO aboutResponseDTO,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        blogService.update(blogId,aboutResponseDTO);

        return "redirect:/blog/"+blogId;
    }
}