package script.groovy

import com.theoxao.BarBean
import com.theoxao.bonsly.groovy.annotations.Autowired
import groovy.transform.Field

import java.util.concurrent.CompletableFuture;

@Field
@Autowired
BarBean barBean

public static CompletableFuture<CompletableFuture<String>> asyncJava(String name) {
    CompletableFuture<String> meFuture = future(name);
    return meFuture.thenApply { me ->
        CompletableFuture<String> youFuture = future("sophia");
        return youFuture.thenApply { you ->
            return me.concat(you);
        }
    }
}

public static CompletableFuture<String> future(String name) {
    return CompletableFuture.completedFuture(name + " from future;");
}
