package script.groovy

import java.util.concurrent.CompletableFuture;

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
