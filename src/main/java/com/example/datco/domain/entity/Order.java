package com.example.datco.domain.entity;

import com.example.datco.domain.dto.OrderRequest;
import com.example.datco.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_request")
public class Order extends FullAuditEntity{
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        private String title;

        private String description;

        private String designFilePath;

        @Enumerated(EnumType.STRING)
        private Status status;

        public void updateDetails(OrderRequest orderRequest, String designFilePath) {
                this.title = orderRequest.title();
                this.description = orderRequest.description();
                this.designFilePath = designFilePath;
                this.status = orderRequest.status();
        }

        public void updateDetails(Status status) {
                this.status = status;
        }

}
