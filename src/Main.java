public class Main {
    public static void main(String[] args) {
        Minefield field = new Minefield(10, 10, 10);
        System.out.println(field);
        field.initialize();
        System.out.println(field);
    }
}