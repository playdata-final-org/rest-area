package com.example.BAS.controller.api;

import com.example.BAS.dto.collection.LikeRequest;
import com.example.BAS.entitiy.blog.CollectionLike;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.collection.CollectionLikeService;
import com.example.BAS.service.collection.CollectionService;
import com.example.BAS.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CollectionRestController {

    private final CollectionLikeService collectionLikeService;
    private final UsersService usersService;
    private final CollectionService collectionService;

    @GetMapping("/likeCount/{collectionId}")
    public ResponseEntity<Integer> getLikeCountByCollectionId(@PathVariable("collectionId") Long collectionId) {
        Collections collections = collectionService.findByCollectionIds(collectionId);

        List<CollectionLike> likes = collectionLikeService.findByCollections(collections);
        System.out.println("likeIds = " + likes);
        int likeCount = likes.size();
        System.out.println("likeCount = " + likeCount);
        return ResponseEntity.ok(likeCount);
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
