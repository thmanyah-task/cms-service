package com.thmanyah.cms_service.programme.repository;


import com.thmanyah.cms_service.programme.entity.Programme;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface ProgrammeRepository extends JpaRepository<Programme,Long> {

}
