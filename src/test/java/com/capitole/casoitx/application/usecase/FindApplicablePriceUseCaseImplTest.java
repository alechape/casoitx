package com.capitole.casoitx.application.usecase;

import com.capitole.casoitx.domain.model.Price;
import com.capitole.casoitx.domain.ports.outbound.PriceRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("===== Unit Test - Find Applicable Price Use Case =====")
class FindApplicablePriceUseCaseImplTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private FindApplicablePriceUseCaseImpl findApplicablePriceUseCase;

    @Test
    @DisplayName("Should return price when repository returns one")
    void findApplicablePrice_WhenPriceFound_ReturnsPrice() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 35455L;
        Long brandId = 1L;
        Price expectedPrice = Price.builder()
                .productId(productId)
                .brandId(brandId)
                .build();

        when(priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(expectedPrice));

        Optional<Price> result = findApplicablePriceUseCase.findApplicablePrice(applicationDate, productId, brandId);

        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
        verify(priceRepositoryPort).findApplicablePrice(applicationDate, productId, brandId);
    }

    @Test
    @DisplayName("Should return empty when repository returns empty")
    void findApplicablePrice_WhenPriceNotFound_ReturnsEmpty() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 35455L;
        Long brandId = 1L;

        when(priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.empty());

        Optional<Price> result = findApplicablePriceUseCase.findApplicablePrice(applicationDate, productId, brandId);

        assertTrue(result.isEmpty());
        verify(priceRepositoryPort).findApplicablePrice(applicationDate, productId, brandId);
    }
}
