package com.capitole.casoitx.infrastructure.adapters.inbound.rest.controller;

import com.capitole.casoitx.domain.model.Price;
import com.capitole.casoitx.domain.ports.inbound.FindApplicablePriceUseCase;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.dto.PriceFindResponse;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.exception.GlobalExceptionHandler;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.mapper.PriceRestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("===== Unit Test - Price Controller Test =====")
class PriceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FindApplicablePriceUseCase findApplicablePriceUseCase;

    @Mock
    private PriceRestMapper priceRestMapper;

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(priceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Debe retornar 200 OK cuando existe precio")
    void findApplicablePrice_WhenPriceExists_ReturnsOk() throws Exception {

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        Long productId = 35455L;
        Long brandId = 1L;

        Price price = new Price();

        PriceFindResponse response = new PriceFindResponse();
        response.setProductId(productId);
        response.setBrandId(brandId);
        response.setPrice(35.50);

        when(findApplicablePriceUseCase.findApplicablePrice(
                eq(applicationDate),
                eq(productId),
                eq(brandId)))
                .thenReturn(Optional.of(price));

        when(priceRestMapper.toPriceFindResponse(price))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId));

        verify(findApplicablePriceUseCase).findApplicablePrice(applicationDate, productId, brandId);
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found cuando no existe precio para los criterios dados")
    void findApplicablePrice_WhenPriceNotFound_ReturnsNotFound() throws Exception {

        when(findApplicablePriceUseCase.findApplicablePrice(any(), any(), any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/prices")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}