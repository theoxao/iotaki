import com.theoxao.BarBean
import com.theoxao.bonsly.groovy.annotations.Autowired

import java.util.concurrent.CompletableFuture

class Foo {

    @Autowired
    BarBean barBean;

    public CompletableFuture<CompletableFuture<String>> asyncJava(String name) {
        CompletableFuture<String> meFuture = future(name);
        return meFuture.thenApply { me ->

            CompletableFuture<String> youFuture = future("sophia");
            return youFuture.thenApply { you ->

                return me.concat(you);
            }
        }
    }

    public CompletableFuture<String> future(String name) {
        return CompletableFuture.completedFuture(name + " from future;");
    }
}

