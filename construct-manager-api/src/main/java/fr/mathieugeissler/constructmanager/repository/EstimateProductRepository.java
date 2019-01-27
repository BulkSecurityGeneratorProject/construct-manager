package fr.mathieugeissler.constructmanager.repository;

import fr.mathieugeissler.constructmanager.domain.EstimateProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstimateProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstimateProductRepository extends JpaRepository<EstimateProduct, Long> {

}
