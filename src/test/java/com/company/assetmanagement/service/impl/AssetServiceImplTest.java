package com.company.assetmanagement.service.impl;

import com.company.assetmanagement.dto.AssetRequest;
import com.company.assetmanagement.dto.AssetResponse;
import com.company.assetmanagement.entity.Asset;
import com.company.assetmanagement.enums.AssetStatus;
import com.company.assetmanagement.enums.AssetType;
import com.company.assetmanagement.exception.DuplicateResourceException;
import com.company.assetmanagement.exception.ResourceNotFoundException;
import com.company.assetmanagement.mapper.AssetMapper;
import com.company.assetmanagement.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetServiceImplTest {

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private AssetMapper assetMapper;

    @InjectMocks
    private AssetServiceImpl assetService;

    private AssetRequest request;
    private Asset asset;
    private AssetResponse response;

    @BeforeEach
    void setUp() {
        request = new AssetRequest(
                "ThinkPad T14", AssetType.LAPTOP, "SN-10001",
                AssetStatus.AVAILABLE, null, LocalDate.of(2025, 1, 15));
        asset = new Asset("ThinkPad T14", AssetType.LAPTOP, "SN-10001",
                AssetStatus.AVAILABLE, null, LocalDate.of(2025, 1, 15));
        response = new AssetResponse(1L, "ThinkPad T14", AssetType.LAPTOP,
                "SN-10001", AssetStatus.AVAILABLE, null,
                LocalDate.of(2025, 1, 15), null);
    }

    @Test
    void createAsset_whenSerialNumberIsNew_savesAndReturnsAsset() {
        when(assetRepository.existsBySerialNumber("SN-10001")).thenReturn(false);
        when(assetMapper.toEntity(request)).thenReturn(asset);
        when(assetRepository.save(asset)).thenReturn(asset);
        when(assetMapper.toResponse(asset)).thenReturn(response);

        AssetResponse result = assetService.createAsset(request);

        assertEquals(1L, result.id());
        assertEquals("SN-10001", result.serialNumber());
        verify(assetRepository).save(asset);
    }

    @Test
    void createAsset_whenSerialNumberAlreadyExists_throwsDuplicateException() {
        when(assetRepository.existsBySerialNumber("SN-10001")).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> assetService.createAsset(request));

        assertTrue(exception.getMessage().contains("SN-10001"));
        verify(assetRepository, never()).save(any());
    }

    @Test
    void getAssetById_whenAssetExists_returnsAsset() {
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(assetMapper.toResponse(asset)).thenReturn(response);

        AssetResponse result = assetService.getAssetById(1L);

        assertEquals(1L, result.id());
        verify(assetRepository).findById(1L);
    }

    @Test
    void getAssetById_whenAssetDoesNotExist_throwsNotFoundException() {
        when(assetRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> assetService.getAssetById(99L));

        assertEquals("Asset not found with id: 99", exception.getMessage());
        verify(assetMapper, never()).toResponse(any());
    }

    @Test
    void deleteAsset_whenAssetExists_deletesAsset() {
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));

        assertDoesNotThrow(() -> assetService.deleteAsset(1L));

        verify(assetRepository).delete(asset);
    }

    @Test
    void deleteAsset_whenAssetDoesNotExist_throwsNotFoundException() {
        when(assetRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> assetService.deleteAsset(99L));

        verify(assetRepository, never()).delete(any(Asset.class));
    }
}
