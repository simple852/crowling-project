package com.teamproject.computerproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "backup_data")

public class BackupDatum  extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index", nullable = false)
    private Integer index;

    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "item_name")
    private String itemName;

    @ColumnDefault(value = "0") //default 0
    @Column(name = "item_price")
    private Integer itemPrice;

    @Lob
    @Column(name = "item_content")
    private String itemContent;

    @Column(name = "category_id")
    private Integer categoryId;

    @Size(max = 255)
    @Column(name = "item_address")
    private String itemAddress;

    @Size(max = 255)
    @Column(name = "item_image")
    private String itemImage;



    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}