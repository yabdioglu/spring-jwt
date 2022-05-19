package com.example.article.article;

import java.util.List;

import com.example.article.config.AppUser;
import com.example.article.config.LoggedInUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

  @Autowired
  ArticleService articleService;

  @GetMapping("/api/1.0/articles")
  List<Article> getArticles(){
    return articleService.getAllArticles();
  }

  @PostMapping("/api/1.0/articles")
  Article saveArticle(@RequestBody Article article, @LoggedInUser AppUser appUser){
    article.setUserId(appUser.getUserId());
    article.setUsername(appUser.getUsername());
    return articleService.save(article);
  }

  @DeleteMapping("/api/1.0/articles/{id}")
  void deleteArticle(@PathVariable long id){
    articleService.deleteArticle(id);
  }
  
}
