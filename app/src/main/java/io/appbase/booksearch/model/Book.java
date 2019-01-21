package io.appbase.booksearch.model;

import java.util.Objects;

public final class Book {

  private String image;
  private int avgRatingRounded;
  private int booksCount;
  private String originalTitle;
  private String imageMedium;
  private String isbn;
  private float avgRating;
  private int originalPublicationYear;
  private String title;
  private String languageCode;
  private int id;
  private int ratingsCount;
  private String originalSeries;
  private String authors;

  public Book() {
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getAvgRatingRounded() {
    return avgRatingRounded;
  }

  public void setAvgRatingRounded(int avgRatingRounded) {
    this.avgRatingRounded = avgRatingRounded;
  }

  public int getBooksCount() {
    return booksCount;
  }

  public void setBooksCount(int booksCount) {
    this.booksCount = booksCount;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getImageMedium() {
    return imageMedium;
  }

  public void setImageMedium(String imageMedium) {
    this.imageMedium = imageMedium;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public float getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(float avgRating) {
    this.avgRating = avgRating;
  }

  public int getOriginalPublicationYear() {
    return originalPublicationYear;
  }

  public void setOriginalPublicationYear(int originalPublicationYear) {
    this.originalPublicationYear = originalPublicationYear;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRatingsCount() {
    return ratingsCount;
  }

  public void setRatingsCount(int ratingsCount) {
    this.ratingsCount = ratingsCount;
  }

  public String getOriginalSeries() {
    return originalSeries;
  }

  public void setOriginalSeries(String originalSeries) {
    this.originalSeries = originalSeries;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return getOriginalPublicationYear() == book.getOriginalPublicationYear() &&
        getId() == book.getId() &&
        Objects.equals(getOriginalTitle(), book.getOriginalTitle()) &&
        Objects.equals(getIsbn(), book.getIsbn());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getOriginalTitle(), getIsbn(), getOriginalPublicationYear(), getId());
  }

  @Override
  public String toString() {
    return "Book{" +
        "isbn='" + isbn + '\'' +
        ", title='" + title + '\'' +
        ", id=" + id +
        '}';
  }
}