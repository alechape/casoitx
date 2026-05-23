package com.capitole.casoitx.infrastructure.adapters.inbound.rest.controller;

import com.capitole.casoitx.domain.exception.PriceNotFoundException;
import com.capitole.casoitx.domain.ports.inbound.FindApplicablePriceUseCase;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.api.PricesApi;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.dto.PriceFindResponse;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.mapper.PriceRestMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class PriceController implements PricesApi {

    private final FindApplicablePriceUseCase findApplicablePriceUseCase;
    private final PriceRestMapper priceRestMapper;

    public PriceController(FindApplicablePriceUseCase findApplicablePriceUseCase, PriceRestMapper priceRestMapper) {
        this.findApplicablePriceUseCase = findApplicablePriceUseCase;
        this.priceRestMapper = priceRestMapper;
    }

    @Override
    public ResponseEntity<PriceFindResponse> findApplicablePrice(
            LocalDateTime applicationDate,
            Long productId,
            Long brandId) {

        return findApplicablePriceUseCase.findApplicablePrice(applicationDate, productId, brandId)
                .map(price -> ResponseEntity.ok(priceRestMapper.toPriceFindResponse(price)))
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("Precio no encontrado para productId=%d, brandId=%d, applicationDate=%s",
                                productId, brandId, applicationDate)));
    }
}