package com.example.BAS.dto.About;

import com.example.BAS.entitiy.blog.Blogs;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AboutRequestDTO {
    private Long aboutId;

    private String aboutTitle;
    private String aboutContent;
    private Blogs blogs;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime deleteDate;
}
