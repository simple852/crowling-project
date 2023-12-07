package com.teamproject.computerproject.repositery;

import com.teamproject.computerproject.domain.Item;
import org.springframework.data.domain.Pageable;
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
    @Query("update Item i set i.itemGap = ?1 where i.itemAddress = ?2")
    int updateItemGapByItemAddress(Integer itemGap, String itemAddress);
    List<Item> findByItemGap(Integer itemGap);

    long countByCategoryId(Integer categoryId);
    @Transactional
    @Modifying
    @Query("""
            update Item i set i.itemName = ?1, i.itemPrice = ?2, i.itemContent = ?3, i.itemImage = ?4
            where i.itemAddress = ?5""")
    int updateItemNameAndItemPriceAndItemContentAndItemImageByItemAddress(String itemName, Integer itemPrice, String itemContent, String itemImage, String itemAddress);
    @Transactional
    @Modifying
    @Query("update Item i set i.itemName = ?1, i.itemPrice = ?2, i.itemContent = ?3 , i.itemImage = ?4 where i.itemAddress = ?4")
    int updateItemNameAndItemPriceAndItemContentByItemAddress(String itemName, Integer itemPrice, String itemContent,String itemImage, String itemAddress);


    @Query("select all from Item as all where all.categoryId = ?1 order by abs(all.itemGap) desc, all.id desc")
    List<Item> findByCategoryIdOrderByItemGapDesc(Integer categoryId, Pageable pageable);


    List<Item> findByCategoryId(Integer categoryId);

    @Query("select all from Item as all order by abs(all.itemGap) desc, all.id desc")
    List<Item> findAllOrder(Pageable page);





}

//{
//        "titleClass": "span.title",
//        "contentClass": "u",
//        "priceClass": "em.prc_c",
//        "categoryId": 2,
//        "imageClass": "baseImage"
//        }