package com.example.article.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

  @Autowired
  ArticleService articleService;

  @GetMapping("/api/1.0/articles")
  List<Article> getArticles(){
    return articleService.getAllArticles();
  }

  @PostMapping("/api/1.0/articles")
  Article saveArticle(@RequestBody Article article){
    return articleService.save(article);
  }

  @DeleteMapping("/api/1.0/articles/{id}")
  void deleteArticle(@PathVariable long id){
    articleService.deleteArticle(id);
  }
  
}
