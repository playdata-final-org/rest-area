package com.example.BAS.dto.About;

import com.example.BAS.entitiy.blog.Blogs;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AboutResponseDTO {
    private Blogs blogs;
    private String aboutTitle;
    private String aboutContent;

}
