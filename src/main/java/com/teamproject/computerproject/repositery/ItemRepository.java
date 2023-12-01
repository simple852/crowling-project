package com.teamproject.computerproject.repositery;

import com.teamproject.computerproject.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Transactional
    @Modifying
    @Query("update Item i set i.itemName = ?1, i.itemPrice = ?2, i.itemContent = ?3 where i.itemAddress = ?4")
    int updateItemNameAndItemPriceAndItemContentByItemAddress(String itemName, Integer itemPrice, String itemContent, String itemAddress);
    List<Item> findByCategoryId(Integer categoryId);



}