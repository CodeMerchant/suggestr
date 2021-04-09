package com.codemerchant.suggestr.service;

import com.codemerchant.suggestr.model.Suggestion;
import com.codemerchant.suggestr.repository.SuggestionRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;

/**
 * class that encapsulates the business logic using service methods
 */
@Service //marking the class as a service
@Transactional(readOnly = true)
public class SuggestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionService.class);

    private final SuggestionRepository suggestionRepository;

    public SuggestionService(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Suggestion> saveAll(List<Suggestion> suggestions) {
        LOGGER.info("Saving", suggestions);

        return suggestionRepository.saveAll(suggestions);

    }
    public List<Suggestion> getAllSuggestionsForToday(){
        LocalDate localDate = LocalDate.now();

        return suggestionRepository.findByCreatedYearAndMonthAndDay(localDate.getYear(), localDate.getMonth().getValue(), localDate.getDayOfMonth());
    }

}
