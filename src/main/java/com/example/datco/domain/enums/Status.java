package com.example.datco.domain.enums;

public enum Status {
    PENDING_REVIEW("검토중"),
    APPROVED("승인됨"),
    QUOTE_REQUESTED("견적요청됨"),
    QUOTE_RECEIVING("견적 접수중"),
    QUOTE_CLOSED("견적마감"),
    ORDER_CONFIRMED("발주 확정됨"),
    COMPLETED("완료됨");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
