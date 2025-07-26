package app.store.repository;

import app.store.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import app.store.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findByName(String name);
    List<Permission> findByNameIn(Set<String> names);

}
