package com.supportportal.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.User;
import com.supportportal.domain.UserPrincipal;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.EmailNotFoundException;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.service.UserService;
import com.supportportal.utility.JWTTokenProvider;


public class UserControllerTest {

    @InjectMocks
    private UserContoller userController;

    @Mock
    private UserService userService;

    @Mock
    private JWTTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private Authentication authentication;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    public static final String User_Deleted_Successfully = "User Deleted Successfully";


  //  @Test
    public void testLogin() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        UserPrincipal userPrincipal = new UserPrincipal(user);
        String token = "token";

        when(userService.findUserByUsername(user.getUsername())).thenReturn(user);
        when(userDetailsService.loadUserByUsername(user.getUsername())).thenReturn(userPrincipal);
        when(jwtTokenProvider.generateJwtToken(userPrincipal)).thenReturn(token);

        ResponseEntity<User> response = userController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        assertEquals(token, response.getHeaders().get("Jwt-Token").get(0));
    }

  //  @Test
    public void testRegister() throws UserNotFoundException, UsernameExistException, EmailExistException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");

        when(userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail())).thenReturn(user);

        ResponseEntity<User> response = userController.register(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testAddNewUser() throws UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");

        MultipartFile profileImage = new MockMultipartFile("profileImage", "image.jpg", "image/jpeg", "image data".getBytes());

        when(userService.addNewUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getRole(), true, true, profileImage)).thenReturn(user);

        ResponseEntity<User> response = userController.addNewUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getRole(), "true", "true", profileImage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUpdateUser() throws NotAnImageFileException, UsernameExistException, EmailExistException, IOException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("newjohndoe");
        user.setEmail("newjohndoe@example.com");

        MultipartFile profileImage = new MockMultipartFile("profileImage", "image.jpg", "image/jpeg", "image data".getBytes());

        when(userService.updateUser(user.getUsername(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getRole(), true, true, profileImage)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getUsername(), user.getEmail(), user.getRole(), "true", "true", profileImage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setUsername("johndoe");

        when(userService.findUserByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<User> response = userController.getUser(user.getUsername());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetAllUser() {
        List<User> users = new ArrayList<>();
        users.add(new User(null, "John", "Doe", "johndoe", "johndoe@example.com", null, null, null, null, null, null, null, null, false, false, null));
        users.add(new User(null, "Jane", "Smith", "janesmith", "janesmith@example.com", null, null, null, null, null, null, null, null, false, false, null));

        when(userService.getUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testResetPassword() throws EmailNotFoundException {
        String email = "johndoe@example.com";

        ResponseEntity<HttpResponse> response = userController.resetPassword(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(" AN EMAIL WITH A NEW PASSWORD WAS SENT TO : JOHNDOE@EXAMPLE.COM", response.getBody().getMessage());

    }

    //@Test
    public void testDeleteUser() throws IOException {
        String username = "johndoe";

        ResponseEntity<HttpResponse> response = userController.deleteUser(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserControllerTest.User_Deleted_Successfully, response.getBody().getMessage());
    }


    @Test
    public void testUpdateProfileImage() throws UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        String username = "johndoe";
        MultipartFile profileImage = new MockMultipartFile("profileImage", "image.jpg", "image/jpeg", "image data".getBytes());

        User user = new User();
        user.setUsername(username);

        when(userService.updateProfileImage(username, profileImage)).thenReturn(user);

        ResponseEntity<User> response = userController.updateProfileImage(username, profileImage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
}
