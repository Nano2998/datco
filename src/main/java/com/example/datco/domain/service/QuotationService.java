package com.example.datco.domain.service;

import com.example.datco.domain.dto.QuotationRequest;
import com.example.datco.domain.dto.QuotationResponse;
import com.example.datco.domain.entity.Order;
import com.example.datco.domain.entity.OrderCompleted;
import com.example.datco.domain.entity.Quotation;
import com.example.datco.domain.entity.User;
import com.example.datco.domain.enums.Status;
import com.example.datco.domain.repository.OrderCompletedRepository;
import com.example.datco.domain.repository.OrderRepository;
import com.example.datco.domain.repository.QuotationRepository;
import com.example.datco.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final OrderCompletedRepository orderCompletedRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    @Transactional
    public QuotationResponse createQuotation(Long orderId, Long userId, QuotationRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Quotation quotation = request.toEntity(user, order);
        quotationRepository.save(quotation);

        return QuotationResponse.from(quotation);
    }

    public List<QuotationResponse> getQuotationsByOrder(Long orderId) {
        return quotationRepository.findByRequestId(orderId).stream().map(QuotationResponse::from).toList();
    }

    public QuotationResponse getQuotationById(Long quotationId) {
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 견적서를 찾을 수 없습니다."));
        return QuotationResponse.from(quotation);
    }

    @Transactional
    public QuotationResponse updateQuotationStatus(Long quotationId, Status newStatus, Long userId) {
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 견적서를 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        if (!quotation.getUser().getId().equals(userId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        Quotation updatedQuotation = Quotation.builder()
                .user(user)
                .name(quotation.getName())
                .request(quotation.getRequest())
                .estimatedCost(quotation.getEstimatedCost())
                .productionTime(quotation.getProductionTime())
                .status(newStatus)
                .build();

        quotationRepository.save(updatedQuotation);

        return QuotationResponse.from(updatedQuotation);
    }

    @Transactional(readOnly = false)
    public QuotationResponse selectSupplier(Long orderId, Long quotationId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        Quotation selectedQuotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 견적서를 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        selectedQuotation.updateStatus(Status.APPROVED);
        quotationRepository.save(selectedQuotation);


        List<Quotation> otherQuotations = quotationRepository.findByRequestId(orderId);
        for (Quotation quotation : otherQuotations) {
            if (!quotation.getId().equals(selectedQuotation.getId()) &&
                    !quotation.getStatus().equals(Status.QUOTE_CLOSED)) {
                quotation.updateStatus(Status.QUOTE_CLOSED);
                quotationRepository.save(quotation);
            }
        }

        OrderCompleted orderCompleted = OrderCompleted.from(order, user);
        orderCompletedRepository.save(orderCompleted);

        return QuotationResponse.from(selectedQuotation);
    }
}