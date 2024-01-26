package com.example.BAS.dto.membership;

import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MembershipResponseDTO {
    private Blogs blogs;
    private List<Users> users;
    private Users creator;
    private Membership_tier membershipTier;
}
