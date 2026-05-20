package org.pipmycode.sbecomm.repository;

import org.pipmycode.sbecomm.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
