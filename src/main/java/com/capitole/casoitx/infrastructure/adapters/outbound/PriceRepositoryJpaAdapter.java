package com.capitole.casoitx.infrastructure.adapters.outbound;

import com.capitole.casoitx.domain.model.Price;
import com.capitole.casoitx.domain.ports.outbound.PriceRepositoryPort;
import com.capitole.casoitx.infrastructure.entity.PriceEntity;
import com.capitole.casoitx.infrastructure.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.time.LocalDateTime;

@Component
public class PriceRepositoryJpaAdapter implements PriceRepositoryPort {

    private final PriceRepository priceRepository;

    public PriceRepositoryJpaAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .map(this::toDomain);
    }

    private Price toDomain(PriceEntity entity) {
        return Price.builder()
            .id(entity.getId())
            .brandId(entity.getBrandId())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .priceList(entity.getPriceList())
            .productId(entity.getProductId())
            .priority(entity.getPriority())
            .price(entity.getPrice())
            .curr(entity.getCurr())
            .build();
    }
}
