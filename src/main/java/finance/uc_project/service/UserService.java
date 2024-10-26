package finance.uc_project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import finance.uc_project.enums.AccountState;
import finance.uc_project.enums.AccountStatus;
import finance.uc_project.model.User_account;
import finance.uc_project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User_account> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User_account> getUserByNumero(String numero) {
        return userRepository.findById(numero);
    }

    public User_account saveUser(User_account user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean numeroExists(String numero) {
        return userRepository.findByNumero(numero).isPresent();
    }

    public void deleteUser(String numero) {
        userRepository.deleteById(numero);
    }

    public User_account updateEtat(String numero, String etat) {
        User_account user = userRepository.findById(numero).orElseThrow(() -> new RuntimeException("User not found"));
        user.setAccountState(AccountState.valueOf(etat));
        return userRepository.save(user);
    }

    public User_account updateStatus(String numero, String status) {
        User_account user = userRepository.findById(numero).orElseThrow(() -> new RuntimeException("User not found"));
        user.setAccountStatus(AccountStatus.valueOf(status));
        return userRepository.save(user);
    }
}
