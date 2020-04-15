package demo.jvm0209;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Arthas
 */
public class RandomSample {

    public static void main(String[] args) throws Exception {
        //
        int count = 10000;
        int seed = 0;
        for (int i = 0; i < count; i++) {
            seed = randomHash(seed);
            TimeUnit.SECONDS.sleep(2);
        }

    }

    public static int randomHash(Integer seed) {
        String uuid = UUID.randomUUID().toString();
        int hashCode = uuid.hashCode();
        System.out.println("prev.seed=" + seed);
        return hashCode;

    }
}
