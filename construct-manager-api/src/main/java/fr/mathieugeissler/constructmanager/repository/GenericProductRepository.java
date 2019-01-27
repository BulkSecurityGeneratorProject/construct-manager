package fr.mathieugeissler.constructmanager.repository;

import fr.mathieugeissler.constructmanager.domain.GenericProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GenericProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenericProductRepository extends JpaRepository<GenericProduct, Long> {

}
