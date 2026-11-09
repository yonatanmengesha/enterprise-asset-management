package com.company.assetmanagement.service;

import com.company.assetmanagement.dto.AssetRequest;
import com.company.assetmanagement.dto.AssetResponse;
import com.company.assetmanagement.enums.AssetStatus;

import java.util.List;

public interface AssetService {
    AssetResponse createAsset(AssetRequest request);
    List<AssetResponse> getAllAssets();
    AssetResponse getAssetById(Long id);
    AssetResponse updateAsset(Long id, AssetRequest request);
    void deleteAsset(Long id);
    List<AssetResponse> getAssetsByStatus(AssetStatus status);
}
