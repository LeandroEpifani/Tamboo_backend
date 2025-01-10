package com.backend.tamboo.controller;

import com.backend.tamboo.dto.UserRequest;
import com.backend.tamboo.entity.Role;
import com.backend.tamboo.entity.User;
import com.backend.tamboo.repository.RoleRepository;
import com.backend.tamboo.repository.UserRepository;
import com.backend.tamboo.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // Per assegnare un ruolo predefinito
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private final PasswordEncoder passwordEncoder; // se usi password hashate

    // Inietti via costruttore o @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Classe che rappresenta la richiesta JSON (email, password)
    public static class LoginRequest {
        private String email;
        private String password;

        // getter e setter
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Classe di esempio per la risposta JSON
    public static class LoginResponse {
        private String message;
        private String token; // se vuoi restituire un token, ad esempio JWT

        public LoginResponse(String message, String token) {
            this.message = message;
            this.token = token;
        }

        // getter e setter
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        logger.info("Tentativo di login con email: {}", email);

        // Controlla se l'utente esiste
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            logger.warn("Email non esistente: {}", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "login.error.wrongEmail"));
        }

        User user = userOpt.get();

        // Controlla la password hashata
        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("Password errata per email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "login.error.wrongPassword"));
        }

        // Genera il token JWT
        String token = jwtUtil.generateToken(user.getId());

        // Risposta con ID utente e token
        return ResponseEntity.ok(Map.of(
                "message", "Login effettuato con successo",
                "token", token,
                "userId", user.getId(), // Restituisce l'ID dell'utente
                "userRole", user.getRole().getId(),
                "name", user.getName()
        ));
    }


    // Endpoint di creazione utente
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
        logger.info("Richiesta di creazione utente ricevuta per email: {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Tentativo di creazione utente con email già esistente: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "createUser.error.emailExists"));
        }

        try {
            User newUser = new User();
            newUser.setName(request.getName());
            newUser.setSurname(request.getSurname());
            newUser.setGender(request.getGender());
            newUser.setDate(request.getBirthday());

            newUser.setEmail(request.getEmail());
            // Hash della password
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser.setDescription(request.getDescription());

            userRepository.save(newUser);
            logger.info("Utente creato con successo: {}", newUser.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Utente creato con successo"));
        } catch (Exception e) {
            logger.error("Errore durante la creazione dell'utente", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "error.creation"));
        }
    }

    // Endpoint di test per verificare che il controller funzioni
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("test funziona");
        logger.info("Endpoint /api/test chiamato");
        return ResponseEntity.ok("Funziona!");
    }

}
