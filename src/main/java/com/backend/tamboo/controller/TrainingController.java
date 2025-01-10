package com.backend.tamboo.controller;

import com.backend.tamboo.dto.TrainingRequest;
import com.backend.tamboo.entity.Training;
import com.backend.tamboo.entity.User;
import com.backend.tamboo.repository.TrainingRepository;
import com.backend.tamboo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTraining(@RequestBody TrainingRequest request) {


        try {
            // Crea un nuovo oggetto Training
            Training training = new Training();
            training.setName(request.getName()); // Aggiunto il campo 'name'
            training.setTimeSignature(request.getTimeSignature());
            training.setBpm(request.getBpm());
            training.setBeat(request.getBeat());

            // Salva nel database
            trainingRepository.save(training);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Training creato con successo!");
        } catch (Exception e) {
            logger.error("Errore durante la creazione del training: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la creazione del training.");
        }
    }



    @GetMapping("/all")
    public ResponseEntity<?> getAllTrainings() {
        logger.info("Richiesta di recupero di tutti i training");

        try {
            // Recupera tutti i training
            List<Training> trainings = trainingRepository.findAll();

            // Converte i dati in DTO
            List<TrainingRequest> response = trainings.stream().map(training ->
                    new TrainingRequest(
                            training.getId(), // Aggiunto l'ID
                            training.getName(), // Aggiunto il campo 'name'
                            training.getTimeSignature(),
                            training.getBpm(),
                            training.getBeat()
                    )
            ).toList();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Errore durante il recupero dei training: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il recupero dei training.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainingById(@PathVariable Integer id) {
        logger.info("Richiesta di recupero del training con ID: {}", id);

        try {
            // Recupera il training dal database
            Training training = trainingRepository.findById(id).orElse(null);

            // Controlla se l'allenamento esiste
            if (training == null) {
                logger.warn("Training non trovato con ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Training non trovato con ID: " + id);
            }

            // Converte i dati in DTO
            TrainingRequest response = new TrainingRequest(
                    training.getId(),
                    training.getName(),
                    training.getTimeSignature(),
                    training.getBpm(),
                    training.getBeat()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Errore durante il recupero del training: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il recupero del training.");
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateTrainingById(@PathVariable Integer id, @RequestBody TrainingRequest request) {
        logger.info("Richiesta di aggiornamento del training con ID: {}", id);

        try {
            // Recupera il training dal database
            Training training = trainingRepository.findById(id).orElse(null);

            // Controlla se l'allenamento esiste
            if (training == null) {
                logger.warn("Training non trovato con ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Training non trovato con ID: " + id);
            }


            // Aggiorna i dati solo se forniti nella richiesta
            if (request.getName() != null) {
                training.setName(request.getName());
            }
            if (request.getTimeSignature() != null) {
                training.setTimeSignature(request.getTimeSignature());
            }
            if (request.getBpm() != null) {
                training.setBpm(request.getBpm());
            }
            if (request.getBeat() != null) {
                training.setBeat(request.getBeat());
            }

            // Salva i dati aggiornati nel database
            trainingRepository.save(training);

            logger.info("Training aggiornato correttamente con ID: {}", id);
            return ResponseEntity.ok("Training aggiornato con successo!");
        } catch (Exception e) {
            logger.error("Errore durante l'aggiornamento del training: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante l'aggiornamento del training.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrainingById(@PathVariable Integer id) {
        logger.info("Richiesta di eliminazione del training con ID: {}", id);

        try {
            // Controlla se l'allenamento esiste
            Training training = trainingRepository.findById(id).orElse(null);
            if (training == null) {
                logger.warn("Training non trovato con ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Training non trovato con ID: " + id);
            }

            // Elimina l'allenamento
            trainingRepository.delete(training);

            logger.info("Training eliminato correttamente con ID: {}", id);
            return ResponseEntity.ok("Training eliminato con successo!");
        } catch (Exception e) {
            logger.error("Errore durante l'eliminazione del training: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante l'eliminazione del training.");
        }
    }



}
