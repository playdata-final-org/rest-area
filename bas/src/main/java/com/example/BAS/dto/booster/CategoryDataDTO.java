package com.example.BAS.dto.booster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDataDTO {

    private Long blogId;
    private String imageUrl;
    private String nickName;
    private int boosterCount;
    private int collectionCount;
    private String blogAbout;
    private String category;

}
