package com.capitole.casoitx.infrastructure.adapters.outbound;

import com.capitole.casoitx.domain.model.Price;
import com.capitole.casoitx.infrastructure.entity.PriceEntity;
import com.capitole.casoitx.infrastructure.repository.PriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("===== Unit Test - Price Repository Jpa Adapter =====")
class PriceRepositoryJpaAdapterTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceRepositoryJpaAdapter priceRepositoryJpaAdapter;

    @Test
    @DisplayName("Should return domain Price when entity is found")
    void findApplicablePrice_WhenEntityFound_ReturnsDomainPrice() {

        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 35455L;
        Long brandId = 1L;
        
        PriceEntity entity = PriceEntity.builder()
                .id(1L)
                .brandId(brandId)
                .productId(productId)
                .startDate(applicationDate.minusDays(1))
                .endDate(applicationDate.plusDays(1))
                .priceList(1L)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .curr("EUR")
                .build();

        when(priceRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate))
                .thenReturn(Optional.of(entity));

        Optional<Price> result = priceRepositoryJpaAdapter.findApplicablePrice(applicationDate, productId, brandId);

        assertTrue(result.isPresent());
        Price domainPrice = result.get();
        assertEquals(entity.getId(), domainPrice.getId());
        assertEquals(entity.getProductId(), domainPrice.getProductId());
        assertEquals(entity.getBrandId(), domainPrice.getBrandId());
        assertEquals(entity.getPrice(), domainPrice.getPrice());
        assertEquals(entity.getCurr(), domainPrice.getCurr());
        
        verify(priceRepository).findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate);
    }

    @Test
    @DisplayName("Should return empty when entity is not found")
    void findApplicablePrice_WhenEntityNotFound_ReturnsEmpty() {
        LocalDateTime applicationDate = LocalDateTime.now();
        Long productId = 35455L;
        Long brandId = 1L;

        when(priceRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate))
                .thenReturn(Optional.empty());

        Optional<Price> result = priceRepositoryJpaAdapter.findApplicablePrice(applicationDate, productId, brandId);

        assertTrue(result.isEmpty());
        verify(priceRepository).findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applicationDate, applicationDate);
    }
}
