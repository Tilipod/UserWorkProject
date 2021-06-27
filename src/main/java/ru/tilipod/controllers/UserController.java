package ru.tilipod.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tilipod.models.User;
import ru.tilipod.models.UserStatus;
import ru.tilipod.services.users.UserService;
import ru.tilipod.services.users.exceptions.UserNotFoundException;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * REST-контроллер для работы с пользователями. Отвечает за работу с информацией о пользователях
 * @author Tilipod
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Запрос на создание нового пользователя
     * @param user Создаваемый пользователь
     * @author Tilipod
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @Order
    public Callable<ResponseEntity> create(@RequestBody User user) {
        return () -> {
            try {
                userService.createUser(user);
                return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
            } catch (Exception err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    /**
     * Запрос на получение информации о пользователе по его id. Высший приоритет исполнения
     * @param id ID пользователя
     * @author Tilipod
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Callable<ResponseEntity> get(@PathVariable long id) {
        return () -> {
            try {
                User user = userService.getById(id);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (UserNotFoundException err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            catch (Exception err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    /**
     * Запрос на изменение статуса пользователя по его id
     * @param id ID пользователя
     * @param newUserStatus Новый статус пользователя
     * @author Tilipod
     */
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @Order
    public Callable<ResponseEntity> changeUserStatus(@PathVariable long id, @RequestBody UserStatus newUserStatus) {
        return () -> {
            try {
                UserStatus oldUserStatus = userService.changeUserStatus(id, newUserStatus);

                Map<String, Object> response = new HashMap<>();
                response.put("user_id", id);
                response.put("old_status", oldUserStatus);
                response.put("new_status", newUserStatus);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (UserNotFoundException err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            catch (Exception err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    /**
     * Запрос на получение статистики посещения сервера. Высший приоритет исполнения
     * @param params Параметры фильтрации запроса (необязательные):
     *               Boolean isOnline - статус пользователя (true - в сети, false - не в сети)
     *               String timestamp - метка времени, начиная с которой нужно выдать статистику
     * @author Tilipod
     */
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Callable<ResponseEntity> getAllIf(@RequestBody Map<String, Object> params) {
        return () -> {
            try {
                Boolean isOnline = (Boolean) params.getOrDefault("isOnline", null);
                String datetime = (String) params.getOrDefault("timestamp", null);
                Timestamp timestamp = datetime == null ?
                                      null :
                                      Timestamp.valueOf(datetime);
                List<User> response = userService.getAllIf(isOnline, timestamp);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
