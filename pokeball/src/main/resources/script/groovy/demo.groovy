package script.groovy

import com.theoxao.BarBean
import com.theoxao.bonsly.groovy.annotations.Autowired
import groovy.transform.Field

import java.util.concurrent.CompletableFuture;

@Field
@Autowired
BarBean barBean

public static CompletableFuture<CompletableFuture<String>> asyncJava(String name, String age) {
    println name
    CompletableFuture<String> meFuture = future(name);
    return meFuture.thenApply { me ->
        CompletableFuture<String> youFuture = future("sophia");
        return youFuture.thenApply { you ->
            def concat = me.concat(you)
            println concat
            return concat;
        }
    }
}

public static CompletableFuture<String> future(String name) {
    return CompletableFuture.completedFuture(name + " from future;");
}
