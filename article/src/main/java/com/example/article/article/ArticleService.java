package com.example.article.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

  @Autowired
  ArticleRepository articleRepository;

  public List<Article> getAllArticles() {
    return articleRepository.findAll();
  }

  public Article save(Article article) {
    return articleRepository.save(article);
  }

  public void deleteArticle(long id) {
    articleRepository.deleteById(id);
  }

}
