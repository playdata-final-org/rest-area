    package com.example.DevSculpt.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import java.time.LocalDateTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "dev_user")
    public class UserEntity {
        @Id
        @GeneratedValue
        // P.K
        private Long devId;
        // 회원 아이디
        @Column(name = "user_id")
        private String username;
        // 회원 비밀번호
        private String password;
        // 회원 이름 (실명)
        private String name;
        // 회원 닉네임
        private String nickName;
        // 회원 이메일
        private String email;
        // 회원 등급 ROLE_USER || ROLE_ADMIN
        private String role;
        // 회원 생성날짜
        @CreationTimestamp
        private LocalDateTime creationDate;
        @UpdateTimestamp
        // 회원 수정날짜
        private LocalDateTime modificationDate;
        // 회원 기본프로필
        private String profileImage;

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }
    }