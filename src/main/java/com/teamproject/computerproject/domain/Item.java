package com.teamproject.computerproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Integer id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_price")
    private Integer itemPrice;

    @Column(name = "item_content", length = 45)
    private String itemContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImages = new ArrayList<>();

}