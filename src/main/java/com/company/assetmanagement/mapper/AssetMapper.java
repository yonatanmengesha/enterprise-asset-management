package com.company.assetmanagement.mapper;

import com.company.assetmanagement.dto.AssetRequest;
import com.company.assetmanagement.dto.AssetResponse;
import com.company.assetmanagement.entity.Asset;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

    public Asset toEntity(AssetRequest request) {
        return new Asset(
                request.name(),
                request.type(),
                request.serialNumber(),
                request.status(),
                request.assignedTo(),
                request.purchaseDate()
        );
    }

    public AssetResponse toResponse(Asset asset) {
        return new AssetResponse(
                asset.getId(),
                asset.getName(),
                asset.getType(),
                asset.getSerialNumber(),
                asset.getStatus(),
                asset.getAssignedTo(),
                asset.getPurchaseDate(),
                asset.getCreatedAt()
        );
    }
}
