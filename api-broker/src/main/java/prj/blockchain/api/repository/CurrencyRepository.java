package prj.blockchain.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prj.blockchain.api.model.Currency;
import prj.blockchain.api.model.Exchange;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Modifying
    @Query("DELETE FROM Currency c WHERE c.exchange = :exchange")
    void deleteByExchange(@Param("exchange") Exchange exchange);

    List<Currency> findAllByExchange(Exchange exchange);
}
