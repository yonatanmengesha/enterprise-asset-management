package com.company.assetmanagement.dto;

import com.company.assetmanagement.enums.AssetStatus;
import com.company.assetmanagement.enums.AssetType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AssetResponse(
        Long id,
        String name,
        AssetType type,
        String serialNumber,
        AssetStatus status,
        String assignedTo,
        LocalDate purchaseDate,
        LocalDateTime createdAt
) {
}
