package app.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.store.entity.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {


}
