package com.codemerchant.suggestr.UserServiceTest;


import com.codemerchant.suggestr.model.User;
import com.codemerchant.suggestr.repository.UserRepository;
import com.codemerchant.suggestr.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith((SpringRunner.class))
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void init() {
        this.userService = new UserService(userRepository);
    }

    @Test
    public void getAllSuggestionsForToday_HappyPath_ShouldReturn1Suggestion() {
        //Given

        User user = new User();
        user.setUsername("John");
        user.setPassword("12345");
        user.setRole("USER");

        when(userRepository.findByUsername("John"))
                .thenReturn(user);

        //When
        UserDetails actual = userService.loadUserByUsername("John");

        //Then
        verify(userRepository, times(1))
                .findByUsername("John");
    }
}
