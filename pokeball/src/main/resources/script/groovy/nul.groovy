import java.util.concurrent.CompletableFuture;
import com.theoxao.BarBean;
import com.theoxao.bonsly.groovy.annotations.Autowired;

public static CompletableFuture<CompletableFuture<String>> asyncJava() {
    CompletableFuture<String> meFuture = future("theo");
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