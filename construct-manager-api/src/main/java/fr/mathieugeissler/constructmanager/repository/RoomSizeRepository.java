package fr.mathieugeissler.constructmanager.repository;

import fr.mathieugeissler.constructmanager.domain.RoomSize;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RoomSize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomSizeRepository extends JpaRepository<RoomSize, Long> {

}
