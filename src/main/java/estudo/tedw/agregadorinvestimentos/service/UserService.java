package estudo.tedw.agregadorinvestimentos.service;

import estudo.tedw.agregadorinvestimentos.controller.CreateUserDto;
import estudo.tedw.agregadorinvestimentos.controller.UpdateUserDto;
import estudo.tedw.agregadorinvestimentos.entity.User;
import estudo.tedw.agregadorinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        var entity = new User(
                null,
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);
        return userSaved.getUserID();
    }

    public Optional<User> getUserById(String userID) {
        return userRepository.findById(UUID.fromString(userID));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userID, UpdateUserDto updateUserDto) {
        var ID = UUID.fromString(userID);
        var userEntity = userRepository.findById(ID);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userID) {
        var ID = UUID.fromString(userID);
        var userExists = userRepository.existsById(ID);

        if (userExists) {
            userRepository.deleteById(ID);
        }
    }
}
