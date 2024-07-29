package com.example.demo.Service;

import com.example.demo.DAO.ChefRepository;
import com.example.demo.DAO.UserRepository;
import com.example.demo.Entity.Chef;
import com.example.demo.Entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Chef> getAllChefs(Long userId) {
        return chefRepository.findByUserId(userId);
    }

    public Chef addChef(Chef chef, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            chef.setUser(user);
            return chefRepository.save(chef);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Chef updateChef(Long id, Chef chefDetails, Long userId) {
        Optional<Chef> optionalChef = chefRepository.findByIdAndUserId(id, userId);
        if (optionalChef.isPresent()) {
            Chef chef = optionalChef.get();
            chef.setName(chefDetails.getName());
            chef.setDesignation(chefDetails.getDesignation());
            chef.setImage(chefDetails.getImage());
            chef.setFacebookUrl(chefDetails.getFacebookUrl());
            chef.setTwitterUrl(chefDetails.getTwitterUrl());
            chef.setInstagramUrl(chefDetails.getInstagramUrl());
            return chefRepository.save(chef);
        } else {
            throw new RuntimeException("Chef not found");
        }
    }

    public void deleteChef(Long id, Long userId) {
        Optional<Chef> optionalChef = chefRepository.findByIdAndUserId(id, userId);
        if (optionalChef.isPresent()) {
            chefRepository.delete(optionalChef.get());
        } else {
            throw new RuntimeException("Chef not found");
        }
    }
}
