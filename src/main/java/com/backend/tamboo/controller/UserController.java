package com.backend.tamboo.controller;

import com.backend.tamboo.dto.UserRequest;
import com.backend.tamboo.dto.UserResponseDTO;
import com.backend.tamboo.entity.Role;
import com.backend.tamboo.entity.User;
import com.backend.tamboo.repository.RoleRepository;
import com.backend.tamboo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        logger.info("Richiesta informazioni utente per ID: {}", id);

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            logger.warn("Utente non trovato con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Utente non trovato"));
        }

        User user = userOpt.get();

        UserResponseDTO userDTO = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getDate(),
                user.getGender(),
                user.getEmail(),
                user.getDescription(),
                user.getRole().getId()
        );

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserRequest request) {
        logger.info("Richiesta di aggiornamento dati utente per ID: {}", id);

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            logger.warn("Utente non trovato con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Utente non trovato"));
        }

        User user = userOpt.get();

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getSurname() != null) {
            user.setSurname(request.getSurname());
        }

        if (request.getBirthday() != null) {
            user.setDate(request.getBirthday());
        }

        if (request.getEmail() != null) {
            Optional<User> emailCheck = userRepository.findByEmail(request.getEmail());
            if (emailCheck.isPresent() && !emailCheck.get().getId().equals(id)) {
                logger.warn("Email già esistente: {}", request.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Email già in uso"));
            }
            user.setEmail(request.getEmail());
        }

        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }

        if (request.getDescription() != null) {
            user.setDescription(request.getDescription());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);
        logger.info("Dati utente aggiornati per ID: {}", id);

        UserResponseDTO userDTO = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getDate(),
                user.getGender(),
                user.getEmail(),
                user.getDescription(),
                user.getRole().getId()
        );

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        logger.info("Richiesta di recupero di tutti gli utenti");

        try {
            List<User> users = userRepository.findAll();

            List<UserResponseDTO> response = users.stream().map(user -> new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getDate(),
                    user.getGender(),
                    user.getEmail(),
                    user.getDescription(),
                    user.getRole().getId()
            )).toList();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Errore durante il recupero degli utenti: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore durante il recupero degli utenti"));
        }
    }

    @GetMapping("/{id}/editRole")
    public ResponseEntity<?> toggleUserRole(@PathVariable Integer id) {
        logger.info("Richiesta di modifica ruolo per l'utente con ID: {}", id);

        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                logger.warn("Utente non trovato con ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Utente non trovato"));
            }

            User user = userOpt.get();

            Integer currentRoleId = user.getRole().getId();
            Role newRole;
            if (currentRoleId == 1) {
                logger.info("Cambio ruolo utente da 1 a 2 per l'utente con ID: {}", id);
                newRole = roleRepository.findById(2)
                        .orElseThrow(() -> new IllegalStateException("Ruolo con ID 2 non trovato"));
            } else {
                logger.info("Cambio ruolo utente da 2 a 1 per l'utente con ID: {}", id);
                newRole = roleRepository.findById(1)
                        .orElseThrow(() -> new IllegalStateException("Ruolo con ID 1 non trovato"));
            }

            user.setRole(newRole);
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "userManagement.roleEdit.success", "newRoleId", newRole.getId()));
        } catch (Exception e) {
            logger.error("Errore durante la modifica del ruolo per l'utente con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "userManagement.error.message"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        logger.info("Richiesta di eliminazione utente con ID: {}", id);

        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                logger.warn("Utente non trovato con ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "error.user.notFound"));
            }

            userRepository.deleteById(id);
            logger.info("Utente con ID {} eliminato con successo", id);

            return ResponseEntity.ok(Map.of("message", "Utente eliminato con successo"));
        } catch (Exception e) {
            logger.error("Errore durante l'eliminazione dell'utente con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Errore durante l'eliminazione dell'utente"));
        }
    }


}


