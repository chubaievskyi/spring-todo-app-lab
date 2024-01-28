package com.chubaievskyi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Response page")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {

    @Schema(description = "List of users")
    private List<T> content;

    @Schema(description = "The number of the received page", example = "0")
    private int page;

    @Schema(description = "Total number of pages", example = "5")
    private int totalPages;
}
