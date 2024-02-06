package com.example.BAS.controller.api;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.dto.collection.LikeRequest;
import com.example.BAS.entitiy.blog.CollectionLike;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.collection.CollectionLikeService;
import com.example.BAS.service.collection.CollectionService;
import com.example.BAS.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class CollectionRestController {

    private final CollectionLikeService collectionLikeService;
    private final UsersService usersService;
    private final CollectionService collectionService;

    @GetMapping("/likeCount/{collectionId}")
    public ResponseEntity<Map<String,Object>> getLikeCountByCollectionId(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("collectionId") Long collectionId) {
        Collections collections = collectionService.findByCollectionIds(collectionId);

        List<CollectionLike> likes = collectionLikeService.findByCollections(collections);
        System.out.println("likeIds = " + likes);
        int likeCount = likes.size();
        boolean is_liked = likes.stream().filter(row -> {
            return Objects.equals(row.getUser().getUserId(), principalDetails.getUsers().getUserId());
        }).count() > 0;
        System.out.println("likeCount = " + likeCount);
        Map<String, Object> map = new HashMap<>();
        map.put("count",likeCount);
        map.put("is_liked",is_liked);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/add")
    public void addLike(@RequestBody LikeRequest likeRequest) {
        Users user = usersService.findByUserId(likeRequest.getUserId());
        System.out.println("user ============================= " + user);
        Collections collection = collectionService.findByCollectionId(likeRequest.getCollectionId());
        System.out.println("collection ============================== " + collection);
        collectionLikeService.addLike(user, collection);
    }
    @DeleteMapping("/remove")
    public void removeLike(@RequestBody LikeRequest likeRequest) {
        Users user = usersService.findByUserId(likeRequest.getUserId());
        System.out.println("user ==================================== " + user);
        Collections collection = collectionService.findByCollectionId(likeRequest.getCollectionId());
        System.out.println("collection =============================== " + collection);;
        collectionLikeService.removeLike(user, collection);
    }
}
