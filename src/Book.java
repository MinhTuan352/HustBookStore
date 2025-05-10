public class Book {
    private String title;
    private double price;
    private String category;

    public Book(String title, double price, String category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return title;
    }
}