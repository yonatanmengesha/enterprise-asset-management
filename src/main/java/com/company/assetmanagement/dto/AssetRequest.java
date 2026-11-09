package com.company.assetmanagement.dto;

import com.company.assetmanagement.enums.AssetStatus;
import com.company.assetmanagement.enums.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AssetRequest(
        @NotBlank @Size(max = 120) String name,
        @NotNull AssetType type,
        @NotBlank @Size(max = 80) String serialNumber,
        @NotNull AssetStatus status,
        @Size(max = 120) String assignedTo,
        LocalDate purchaseDate
) {
}
