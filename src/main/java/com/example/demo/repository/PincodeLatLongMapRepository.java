package com.example.demo.repository;

import com.example.demo.entity.PincodeLatLongMapTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodeLatLongMapRepository extends JpaRepository<PincodeLatLongMapTbl, Integer> {

    @Query("select tbl from PincodeLatLongMapTbl tbl where tbl.pincode = :pincode ")
    PincodeLatLongMapTbl findByPincode(@Param("pincode") String pincode);
}
