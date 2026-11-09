package com.company.assetmanagement.entity;

import com.company.assetmanagement.enums.AssetStatus;
import com.company.assetmanagement.enums.AssetType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "assets", uniqueConstraints = {
        @UniqueConstraint(name = "uk_asset_serial_number", columnNames = "serial_number")
})
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private AssetType type;

    @Column(name = "serial_number", nullable = false, unique = true, length = 80)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private AssetStatus status;

    @Column(length = 120)
    private String assignedTo;

    private LocalDate purchaseDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Asset() {
    }

    public Asset(String name, AssetType type, String serialNumber,
                 AssetStatus status, String assignedTo, LocalDate purchaseDate) {
        this.name = name;
        this.type = type;
        this.serialNumber = serialNumber;
        this.status = status;
        this.assignedTo = assignedTo;
        this.purchaseDate = purchaseDate;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public AssetType getType() { return type; }
    public String getSerialNumber() { return serialNumber; }
    public AssetStatus getStatus() { return status; }
    public String getAssignedTo() { return assignedTo; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void update(String name, AssetType type, AssetStatus status,
                       String assignedTo, LocalDate purchaseDate) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.assignedTo = assignedTo;
        this.purchaseDate = purchaseDate;
    }
}
