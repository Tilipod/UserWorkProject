package ru.tilipod.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tilipod.models.UserStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * Репозиторий статусов пользователей. Для работы с таблицей user_status в БД.
 * @author Tilipod
 */
@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    /**
     * Запрос на получение всех статусов, метка времени изменения которых позже statusTimestamp
     * и значение статуса которых равно isOnline
     * @param isOnline Значение статуса (в сети/не в сети)
     * @param statusTimestamp Метка времени для отбора статусов
     * @author Tilipod
     */
    List<UserStatus> findByIsOnlineAndChangeTimestampAfter (boolean isOnline, Timestamp statusTimestamp);

    /**
     * Запрос на получение всех статусов, значение которых равно isOnline
     * @param isOnline Значение статуса (в сети/не в сети)
     * @author Tilipod
     */
    List<UserStatus> findByIsOnline (boolean isOnline);

    /**
     * Запрос на получение всех статусов, метка времени изменения которых позже statusTimestampe
     * @param statusTimestamp Метка времени для отбора статусов
     * @author Tilipod
     */
    List<UserStatus> findByChangeTimestampAfter (Timestamp statusTimestamp);

    /**
     * Запрос на получение статуса пользователя по его id
     * @param id ID пользователя
     * @author Tilipod
     */
    UserStatus findByUserId (long id);
}
