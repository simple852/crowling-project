package com.teamproject.computerproject.repositery;

import com.teamproject.computerproject.domain.BackupDatum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BackupDatumRepository extends JpaRepository<BackupDatum, Integer> {
    BackupDatum findFirstByIdOrderByIndexDesc(Integer id);


    @Query("""
            select b from BackupDatum b
            where  b.itemName = ?1
            order by b.index desc limit 1""")
    BackupDatum findBackData(String itemName);


    BackupDatum findByIdAndItemAddressAndItemName(Integer id, String itemAddress, String itemName);
    List<BackupDatum> findByIdOrderByIndexDesc(Integer id);
}