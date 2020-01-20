package demo.java;


public class TestWrapper {

    public static void main(String[] args) {
        final String ONE = "1";
        Boolean flag = Boolean.valueOf(ONE);
        Integer num1 = new Integer(ONE);
        Integer num2 = 1;

        if (flag) {
            System.out.println(num1 == num2);
        }
    }
}
