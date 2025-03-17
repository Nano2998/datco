package com.example.datco.domain.service;

import com.example.datco.domain.dto.OrderRequest;
import com.example.datco.domain.dto.OrderResponse;
import com.example.datco.domain.entity.Order;
import com.example.datco.domain.entity.User;
import com.example.datco.domain.enums.Role;
import com.example.datco.domain.repository.OrderRepository;
import com.example.datco.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public OrderResponse addOrder(OrderRequest orderRequest, Long userId, MultipartFile designFile) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        String designFilePath = fileStorageService.storeFile(designFile);
        Order order = orderRequest.toEntity(user, designFilePath);
        orderRepository.save(order);

        return OrderResponse.fromEntity(order);
    }

    @Transactional
    public void deleteOrder(Long userId, Long orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("오더를 찾을 수 없습니다."));

        if (!user.getId().equals(order.getUser().getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }

        orderRepository.delete(order);
    }

    @Transactional
    public OrderResponse updateOrder(Long userId, Long orderId, OrderRequest updateRequest, MultipartFile designFile) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("오더를 찾을 수 없습니다."));

        if (!user.getId().equals(order.getUser().getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }

        String designFilePath = fileStorageService.storeFile(designFile);
        order.updateDetails(updateRequest, designFilePath);
        orderRepository.save(order);

        return OrderResponse.fromEntity(order);
    }


    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }


    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("오더를 찾을 수 없습니다."));

        return OrderResponse.fromEntity(order);
    }
}
