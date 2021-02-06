package com.laioffer.tinnews.model;

import java.util.Objects;

public class Article {
//    Source source; // 不好存，不重要
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) &&
                Objects.equals(title, article.title) &&
                Objects.equals(description, article.description) &&
                Objects.equals(url, article.url) &&
                Objects.equals(urlToImage, article.urlToImage) &&
                Objects.equals(publishedAt, article.publishedAt) &&
                Objects.equals(content, article.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, description, url, urlToImage, publishedAt, content);
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
