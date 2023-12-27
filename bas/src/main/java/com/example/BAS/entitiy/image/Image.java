package com.example.BAS.entitiy.image;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Image {
    @Id
    @GeneratedValue
    private Long imageId;
    @NotNull
    private String uuid;
    @NotNull
    private String fileName;
    @NotNull
    private String fileUrl;
}
