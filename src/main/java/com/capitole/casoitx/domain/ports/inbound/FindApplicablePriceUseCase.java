package com.capitole.casoitx.domain.ports.inbound;

import com.capitole.casoitx.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FindApplicablePriceUseCase {
    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
