package com.laur.bookshop.service;

import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.repositories.AppUserRepo;
import com.laur.bookshop.services.AppUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppUserServiceTests {

    @Mock
    private AppUserRepo repo;
    
    @InjectMocks
    private AppUserService service;

    private AutoCloseable autoCloseable;

    private static Integer NUMBER_OF_USERS = 10;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testFindAll() {
        // given
        List<AppUser> appUsers = generateAppUsers();

        // when
        when(repo.findAll()).thenReturn(appUsers);
        List<AppUser> result = service.findAllUsers();

        // then
        assertEquals(NUMBER_OF_USERS, result.size());
        verify(repo, times(1)).findAll();
        assertEquals(appUsers, result);
    }

    @Test
    public void testAdd() {
        // given

        // when

        // then
    }


    @Test
    public void testDeleteOne() {
        // given

        // when

        // then
    }

    @Test
    public void testDeleteMany() {
        // given

        // when

        // then
    }

    @Test
    public void testUpdate() {
        // given

        // when

        // then
    }

    @Test
    public void testLogin() {
        // given

        // when

        // then
    }

    private List<AppUser> generateAppUsers() {
        NUMBER_OF_USERS = 10;
        return List.of(
                // 1
                new AppUser(),
                // 2
                new AppUser(),
                // 3
                new AppUser(),
                // 4
                new AppUser(),
                // 5
                new AppUser(),
                // 6
                new AppUser(),
                // 7
                new AppUser(),
                // 8
                new AppUser(),
                // 9
                new AppUser(),
                // 10
                new AppUser()
        );
    }
}
