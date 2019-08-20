package script.groovy

import com.theoxao.BarBean
import com.theoxao.bonsly.groovy.annotations.Autowired
import groovy.transform.Field

import java.util.concurrent.CompletableFuture;

@Field
@Autowired
BarBean barBean

public static CompletableFuture<CompletableFuture<String>> asyncJava(String name, String age) {
    CompletableFuture<String> meFuture = future(name, age);
    return meFuture.thenApply { me ->
        CompletableFuture<String> youFuture = future("sophia", "18");
        return youFuture.thenApply { you ->
            return me.concat(you);
        }
    }
}

public static CompletableFuture<String> future(String name, String age) {
    return CompletableFuture.completedFuture(name + "(" + age + ") from future;");
}
