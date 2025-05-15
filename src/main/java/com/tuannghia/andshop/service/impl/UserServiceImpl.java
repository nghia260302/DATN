package com.tuannghia.andshop.service.impl;

import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.entity.Role;
import com.tuannghia.andshop.entity.User;
import com.tuannghia.andshop.repository.ClotheRepository;
import com.tuannghia.andshop.repository.UserRepository;
import com.tuannghia.andshop.service.UserService;
import com.tuannghia.andshop.enums.Status;
import com.tuannghia.andshop.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClotheRepository clotheRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> getAllUserOrderByCreatedDate(Pageable pageable) {
        return userRepository.findAllByOrderByCreatedAtDesc(pageable);
    }


    @Override
    public void addBookToUser(Long userId, Long ClotheId) {
        User user = userRepository.findById(userId).orElse(null);
        Clothe clothe = clotheRepository.findById(ClotheId).orElse(null);

        if (user != null && clothe != null) {
            user.addFavoriteBook(clothe);
            userRepository.save(user);
        }
    }

    @Override
    public void removeBookFromUser(Long userId, Long ClotheId) {
        User user = userRepository.findById(userId).orElse(null);
        Clothe clothe = clotheRepository.findById(ClotheId).orElse(null);

        if (user != null && clothe != null) {
            user.removeFavoriteBook(clothe);
            userRepository.save(user);
        }
    }

    @Override
    public Long countUser() {
        return userRepository.count();
    }

    @Override
    public void saveUserForWebSocket(User user) {
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    @Override
    public void disconnectUser(User user) {
        var storedUser = userRepository.findById(user.getId()).orElse(null);
        if(storedUser != null){
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    @Override
    public List<User> findConnedUsers() {
        return userRepository.findAllByStatus(Status.ONLINE);
    }


    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }

        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        user.getRoles().add(userRole);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return true;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
