package demo.jvm0104;

public class ExceptionTest {
    public static void main(String[] args) throws Exception {
        try {
            throw new RuntimeException("Test");
        } catch (RuntimeException e) {
            throw new Exception(e);
        }
    }
}