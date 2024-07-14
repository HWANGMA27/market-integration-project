package prj.blockchain.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj.blockchain.api.model.BalanceHistory;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {
}
