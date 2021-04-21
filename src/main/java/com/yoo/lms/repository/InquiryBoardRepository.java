package com.yoo.lms.repository;

import com.yoo.lms.domain.InquiryBoard;
import com.yoo.lms.repository.custom.InquirylBoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InquiryBoardRepository extends JpaRepository<InquiryBoard, Long>, InquirylBoardRepositoryCustom {

    @Query("select i from InquiryBoard i join fetch i.createdBy where i.id=:boardId")
    InquiryBoard findByIdFetchMember(@Param("boardId") Long boardId);

}
