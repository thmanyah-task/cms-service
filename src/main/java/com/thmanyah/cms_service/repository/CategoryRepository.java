package com.thmanyah.cms_service.repository;

import com.thmanyah.cms_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
