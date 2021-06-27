package ru.tilipod.services.users;

import ru.tilipod.models.User;
import ru.tilipod.models.UserStatus;
import ru.tilipod.services.users.exceptions.UserNotFoundException;

import java.sql.Timestamp;
import java.util.List;

/**
 * Класс сервиса для работы с пользователями
 * @author Tilipod
 */
public interface UserService {
    /**
     * Создает нового пользователя в БД
     * @param user Создаваемый пользователь
     * @author Tilipod
     */
    void createUser(User user);

    /**
     * Возвращает пользователя из БД по его id
     * @param id ID искомого пользователя
     * @return Найденный пользователь
     * @throws UserNotFoundException Возникает, если пользователь с указанным id не найден
     * @author Tilipod
     */
    User getById(long id) throws UserNotFoundException;

    /**
     * Изменяет статус пользователя по его id и возвращает старый статус
     * @param id ID пользователя, у которого изменяется статус
     * @param newUserStatus Новый статус пользователя
     * @return Старый статус
     * @throws UserNotFoundException Возникает, если пользователь с указанным id не найден
     * @author Tilipod
     */
    UserStatus changeUserStatus(long id, UserStatus newUserStatus) throws UserNotFoundException;

    /**
     * Отбирает пользователей по условию их статусов
     * @param isOnline Статус пользователей, которых нужно выбрать
     * @param timestamp Метка времени последнего изменения статуса пользователей, которых нужно выбрать
     * @return Список отобранных пользователей
     * @author Tilipod
     */
    List<User> getAllIf(Boolean isOnline, Timestamp timestamp);
}
