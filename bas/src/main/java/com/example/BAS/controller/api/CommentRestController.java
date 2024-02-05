package com.example.BAS.controller.api;

import com.example.BAS.dto.collection.CommentDTO;
import com.example.BAS.dto.collection.UserComment;
import com.example.BAS.entitiy.blog.CollectionComment;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.collection.CollectionService;
import com.example.BAS.service.collection.CommentService;
import com.example.BAS.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentRestController {
    private final UsersService usersService;
    private final CollectionService collectionService;
    private final CommentService commentService;

    @PostMapping("/saveComment")
    public ResponseEntity<CommentDTO> comment(@RequestBody UserComment userComment) {
        CollectionComment collectionComment = commentService.save(userComment);

        Long userId = userComment.getUserId();
        Users users = usersService.findByUserId(userId);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setNickName(users.getNickName());
        commentDTO.setProfileImageUrl(users.getProfileImage().getFileUrl());
        commentDTO.setContent(collectionComment.getContent());
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByCollectionId(@RequestBody Map<String, String> requestData) {
        Long collectionId = Long.parseLong(requestData.get("collectionId"));
        List<CollectionComment> collectionComments = commentService.findByCollectionId(collectionId);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (CollectionComment comment : collectionComments) {
            Long userId = comment.getUsers().getUserId();
            Users users = usersService.findByUserId(userId);

            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setNickName(users.getNickName());
            commentDTO.setProfileImageUrl(users.getProfileImage().getFileUrl());
            commentDTO.setContent(comment.getContent());

            commentDTOs.add(commentDTO);
        }
        System.out.println("commentDTOs = " + commentDTOs);
        return ResponseEntity.ok(commentDTOs);
    }
}
