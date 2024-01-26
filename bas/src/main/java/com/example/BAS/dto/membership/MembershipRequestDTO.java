package com.example.BAS.dto.membership;

import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MembershipRequestDTO {
    private Long MembershipId;
    private Blogs blogs;
    private List<Users> users;
    private Users creator;
    private Membership_tier membershipTier;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime deleteDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime cancelDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime boostDate;
}
