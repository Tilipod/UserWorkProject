package ru.tilipod.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tilipod.models.User;

/**
 * Репозиторий пользователей. Для работы с таблицей user в БД. Без спец. запросов
 * @author Tilipod
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
