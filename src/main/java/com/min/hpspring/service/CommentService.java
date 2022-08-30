package com.min.hpspring.service;

import com.min.hpspring.repository.ArticleRepository;
import com.min.hpspring.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;





}//CommentService
