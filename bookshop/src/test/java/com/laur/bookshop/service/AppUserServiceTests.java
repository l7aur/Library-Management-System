package com.laur.bookshop.service;

import com.laur.bookshop.config.enums.Role;
import com.laur.bookshop.model.AppUser;
import com.laur.bookshop.repositories.AppUserRepo;
import com.laur.bookshop.services.AppUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppUserServiceTests {

    @Mock
    private AppUserRepo repo;
    private static final Integer NUMBER_OF_USERS = 10;

    @InjectMocks
    private AppUserService service;

    private AutoCloseable autoCloseable;

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
        List<AppUser> appUsers = generateAppUsers();
        AppUser newAppUser = createNewAppUser();
        appUsers.add(newAppUser);

        // when
        when(repo.save(newAppUser)).thenReturn(newAppUser);
        AppUser result = service.addAppUser(newAppUser.toDTO());

        // then
        verify(repo, times(1)).save(newAppUser);
        assertEquals(newAppUser, result);
    }

    @Test
    public void testDeleteOne1() {
        // given
        List<AppUser> appUsers = generateAppUsers();
        AppUser deletedAppUser = appUsers.getFirst();
        appUsers.remove(deletedAppUser);

        // when
        doNothing().when(repo).deleteById(deletedAppUser.getId());
        service.deleteByIds(List.of(deletedAppUser.getId()));
        when(repo.findAll()).thenReturn(appUsers);

        // then
        verify(repo, times(1)).deleteById(deletedAppUser.getId());
        assertEquals(repo.findAll(), appUsers);
    }

    @Test
    public void testDeleteOne2() {
        // given
        List<AppUser> appUsers = generateAppUsers();
        AppUser deletedAppUser = appUsers.getLast();
        appUsers.remove(deletedAppUser);

        // when
        doNothing().when(repo).deleteById(deletedAppUser.getId());
        service.deleteByIds(List.of(deletedAppUser.getId()));
        when(repo.findAll()).thenReturn(appUsers);

        // then
        verify(repo, times(1)).deleteById(deletedAppUser.getId());
        assertEquals(repo.findAll(), appUsers);
    }

    @Test
    public void testDeleteMany() {
        // given
        List<AppUser> appUsers = generateAppUsers();
        List<AppUser> deletedAppUsers = List.of(
                appUsers.getFirst(),
                appUsers.getLast(),
                appUsers.get(3)
        );
        appUsers.removeAll(deletedAppUsers);

        // when
        for(AppUser appUser : deletedAppUsers) {
            doNothing().when(repo).deleteById(appUser.getId());
        }
        service.deleteByIds(deletedAppUsers.stream().map(AppUser::getId).toList());
        when(repo.findAll()).thenReturn(appUsers);

        // then
        for (AppUser deleted : deletedAppUsers) {
            verify(repo, times(1)).deleteById(deleted.getId());
        }
        assertEquals(appUsers, repo.findAll());
    }

    @Test
    public void testUpdate() {
        // given
        UUID userId = UUID.randomUUID();
        AppUser existingUser = createNewAppUser(userId);
        AppUser updatedUser = createUpdatedUser(userId);

        // when
        when(repo.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        when(repo.save(updatedUser)).thenReturn(updatedUser);
        AppUser result = service.updateAppUser(updatedUser.toDTO());

        // then
        verify(repo, times(1)).findById(userId);
        verify(repo, times(1)).save(updatedUser);
        assertEquals(updatedUser, result);
    }

    private List<AppUser> generateAppUsers() {
        List<AppUser> users = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            AppUser user = new AppUser();
            user.setId(UUID.randomUUID());
            user.setUsername("user" + i);
            user.setPassword("Password!" + i);
            user.setRole(Role.CUSTOMER);
            user.setFirstName("FirstName" + i);
            user.setLastName("LastName" + i);
            users.add(user);
        }
        return users;
    }

    private AppUser createNewAppUser() {
        AppUser newAppUser = new AppUser();
        newAppUser.setUsername("user" + NUMBER_OF_USERS);
        newAppUser.setPassword("Password!" + NUMBER_OF_USERS);
        newAppUser.setRole(Role.CUSTOMER);
        newAppUser.setFirstName("FirstName" + NUMBER_OF_USERS);
        newAppUser.setLastName("LastName" + NUMBER_OF_USERS);
        return newAppUser;
    }

    private AppUser createNewAppUser(UUID userId) {
        AppUser existingUser = new AppUser();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("oldPassword!1");
        existingUser.setRole(Role.ADMIN);
        existingUser.setFirstName("OldFirstName");
        existingUser.setLastName("OldLastName");
        return existingUser;
    }

    private AppUser createUpdatedUser(UUID userId) {
        AppUser updatedUser = new AppUser();
        updatedUser.setId(userId);
        updatedUser.setUsername("newUsername");
        updatedUser.setPassword("newPassword!1");
        updatedUser.setRole(Role.ADMIN);
        updatedUser.setFirstName("NewFirstName");
        updatedUser.setLastName("NewLastName");
        return updatedUser;
    }
}
