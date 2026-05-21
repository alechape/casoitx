package com.capitole.casoitx.infrastructure.adapters.inbound.rest.controller;

import com.capitole.casoitx.domain.ports.inbound.FindApplicablePriceUseCase;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.dto.PriceFindResponse;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.mapper.PriceRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {

    private final FindApplicablePriceUseCase findApplicablePriceUseCase;
    private final PriceRestMapper priceRestMapper;

    public PriceController(FindApplicablePriceUseCase findApplicablePriceUseCase, PriceRestMapper priceRestMapper) {
        this.findApplicablePriceUseCase = findApplicablePriceUseCase;
        this.priceRestMapper = priceRestMapper;
    }

    @GetMapping
    public ResponseEntity<PriceFindResponse> findApplicablePrice(
            @RequestParam LocalDateTime applicationDate,
            @RequestParam Long productId,
            @RequestParam Long brandId) {

        return findApplicablePriceUseCase.findApplicablePrice(applicationDate, productId, brandId)
                .map(price -> ResponseEntity.ok(priceRestMapper.toPriceFindResponse(price)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}