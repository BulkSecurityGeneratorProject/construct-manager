package fr.mathieugeissler.constructmanager.repository;

import fr.mathieugeissler.constructmanager.domain.RoomGenericProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RoomGenericProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomGenericProductRepository extends JpaRepository<RoomGenericProduct, Long> {

}
