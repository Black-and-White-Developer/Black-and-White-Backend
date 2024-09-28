package bw.growingcode.code.repository;

import bw.growingcode.code.domain.Keyword;
import bw.growingcode.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findAllByUser(User user);

}