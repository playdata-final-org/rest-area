package com.example.BAS.entitiy.blog;

import com.example.BAS.entitiy.files.CollectionFiles;
import com.example.BAS.entitiy.files.CollectionImages;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Collections {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long collectionId;

    @OneToMany(mappedBy = "collections")
    @ToString.Exclude
    private List<CollectionComment> collectionComments;

    @OneToMany(mappedBy = "collections")
    @ToString.Exclude
    private List<CollectionLike> collectionLikes;

    @ManyToOne
    @JoinColumn(name = "blogs")
    private Blogs blogs;

    @OneToMany(mappedBy = "collections", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CollectionImages> collectionImages;

    private String title;
    private String content;

    private String membershipType;
    private Long tierId;
    private String tierName;

    @OneToOne(mappedBy = "collections", cascade = CascadeType.ALL)
    @ToString.Exclude
    private CollectionFiles collectionFiles;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
    @PreUpdate
    public void updateDate() {
        this.updateDate = LocalDateTime.now();
    }
    @PreRemove
    public void deleteDate() {
        this.deleteDate = LocalDateTime.now();
    }

    public void updateCollectionImages(CollectionImages collectionImages) {
        this.collectionImages = (List<CollectionImages>) collectionImages;
    }


}
