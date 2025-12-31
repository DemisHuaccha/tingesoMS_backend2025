package com.tingeso.tingesoMS_kardex.Repository;

import com.tingeso.tingesoMS_kardex.Dtos.CardexDto;
import com.tingeso.tingesoMS_kardex.Entities.Cardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardexRepositorie extends JpaRepository<Cardex,Long> {
    @Query("""
    SELECT new com.tingeso.tingesoMS_kardex.Dtos.CardexDto(
        c.id, c.moveDate, c.typeMove, c.description, c.amount, c.quantity, c.userEmail, c.toolId, c.loanId, c.clientRut)
    FROM Cardex c
    WHERE c.moveDate BETWEEN :startDate AND :endDate 
    ORDER BY c.moveDate ASC
    """)
    List<CardexDto> findCardexDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
    SELECT new com.tingeso.tingesoMS_kardex.Dtos.CardexDto(
        c.id, c.moveDate, c.typeMove, c.description, c.amount, c.quantity, c.userEmail, c.toolId, c.loanId, c.clientRut) 
    FROM Cardex c
    WHERE c.moveDate >= :startDate 
    ORDER BY c.moveDate ASC
    """)
    List<CardexDto> findMovementsFromDate(@Param("startDate") LocalDate startDate);

    @Query(""" 
    SELECT new com.tingeso.tingesoMS_kardex.Dtos.CardexDto(
        c.id, c.moveDate, c.typeMove, c.description, c.amount, c.quantity, c.userEmail, c.toolId, c.loanId, c.clientRut)
    FROM Cardex c
    WHERE c.moveDate <= :endDate 
    ORDER BY c.moveDate ASC
    """)
    List<CardexDto> findMovementsUntilDate(@Param("endDate") LocalDate endDate);


    @Query("""
    SELECT new com.tingeso.tingesoMS_kardex.Dtos.CardexDto(
        c.id, c.moveDate, c.typeMove, c.description, c.amount, c.quantity, c.userEmail, c.toolId, c.loanId, c.clientRut)
    FROM Cardex c
    WHERE c.toolId =:toolId
        AND c.moveDate BETWEEN :startDate AND :endDate 
    ORDER BY c.moveDate ASC
    """)
    List<CardexDto> findCardexDateRangeId(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("toolId") Long toolId);

    @Query("""
    SELECT new com.tingeso.tingesoMS_kardex.Dtos.CardexDto(
        c.id, c.moveDate, c.typeMove, c.description, c.amount, c.quantity, c.userEmail, c.toolId, c.loanId, c.clientRut) 
    FROM Cardex c
    WHERE c.toolId =:toolId
        AND c.moveDate >= :startDate 
    ORDER BY c.moveDate ASC
    """)
    List<CardexDto> findMovementsFromDateId(@Param("startDate") LocalDate startDate, @Param("toolId") Long toolId);

    @Query(""" 
    SELECT new com.tingeso.tingesoMS_kardex.Dtos.CardexDto(
        c.id, c.moveDate, c.typeMove, c.description, c.amount, c.quantity, c.userEmail, c.toolId, c.loanId, c.clientRut)
    FROM Cardex c
    WHERE c.toolId =:toolId
        AND c.moveDate <= :endDate 
    ORDER BY c.moveDate ASC
    """)
    List<CardexDto> findMovementsUntilDateId(@Param("endDate") LocalDate endDate, @Param("toolId") Long toolId);


    @Query("""
    SELECT new com.tingeso.tingesoMS_kardex.Dtos.CardexDto(
        c.id, c.moveDate, c.typeMove, c.description, c.amount, c.quantity, c.userEmail, c.toolId, c.loanId, c.clientRut)
    FROM Cardex c 
    WHERE c.toolId = :toolId 
    ORDER BY c.moveDate DESC
    """)
    List<CardexDto> findCardexByToolId(@Param("toolId") Long toolId);

    @Query("""
    SELECT new com.tingeso.tingesoMS_kardex.Dtos.CardexDto(
        c.id, c.moveDate, c.typeMove, c.description, c.amount, c.quantity, c.userEmail, c.toolId, c.loanId, c.clientRut)
    FROM Cardex c
    """)
    List<CardexDto> findAllCardex();
}
