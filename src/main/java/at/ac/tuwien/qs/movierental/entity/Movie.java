package at.ac.tuwien.qs.movierental.entity;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;
import java.time.Year;

@ToString
@EqualsAndHashCode
public class Movie {

    private final ObjectProperty<Long> id = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> title = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> subtitle = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> genre = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> ageRating = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> language = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Integer> priceInCents = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> director = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Float> rating = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Year> yearPublished = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Boolean> series = new SimpleObjectProperty<>(false);
    private final ObjectProperty<Integer> stock = new SimpleObjectProperty<>(0);
    private final ObjectProperty<File> cover = new SimpleObjectProperty<>(null);

    public Long getId() {
        return id.get();
    }
    public void setId(Long id) {
        this.id.set(id);
    }
    public ObjectProperty<Long> idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
    public ObjectProperty<String> titleProperty() {
        return title;
    }

    public String getSubtitle() {
        return subtitle.get();
    }
    public void setSubtitle(String subtitle) {
        this.subtitle.set(subtitle);
    }
    public ObjectProperty<String> subtitleProperty() {
        return subtitle;
    }

    public String getGenre() {
        return genre.get();
    }
    public void setGenre(String genre) {
        this.genre.set(genre);
    }
    public ObjectProperty<String> genreProperty() {
        return genre;
    }

    public String getAgeRating() {
        return ageRating.get();
    }
    public void setAgeRating(String ageRating) {
        this.ageRating.set(ageRating);
    }
    public ObjectProperty<String> ageRatingProperty() {
        return ageRating;
    }

    public String getLanguage() {
        return language.get();
    }
    public void setLanguage(String language) {
        this.language.set(language);
    }
    public ObjectProperty<String> languageProperty() {
        return language;
    }

    public Integer getPriceInCents() {
        return priceInCents.get();
    }
    public void setPriceInCents(Integer priceInCents) {
        this.priceInCents.set(priceInCents);
    }
    public ObjectProperty<Integer> priceInCentsProperty() {
        return priceInCents;
    }

    public String getDirector() {
        return director.get();
    }
    public void setDirector(String director) {
        this.director.set(director);
    }
    public ObjectProperty<String> directorProperty() {
        return director;
    }

    public Float getRating() {
        return rating.get();
    }
    public void setRating(Float rating) {
        this.rating.set(rating);
    }
    public ObjectProperty<Float> ratingProperty() {
        return rating;
    }

    public Year getYearPublished() {
        return yearPublished.get();
    }
    public void setYearPublished(Year yearPublished) {
        this.yearPublished.set(yearPublished);
    }
    public ObjectProperty<Year> yearPublishedProperty() {
        return yearPublished;
    }

    public Boolean getSeries() {
        return series.get();
    }
    public void setSeries(Boolean series) {
        this.series.set(series);
    }
    public ObjectProperty<Boolean> seriesProperty() {
        return series;
    }

    public Integer getStock() {
        return stock.get();
    }
    public void setStock(Integer stock) {
        this.stock.set(stock);
    }
    public ObjectProperty<Integer> stockProperty() {
        return stock;
    }

    public File getCover() {
        return cover.get();
    }
    public void setCover(File cover) {
        this.cover.set(cover);
    }
    public ObjectProperty<File> coverProperty() {
        return cover;
    }
}
