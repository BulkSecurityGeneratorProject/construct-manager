package fr.mathieugeissler.constructmanager.repository;

import fr.mathieugeissler.constructmanager.domain.GenericProductCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GenericProductCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenericProductCategoryRepository extends JpaRepository<GenericProductCategory, Long> {

}
