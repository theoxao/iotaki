import java.util.concurrent.CompletableFuture;

public static String asyncJava(){
    String me=await future("theo");
    String you=await future("sophia");
    return me.concat(you);
}

public static CompletableFuture<String> future(String name){
    return CompletableFuture.completedFuture(name+" from future;");
}


