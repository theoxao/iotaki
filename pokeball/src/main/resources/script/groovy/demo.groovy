package script.groovy

import java.util.concurrent.CompletableFuture;

public CompletableFuture<CompletableFuture<String>> asyncJava(String name, String age) {
    CompletableFuture<String> meFuture = future(name);
    return meFuture.thenApply { me ->
        CompletableFuture<String> youFuture = future("sophia");
        return youFuture.thenApply { you ->
            def concat = me.concat(you)
            return concat;
        }
    }
}

public CompletableFuture<String> future(String name) {
    return CompletableFuture.completedFuture(name + " from future;");
}
