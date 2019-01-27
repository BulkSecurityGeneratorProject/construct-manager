package fr.mathieugeissler.constructmanager.repository;

import fr.mathieugeissler.constructmanager.domain.Home;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Home entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {

}
