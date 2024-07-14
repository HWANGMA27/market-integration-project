package prj.blockchain.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj.blockchain.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

