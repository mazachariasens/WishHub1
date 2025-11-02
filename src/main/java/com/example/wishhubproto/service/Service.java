package com.example.wishhubproto.service;

import com.example.wishhubproto.model.User;
import com.example.wishhubproto.model.Wish;
import com.example.wishhubproto.repository.Repository;

@org.springframework.stereotype.Service
public class Service {

    private final Repository repository;

    public Repository(Repository repository) {
        this.repository = repository;
    }

    //Method 1 in repo - create user

    //receives a user object from the controller method and returns it
    public User createUserAndReturn(User user) {
        return repository.createUserAndReturn(user);
    }

    //Method 2 in repo - delete wish

    public boolean deleteWishByID(int wishID) {
        return repository.deleteWishById(wishID);
    }

    //Method 3 in repo -
    public Wish updateWishAndReturn(Wish wish) {
        return repository.updateWishAndReturn(wish);
    }




}
