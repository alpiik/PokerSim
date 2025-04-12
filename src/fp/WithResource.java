package fp;

import org.junit.jupiter.api.Test;
// Vajalik import Consumer liidese jaoks
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class WithResource {

    @Test
    public void usesResourceInControlledManner() {
        assertThat(Resource.isOpen()).isFalse();
        withResource(resource -> resource.write("hello!"));
        assertThat(Resource.isOpen()).isFalse();
        assertThat(Resource.getWrittenData()).isEqualTo("hello!");
    }
    private void withResource(Consumer<Resource> consumer) {
        Resource resource = new Resource();
        try {
            resource.open();
            consumer.accept(resource);
        } finally {
            resource.close();
        }
    }
}