import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by knoldus on 28/2/18. Worked on by Anmol Mehta, Software Consultant, Knoldus Software LLP
 */
public class CompletableFutureExample {

    public static void main(String args[]) throws InterruptedException {

        /**
         *  Example for runAsync
         */
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Running asynchronous task in parallel");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        });

        /**
         * supplyAsync Example:
         */
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "This is the result of the asynchronous computation";
        });

        /**
         * thenApply and thenAccept example:
         */

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Knolders!";
        });
        CompletableFuture<String> result = completableFuture.thenApply(name -> "Hello " + name);

        result.thenAccept(System.out::println);

        /**
         * Sequence of thenApply.
         */

        //CompletableFuture<String> supplyForSequencedThenApplyExample =
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Knolders!";
        }).thenApply(name -> "Hello " + name).thenApply(greeting -> greeting + " Welcome to Knoldus Inc!");

        /**
         * thenRun example
         */

        CompletableFuture<String> thenRunExampleSuppliedValue =
                CompletableFuture.supplyAsync(() -> "Knolders!");

        CompletableFuture<Void> thenRun =
                thenRunExampleSuppliedValue.thenRun(() -> System.out.println("Example with thenRun()."));

        //completableFuture.thenRun(() -> System.out.println("Example with thenRun()."));

        /**
         * thenCompose example:
         */

        CompletableFuture<String> thenCompose =
                CompletableFuture.supplyAsync(() -> "Hello")
                        .thenCompose(value -> CompletableFuture.supplyAsync(() -> value + " Knolders!"));

        thenCompose.thenAccept(System.out::println);

        /**
         * thenCombine example:
         */
        CompletableFuture<String> thenCombine
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> " Knolders!"), (value1, value2) -> value1 + value2);

        thenCombine.thenAccept(System.out::println);

        /**
         * thenAcceptBoth example
         */

        CompletableFuture<Void> thenAcceptBoth = CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " Knolders"),
                        (value1, value2) -> System.out.println(value1 + value2));

        /**
         * allOf example
         */

        CompletableFuture<String> completableFuture1
                = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> completableFuture2
                = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> completableFuture3
                = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);

        /**
         * anyOf example
         */
        CompletableFuture<String> anyOf1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 1";
        });
        CompletableFuture<String> anyOf2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 2";
        });
        CompletableFuture<String> anyOf3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 3";
        });

        CompletableFuture<Object> anyOfExample
                = CompletableFuture.anyOf(anyOf1, anyOf2, anyOf3);


        /**
         * exception handling with CompletableFuture.
         */
        Integer age = -1;

        CompletableFuture.supplyAsync(() -> {
            if (age < 0) {
                throw new IllegalArgumentException("Age can not be negative");
            }
            if (age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).exceptionally(ex -> {
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return "Unknown!";
        });

        Thread.sleep(10000);

        Optional<String> optional = Optional.of("Anmol Mehta");
        Optional<String> optional1 = Optional.ofNullable(null);
        try {
            Optional.of(null).orElseThrow(Exception::new);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        optional.get();
        optional1.orElse("Ammo");
        optional1.orElseGet(()-> "Ammo Mehta");

        Optional<Optional<List<String>>> optionalOptional = Optional.empty();

        Optional<List<String>> stringOptional = optionalOptional.flatMap(x-> x);
        Optional<List<String>> listOptional = Optional.empty();
        Optional<List<String>> listOptionalResult = listOptional.map(x->x.subList(1,3));
        Optional<List<String>> listOptionalResult1 = listOptional.filter(List::isEmpty);

    }
}
