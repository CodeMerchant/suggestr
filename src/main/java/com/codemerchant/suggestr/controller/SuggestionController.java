package com.codemerchant.suggestr.controller;

import com.codemerchant.suggestr.model.Suggestion;
import com.codemerchant.suggestr.model.SuggestionType;
import com.codemerchant.suggestr.service.SuggestionService;
import lombok.AllArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@AllArgsConstructor
@Controller
public class SuggestionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SuggestionController.class);

    private final SuggestionService suggestionService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        List<Suggestion> allSuggestions = suggestionService.getAllSuggestionsForToday();

        Map<SuggestionType, List<Suggestion>> groupedSuggestions = allSuggestions.stream()
                .collect(Collectors.groupingBy(Suggestion::getType));

        model.addAttribute("movieSuggestions", groupedSuggestions.get(SuggestionType.MOVIE));
        model.addAttribute("musicAlbumSuggestions", groupedSuggestions.get(SuggestionType.MUSIC_ALBUM));
        model.addAttribute("gameSuggestions", groupedSuggestions.get(SuggestionType.GAME));

        return "suggestion";
    }

    @PostMapping("/suggestion")
    public String createSuggestion(@RequestParam(name = "movieSuggestion", required = false)
                                           String movieSuggestion,
                                   @RequestParam(name = "musicAlbumSuggestion", required = false)
                                           String musicAlbumSuggestion,
                                   @RequestParam(name = "gameSuggestion", required = false)
                                           String gameSuggestion) {


        List<Suggestion> suggestions = new ArrayList<>();

        if (StringUtils.isNotEmpty(movieSuggestion)) {
            suggestions.add(getSuggestion(movieSuggestion, SuggestionType.MOVIE));
        }
        if (StringUtils.isNotEmpty(musicAlbumSuggestion)) {
            suggestions.add(getSuggestion(musicAlbumSuggestion, SuggestionType.MUSIC_ALBUM));
        }
        if (StringUtils.isNotEmpty(gameSuggestion)) {
            suggestions.add(getSuggestion(gameSuggestion, SuggestionType.GAME));
        }

        if (!suggestions.isEmpty()) {
            LOGGER.info("Saved {}", suggestionService.saveAll(suggestions));
        }

        return "redirect:/";
    }


    private Suggestion getSuggestion(String suggestion, SuggestionType suggestionType) {
        Suggestion suggestionObject = new Suggestion();
        suggestionObject.setType(suggestionType);
        suggestionObject.setSuggestion(suggestion);

        return suggestionObject;
    }


}
