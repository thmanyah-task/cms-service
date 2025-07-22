package com.thmanyah.cms_service.repository;

import com.thmanyah.cms_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long > {

       List<Role> findByNameIn(List<String> names);

}
