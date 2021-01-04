public class Main {
    public static void main(String[] args) {
        double[] array = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] arr;
        arr = array;
        arr[5] = 7.0;

        for (double el: arr) {
            System.out.println(el);
        }
    }
}
