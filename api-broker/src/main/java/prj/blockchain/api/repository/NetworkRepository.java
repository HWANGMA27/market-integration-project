package prj.blockchain.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prj.blockchain.api.model.Exchange;
import prj.blockchain.api.model.Network;

import java.util.List;

@Repository
public interface NetworkRepository extends JpaRepository<Network, Long> {

    @Modifying
    @Query("DELETE FROM Network n WHERE n.exchange = :exchange")
    void deleteByExchange(Exchange exchange);

    @Query("SELECT DISTINCT n.currency FROM Network n WHERE n.exchange = :exchange")
    List<String> findDistinctCurrencyByExchange(Exchange exchange);
}
