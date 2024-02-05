package com.example.BAS.entitiy.blog;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Membership_tier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tierId;

    private String tierName;
    @Column(length = 1000)
    private String tierContent;
    private String tierPrice;
    private String grade;

    @OneToMany(mappedBy = "membership_tier",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Users> users;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_id")
    private Memberships memberships;

    @OneToMany(mappedBy = "membership_tier",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BoostHistory> boostHistories;

    public void setMemberships(Memberships memberships) {
        this.memberships = memberships;
        if (memberships != null && !memberships.getMembershipTiers().contains(this)) {
            memberships.addMembershipTier(this);
        }
    }


}

