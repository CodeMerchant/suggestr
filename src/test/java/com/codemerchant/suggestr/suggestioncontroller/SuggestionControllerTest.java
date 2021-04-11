package com.codemerchant.suggestr.suggestioncontroller;

import com.codemerchant.suggestr.controller.SuggestionController;
import com.codemerchant.suggestr.model.Suggestion;
import com.codemerchant.suggestr.model.SuggestionType;
import com.codemerchant.suggestr.service.SuggestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SuggestionController.class)
public class SuggestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuggestionService suggestionService;

    @Test
    public void saveSuggestions_HappyPath_ShouldReturnStatus302() throws Exception {
        //When
        ResultActions resultActions = mockMvc.perform(post("/suggestion")
                .with(csrf())
                .with(user("John")
                        .roles("USER"))
                .param("movieSuggestion", "Test Movie"));


        //Then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(suggestionService, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(suggestionService);
    }

    @Test
    public void getSuggestions_HappyPath_ShouldReturnStatus200() throws Exception{
        //Given
        Suggestion suggestion = new Suggestion();

        suggestion.setSuggestion("Test Movie");
        suggestion.setType(SuggestionType.MOVIE);
        suggestion.setCreatedBy("John");
        suggestion.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Suggestion suggestion1 = new Suggestion();

        suggestion1.setSuggestion("Test Game");
        suggestion1.setType(SuggestionType.GAME);
        suggestion1.setCreatedBy("Jane");
        suggestion1.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<Suggestion> suggestions = Arrays.asList(suggestion,suggestion1);
        when(suggestionService.getAllSuggestionsForToday())
                .thenReturn(suggestions);

        //When
        ResultActions resultActions = mockMvc.perform(get("/").with(user("John").roles("USER")));

        //Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("suggestion"))
                .andExpect(model().attribute("movieSuggestions", hasSize(1)))
                .andExpect(model().attribute("movieSuggestions", hasItem(
                        allOf(
                                hasProperty("createdBy", is("John")),
                                hasProperty("suggestion", is("Test Movie"))
                        )
                )))
                .andExpect(model().attribute("gameSuggestions", hasSize(1)))
                .andExpect(model().attribute("gameSuggestions", hasItem(
                        allOf(
                                hasProperty("createdBy", is("Jane")),
                                hasProperty("suggestion", is("Test Game"))
                        )
                )));

        verify(suggestionService,times(1)).getAllSuggestionsForToday();
        verifyNoMoreInteractions(suggestionService);

    }

}
