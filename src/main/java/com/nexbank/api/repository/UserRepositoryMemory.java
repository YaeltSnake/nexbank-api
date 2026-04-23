package com.nexbank.api.repository;

import com.nexbank.api.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryMemory implements UserRepository{

    private final Map<Long, User> storage = new HashMap<>();
    private long idCounter = 1;

    @Override
    public User save(User user) {
        if (user.getId() == null){
            user.setId(idCounter++);
        }
        storage.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return storage.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean existsByEmail(String email) {
        return storage.values().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
