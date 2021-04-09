package com.codemerchant.suggestr.SuggestionServiceTest;

import com.codemerchant.suggestr.model.Suggestion;
import com.codemerchant.suggestr.model.SuggestionType;
import com.codemerchant.suggestr.repository.SuggestionRepository;
import com.codemerchant.suggestr.service.SuggestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SuggestionServiceTest {
    @MockBean
    private SuggestionRepository suggestionRepository;

    private SuggestionService suggestionService;

    @Before
    public void getAllSuggestionsForToday_HappyPath_ShouldReturn1Suggestion() {
        //Given
        Suggestion suggestion = new Suggestion();
        suggestion.setSuggestion("808s and Heartbreak");
        suggestion.setType(SuggestionType.MUSIC_ALBUM);
        suggestion.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<Suggestion> suggestions = Arrays.asList(suggestion);
        LocalDate now = LocalDate.now();

        when(suggestionRepository.findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonth().getValue(), now.getDayOfMonth()))
                .thenReturn(suggestions);

        //When
        List<Suggestion> actualSuggestions = suggestionService.getAllSuggestionsForToday();

        //Then
        verify(suggestionRepository, times(1))
                .findByCreatedYearAndMonthAndDay(now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());
        assertThat(suggestions).isEqualTo(actualSuggestions);

    }

    @Test
    public void saveAll_HappyPath_ShouldSave2Suggestions() {
        //Given
        Suggestion suggestion = new Suggestion();
        suggestion.setSuggestion("GTA5");
        suggestion.setType(SuggestionType.GAME);
        suggestion.setCreatedBy("John");
        suggestion.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Suggestion suggestion1 = new Suggestion();
        suggestion1.setSuggestion("KOD");
        suggestion1.setType(SuggestionType.MUSIC_ALBUM);
        suggestion.setCreatedBy("John");

        List<Suggestion> suggestions = Arrays.asList(suggestion, suggestion1);

        when(suggestionRepository.saveAll(suggestions))
                .thenReturn(suggestions);

        //When
        List<Suggestion> saved = suggestionService.saveAll(suggestions);

        //Then
        assertThat(saved).isNotEmpty();
        verify(suggestionRepository, times(1))
                .saveAll(suggestions);


    }
}
