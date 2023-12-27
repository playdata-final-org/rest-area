package com.example.BAS.entitiy.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class ProfileImage extends Image{
    @Lob
    private byte[]imageData;
}
