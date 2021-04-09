package com.codemerchant.suggestr.repository;

import com.codemerchant.suggestr.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    @Query("SELECT s FROM Suggestion s WHERE year(s.createdDate) = ?1 AND month(s.createdDate) = ?2 AND " +
            "day(s.createdDate) = ?3")

    List<Suggestion> findByCreatedYearAndMonthAndDay(int year, int month, int day);


}
