package com.yoo.lms.repository;

import com.yoo.lms.domain.CourseMaterial;
import com.yoo.lms.repository.custom.CourseMaterialRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long>, CourseMaterialRepositoryCustom {

}
