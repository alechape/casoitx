package com.capitole.casoitx.infrastructure.adapters.inbound.rest.mapper;

import com.capitole.casoitx.domain.model.Price;
import com.capitole.casoitx.infrastructure.adapters.inbound.rest.dto.PriceFindResponse;
import org.springframework.stereotype.Component;

@Component
public class PriceRestMapper {

    public PriceFindResponse toPriceFindResponse(Price price) {

        return PriceFindResponse.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .curr(price.getCurr())
                .build();
    }
}