package com.example.BAS.entitiy.files;

import com.example.BAS.entitiy.blog.Collections;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CollectionImages extends Image{

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collections collections;

}
