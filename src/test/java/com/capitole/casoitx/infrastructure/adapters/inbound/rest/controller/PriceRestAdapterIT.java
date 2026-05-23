package com.capitole.casoitx.infrastructure.adapters.inbound.rest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("===== Integration Tests - Price Controller ITest =====")
class PriceRestAdapterIT {

    private static final String PRICES_ENDPOINT = "/api/v1/prices";

    private static final String APPLICATION_DATE_PARAM = "applicationDate";

    private static final String PRODUCT_ID_PARAM = "productId";

    private static final String BRAND_ID_PARAM = "brandId";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test 1: Petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void should_Return_Applicable_Price_Test1() throws Exception {
        mockMvc.perform(get(PRICES_ENDPOINT)
                        .param(APPLICATION_DATE_PARAM, "2020-06-14T10:00:00")
                        .param(PRODUCT_ID_PARAM, "35455")
                        .param(BRAND_ID_PARAM, "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 2: Petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void should_Return_Applicable_Price_Test2() throws Exception {
        mockMvc.perform(get(PRICES_ENDPOINT)
                        .param(APPLICATION_DATE_PARAM, "2020-06-14T16:00:00")
                        .param(PRODUCT_ID_PARAM, "35455")
                        .param(BRAND_ID_PARAM, "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Test 3: Petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void should_Return_Applicable_Price_Test3() throws Exception {
        mockMvc.perform(get(PRICES_ENDPOINT)
                        .param(APPLICATION_DATE_PARAM, "2020-06-14T21:00:00")
                        .param(PRODUCT_ID_PARAM, "35455")
                        .param(BRAND_ID_PARAM, "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 4: Petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
    void should_Return_Applicable_Price_Test4() throws Exception {
        mockMvc.perform(get(PRICES_ENDPOINT)
                        .param(APPLICATION_DATE_PARAM, "2020-06-15T10:00:00")
                        .param(PRODUCT_ID_PARAM, "35455")
                        .param(BRAND_ID_PARAM, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    @DisplayName("Test 5: Petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
    void should_Return_Applicable_Price_Test5() throws Exception {
        mockMvc.perform(get(PRICES_ENDPOINT)
                        .param(APPLICATION_DATE_PARAM, "2020-06-16T21:00:00")
                        .param(PRODUCT_ID_PARAM, "35455")
                        .param(BRAND_ID_PARAM, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    @DisplayName("Escenario de Error: Producto no existente (404 Not Found)")
    void should_Return_404_When_Price_Not_Found() throws Exception {
        mockMvc.perform(get(PRICES_ENDPOINT)
                        .param(APPLICATION_DATE_PARAM, "2020-06-14T10:00:00")
                        .param(PRODUCT_ID_PARAM, "99999")
                        .param(BRAND_ID_PARAM, "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value(PRICES_ENDPOINT));
    }

    @Test
    @DisplayName("Escenario de Error: Parámetros faltantes (400 Bad Request)")
    void should_Return_400_When_Parameters_Are_Missing() throws Exception {
        mockMvc.perform(get(PRICES_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
