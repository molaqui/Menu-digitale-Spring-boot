package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Optional<User> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional;
        }
        return Optional.empty();
    }

    public boolean forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            sendEmail(user.getEmail(), user.getPassword());
            return true;
        } else {
            return false;
        }
    }

    private void sendEmail(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Password");
        message.setText("Your password is: " + password);
        mailSender.send(message);
    }

    public Optional<User> getUserByStoreName(String storeName) {
        return userRepository.findByStoreName(storeName);
    }

    public Optional<String> getWebsiteUrl(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getWebsiteUrl);
    }
}
