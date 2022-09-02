package com.cqupt.logistic.dao;

import com.cqupt.logistic.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author ACER
 * @Date:2020/12/19
 */
public interface ICommentDao extends JpaRepository<Comment,Long> {
}
