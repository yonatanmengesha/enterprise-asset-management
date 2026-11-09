package com.company.assetmanagement.controller;

import com.company.assetmanagement.dto.AssetResponse;
import com.company.assetmanagement.enums.AssetStatus;
import com.company.assetmanagement.enums.AssetType;
import com.company.assetmanagement.service.AssetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssetController.class)
class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AssetService assetService;

    @Test
    void getAllAssets_returnsOkAndAssetList() throws Exception {
        AssetResponse asset = new AssetResponse(
                1L, "ThinkPad T14", AssetType.LAPTOP, "SN-10001",
                AssetStatus.AVAILABLE, null, null, null);
        when(assetService.getAllAssets()).thenReturn(List.of(asset));

        mockMvc.perform(get("/api/v1/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].serialNumber").value("SN-10001"));
    }
}
