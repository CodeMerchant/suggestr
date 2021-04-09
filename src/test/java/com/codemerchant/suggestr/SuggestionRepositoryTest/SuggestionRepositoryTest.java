package com.codemerchant.suggestr.SuggestionRepositoryTest;

import com.codemerchant.suggestr.model.Suggestion;
import com.codemerchant.suggestr.model.SuggestionType;
import com.codemerchant.suggestr.repository.SuggestionRepository;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SuggestionRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Test
    public void findCreatedYearAndMonthAndDay_HappyPath_ShouldReturn1Suggestion() {
        //Given
        Suggestion suggestion = new Suggestion();
        suggestion.setSuggestion("John Wick");
        suggestion.setType(SuggestionType.MOVIE);
        suggestion.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        testEntityManager.persist(suggestion);
        testEntityManager.flush();

        //When
        LocalDate now = LocalDate.now();
        List<Suggestion> suggestions = suggestionRepository
                .findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());

        //Then
        assertThat(suggestions).hasSize(1);
        assertThat(suggestions.get(0)).hasFieldOrPropertyWithValue("Suggestion", "John Wick");


    }

    @Test
    public void save_HappyPath_ShouldSave1Suggestion() {
        //Given
        Suggestion suggestion = new Suggestion();
        suggestion.setSuggestion("Harry Potter");
        suggestion.setType(SuggestionType.MOVIE);
        suggestion.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        //When
        Suggestion saved = suggestionRepository.save(suggestion);

        //Then
        assertThat(testEntityManager.find(Suggestion.class, saved.getId())).isEqualTo(saved);
    }
}
