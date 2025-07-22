package com.thmanyah.cms_service.security.repository;

import com.thmanyah.cms_service.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long > {

       List<Role> findByNameIn(List<String> names);

}
