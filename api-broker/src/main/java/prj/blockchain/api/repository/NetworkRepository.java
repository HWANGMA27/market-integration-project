package prj.blockchain.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import prj.blockchain.api.model.Exchange;
import prj.blockchain.api.model.Network;

@Repository
public interface NetworkRepository extends JpaRepository<Network, Long> {

    @Modifying
    void deleteByExchange(Exchange exchange);
}
