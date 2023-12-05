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

    long countByCategoryId(Integer categoryId);
    @Transactional
    @Modifying
    @Query("""
            update Item i set i.itemName = ?1, i.itemPrice = ?2, i.itemContent = ?3, i.itemImage = ?4 , i.itemGap = ?6
            where i.itemAddress = ?5""")
    int updateItemNameAndItemPriceAndItemContentAndItemImageByItemAddress(String itemName, Integer itemPrice, String itemContent, String itemImage, String itemAddress,Integer gapPrice);
    @Transactional
    @Modifying
    @Query("update Item i set i.itemName = ?1, i.itemPrice = ?2, i.itemContent = ?3 , i.itemImage = ?4 where i.itemAddress = ?4")
    int updateItemNameAndItemPriceAndItemContentByItemAddress(String itemName, Integer itemPrice, String itemContent,String itemImage, String itemAddress);



    List<Item> findByCategoryIdOrderById(Integer categoryId, Pageable pageable);


    List<Item> findByCategoryId(Integer categoryId);





}

//{
//        "titleClass": "span.title",
//        "contentClass": "u",
//        "priceClass": "em.prc_c",
//        "categoryId": 2,
//        "imageClass": "baseImage"
//        }