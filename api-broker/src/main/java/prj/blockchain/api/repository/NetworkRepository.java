package prj.blockchain.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj.blockchain.api.model.Network;

@Repository
public interface NetworkRepository extends JpaRepository<Network, Long> {
}
