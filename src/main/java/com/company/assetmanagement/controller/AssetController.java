package com.company.assetmanagement.controller;

import com.company.assetmanagement.dto.AssetRequest;
import com.company.assetmanagement.dto.AssetResponse;
import com.company.assetmanagement.enums.AssetStatus;
import com.company.assetmanagement.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<AssetResponse> createAsset(@Valid @RequestBody AssetRequest request) {
        AssetResponse created = assetService.createAsset(request);
        return ResponseEntity.created(URI.create("/api/v1/assets/" + created.id())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<AssetResponse>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponse> updateAsset(
            @PathVariable Long id, @Valid @RequestBody AssetRequest request) {
        return ResponseEntity.ok(assetService.updateAsset(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssetResponse>> getAssetsByStatus(@PathVariable AssetStatus status) {
        return ResponseEntity.ok(assetService.getAssetsByStatus(status));
    }
}
