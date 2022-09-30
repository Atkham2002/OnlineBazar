package uz.nt.firstspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.nt.firstspring.entity.UserSession;

@Repository
public interface RedisRepository extends CrudRepository<UserSession,Long> {
}
