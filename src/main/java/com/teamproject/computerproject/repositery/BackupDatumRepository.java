package com.teamproject.computerproject.repositery;

import com.teamproject.computerproject.domain.BackupDatum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BackupDatumRepository extends JpaRepository<BackupDatum, Integer> {
    @Query("select b from BackupDatum b where Date(b.createdDate) = ?1 and b.itemAddress = ?2 order by b.itemPrice DESC limit  1")
    Optional<BackupDatum> findByCreatedDateAndItemAddressOrderByItemPriceDesc(LocalDate createdDate, String itemAddress);
    @Query("select b from BackupDatum b where b.createdDate = ?1 order by b.itemPrice DESC  limit  1")
    Optional<BackupDatum> findByCreatedDateOrderByItemPriceDesc(LocalDate createdDate);

    @Query("""
            select b from BackupDatum b
            where  b.itemAddress =?1
            order by b.index desc limit 1""")
    Optional<BackupDatum> findByItemAddressOrderByIndexDesc(String address);



}