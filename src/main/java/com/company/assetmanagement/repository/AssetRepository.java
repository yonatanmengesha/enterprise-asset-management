package com.company.assetmanagement.repository;

import com.company.assetmanagement.entity.Asset;
import com.company.assetmanagement.enums.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);

    List<Asset> findAllByStatus(AssetStatus status);
}
