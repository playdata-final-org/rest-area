package com.example.BAS.dto.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserComment {
    private Long userId;
    private Long collectionId;
    private String content;
}
