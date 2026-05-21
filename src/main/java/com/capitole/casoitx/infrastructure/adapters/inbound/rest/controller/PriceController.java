package com.capitole.casoitx.infrastructure.adapters.inbound.rest.controller;

import com.capitole.casoitx.domain.exception.PriceNotFoundException;
import com.capitole.casoitx.domain.ports.inbound.FindApplicablePriceUseCase;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.dto.PriceFindResponse;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.mapper.PriceRestMapper;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@Validated
public class PriceController {

    private final FindApplicablePriceUseCase findApplicablePriceUseCase;
    private final PriceRestMapper priceRestMapper;

    public PriceController(FindApplicablePriceUseCase findApplicablePriceUseCase, PriceRestMapper priceRestMapper) {
        this.findApplicablePriceUseCase = findApplicablePriceUseCase;
        this.priceRestMapper = priceRestMapper;
    }

    @GetMapping
    public ResponseEntity<PriceFindResponse> findApplicablePrice(
            @RequestParam @NotNull(message = "applicationDate es requerido")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam @NotNull(message = "productId es requerido")
            @Min(value = 1, message = "productId debe ser mayor a 0") Long productId,
            @RequestParam @NotNull(message = "brandId es requerido")
            @Min(value = 1, message = "brandId debe ser mayor a 0") Long brandId) {

        return findApplicablePriceUseCase.findApplicablePrice(applicationDate, productId, brandId)
                .map(price -> ResponseEntity.ok(priceRestMapper.toPriceFindResponse(price)))
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("Precio no encontrado para productId=%d, brandId=%d, applicationDate=%s",
                                productId, brandId, applicationDate)));
    }
}