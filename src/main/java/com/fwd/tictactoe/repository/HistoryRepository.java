package com.fwd.tictactoe.repository;

import com.fwd.tictactoe.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Setiawan Candrafu
 * @date 3/22/2023
 */
@EnableJpaRepositories
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query(
            value = "select status ,count(*) total \n" +
                    "from history \n" +
                    "where user_name=:username \n" +
                    "group by status",
            nativeQuery = true)
    List<Map<String, Object>> getHistory(@Param("username") String username);
}
