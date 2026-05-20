package com.capitole.casoitx.application.usecase;

import com.capitole.casoitx.domain.model.Price;
import com.capitole.casoitx.domain.ports.inbound.FindApplicablePriceUseCase;
import com.capitole.casoitx.domain.ports.outbound.PriceRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FindApplicablePriceUseCaseImpl implements FindApplicablePriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    public FindApplicablePriceUseCaseImpl(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId);
    }
}
