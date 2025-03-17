package com.example.datco.domain.controller;

import com.example.datco.common.SecurityUtils;
import com.example.datco.domain.dto.OrderRequest;
import com.example.datco.domain.dto.OrderResponse;
import com.example.datco.domain.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order controller api", description = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrderResponse> createOrder(
            @RequestPart("orderRequest") OrderRequest orderRequest,
            @RequestPart("designFile") MultipartFile designFile
    ) throws IOException {
        Long userId = SecurityUtils.getUserId();

        return ResponseEntity.ok().body(orderService.addOrder(orderRequest, userId, designFile));
    }

    @Operation(summary = "주문 삭제")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long orderId
    ) {
        Long userId = SecurityUtils.getUserId();
        orderService.deleteOrder(userId, orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }

    @Operation(summary = "주문 수정")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Long orderId,
            @RequestPart("designFile") MultipartFile designFile,
            @RequestBody OrderRequest orderRequest
    ) throws IOException {
        Long userId = SecurityUtils.getUserId();
        return ResponseEntity.ok().body(orderService.updateOrder(userId, orderId, orderRequest,designFile));
    }

    @Operation(summary = "주문 전체 조회")
    @GetMapping("")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @Operation(summary = "주문 단일 조회")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }
}