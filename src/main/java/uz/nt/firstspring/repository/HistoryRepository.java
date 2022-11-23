package uz.nt.firstspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.firstspring.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
