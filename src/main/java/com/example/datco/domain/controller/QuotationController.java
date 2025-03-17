package com.example.datco.domain.controller;

import com.example.datco.common.SecurityUtils;
import com.example.datco.domain.dto.QuotationRequest;
import com.example.datco.domain.dto.QuotationResponse;
import com.example.datco.domain.enums.Status;
import com.example.datco.domain.service.QuotationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotations")
@RequiredArgsConstructor
@Tag(name = "Quotations controller api", description = "견적서 API")
public class QuotationController {

    private final QuotationService quotationService;

    @Operation(summary = "견적서 생성")
    @PostMapping("/order/{orderId}/")
    public ResponseEntity<QuotationResponse> createQuotation(
            @PathVariable Long orderId,
            @RequestBody QuotationRequest request
    ) {
        Long userId = SecurityUtils.getUserId();

        return ResponseEntity.ok().body(quotationService.createQuotation(orderId, userId, request));
    }

    @Operation(summary = "견적서 리스트 조회")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<QuotationResponse>> getQuotationsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(quotationService.getQuotationsByOrder(orderId));
    }

    @Operation(summary = "견적서 상세 조회")
    @GetMapping("/{quotationId}")
    public ResponseEntity<QuotationResponse> getQuotationById(@PathVariable Long quotationId) {
        return ResponseEntity.ok().body(quotationService.getQuotationById(quotationId));
    }

    @Operation(summary = "견적서 업데이트")
    @PatchMapping("/{quotationId}/status")
    public ResponseEntity<QuotationResponse> updateQuotationStatus(
            @PathVariable Long quotationId,
            @RequestParam Status status) {
        Long userId = SecurityUtils.getUserId();

        return ResponseEntity.ok().body(quotationService.updateQuotationStatus(quotationId, status,userId));
    }

    @Operation(summary = "견적서 공급사 선정")
    @PostMapping("/{orderId}/select/{quotationId}")
    public ResponseEntity<QuotationResponse> selectSupplier(
            @PathVariable Long orderId,
            @PathVariable Long quotationId
    ) {
        Long userId = SecurityUtils.getUserId();

        return ResponseEntity.ok().body(quotationService.selectSupplier(orderId, quotationId, userId));
    }
}