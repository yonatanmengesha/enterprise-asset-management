package com.company.assetmanagement.service.impl;

import com.company.assetmanagement.dto.AssetRequest;
import com.company.assetmanagement.dto.AssetResponse;
import com.company.assetmanagement.entity.Asset;
import com.company.assetmanagement.enums.AssetStatus;
import com.company.assetmanagement.exception.DuplicateResourceException;
import com.company.assetmanagement.exception.ResourceNotFoundException;
import com.company.assetmanagement.mapper.AssetMapper;
import com.company.assetmanagement.repository.AssetRepository;
import com.company.assetmanagement.service.AssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    public AssetServiceImpl(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    @Override
    public AssetResponse createAsset(AssetRequest request) {
        if (assetRepository.existsBySerialNumber(request.serialNumber())) {
            throw new DuplicateResourceException(
                    "Asset serial number already exists: " + request.serialNumber());
        }
        Asset saved = assetRepository.save(assetMapper.toEntity(request));
        return assetMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetResponse> getAllAssets() {
        return assetRepository.findAll().stream().map(assetMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AssetResponse getAssetById(Long id) {
        return assetMapper.toResponse(findAsset(id));
    }

    @Override
    public AssetResponse updateAsset(Long id, AssetRequest request) {
        Asset existing = findAsset(id);
        assetRepository.findBySerialNumber(request.serialNumber())
                .filter(asset -> !asset.getId().equals(id))
                .ifPresent(asset -> {
                    throw new DuplicateResourceException(
                            "Asset serial number already exists: " + request.serialNumber());
                });

        existing.update(request.name(), request.type(), request.status(),
                request.assignedTo(), request.purchaseDate());
        return assetMapper.toResponse(assetRepository.save(existing));
    }

    @Override
    public void deleteAsset(Long id) {
        Asset existing = findAsset(id);
        assetRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetResponse> getAssetsByStatus(AssetStatus status) {
        return assetRepository.findAllByStatus(status).stream()
                .map(assetMapper::toResponse).toList();
    }

    private Asset findAsset(Long id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
    }
}
