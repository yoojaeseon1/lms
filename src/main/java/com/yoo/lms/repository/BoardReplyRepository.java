package com.yoo.lms.repository;

import com.yoo.lms.domain.BoardReply;
import com.yoo.lms.repository.custom.BoardReplyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long>, BoardReplyRepositoryCustom {



}
