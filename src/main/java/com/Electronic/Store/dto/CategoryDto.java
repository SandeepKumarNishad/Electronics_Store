package com.Electronic.Store.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;
    @Size(min=4,message = "title must be minimum 4 characters")
    @NotBlank(message = "title is required!!")
    private String title;
    @NotBlank(message = "Description required!!")
    private String description;

    private String coverImage;
}
