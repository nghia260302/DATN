package com.tuannghia.andshop.service;

import com.tuannghia.andshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<User> getAllUserOrderByCreatedDate(Pageable pageable);

    User getUserById(Long userId);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(Long userId);

    boolean registerUser(User user);

    void deleteUserById(Long id);

    void saveUser(User user);

    void addBookToUser(Long userId, Long ClotheId);

    void removeBookFromUser(Long userId, Long ClotheId);

    Long countUser();

    void saveUserForWebSocket(User user);

    void disconnectUser(User user);

    List<User> findConnedUsers();

}
