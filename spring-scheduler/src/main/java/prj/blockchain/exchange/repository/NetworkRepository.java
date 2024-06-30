package prj.blockchain.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj.blockchain.exchange.model.Network;

@Repository
public interface NetworkRepository extends JpaRepository<Network, Long> {
}
