package com.teamproject.computerproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "backup_data")
public class BackupDatum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index", nullable = false)
    private Integer index;

    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "item_name")
    private String itemName;

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

}