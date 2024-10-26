package finance.uc_project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance.uc_project.model.User_account;
import finance.uc_project.service.UserService;

@RestController
@RequestMapping("/api/users")
public class User_accountController {

    @Autowired
    private final UserService userService;

    public User_accountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User_account>> getAllUsers() {
        List<User_account> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<User_account> getUserByNumero(@PathVariable String numero) {
        Optional<User_account> user = userService.getUserByNumero(numero);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/check-numero")
    public ResponseEntity<Boolean> checkNumeroExists(@RequestParam String numero) {
        boolean exists = userService.numeroExists(numero);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/adduser")
    public ResponseEntity<User_account> addUser(@RequestBody User_account user) {
        User_account savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{numero}")
    public ResponseEntity<User_account> updateUser(@PathVariable String numero, @RequestBody User_account user) {
        if (!userService.getUserByNumero(numero).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setNumero(numero); // Ensure the numero is set correctly
        User_account updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deleteUser(@PathVariable String numero) {
        if (!userService.getUserByNumero(numero).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(numero);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("Authentication: " + authentication); // Log authentication details
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                // Use userDetails to get user information
                return ResponseEntity.ok(userDetails);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authenticated");
    }

    @PutMapping("/{numero}/etat")
    public ResponseEntity<User_account> updateEtat(@PathVariable String numero, @RequestParam String etat) {
        User_account updatedUser = userService.updateEtat(numero, etat);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{numero}/status")
    public ResponseEntity<User_account> updateStatus(@PathVariable String numero, @RequestParam String status) {
        User_account updatedUser = userService.updateStatus(numero, status);
        return ResponseEntity.ok(updatedUser);
    }
}
