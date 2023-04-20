package io.raman.moviecatalogservice.models;;

public class Movie {
    private String movieId;
    private String name;
    private String description;

/*When a Java object is unmarshalled from a JSON or XML document , the default constructor is used 
to create an instance of the class. Though Java provides an empty on its own, but since here we 
already have a constructor with arguments, we need to provide an empty constructor explicitly. The
way marshalling and un-marshalling works is that it first creates an instance and then parses the
JSON/XML document/string and populates one-by-one.
*/
    public Movie() {

    }

    public Movie(String movieId, String name, String description) {
        this.movieId = movieId;
        this.name = name;
        this.description = description;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
