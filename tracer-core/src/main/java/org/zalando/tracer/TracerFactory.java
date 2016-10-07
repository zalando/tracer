package org.zalando.tracer;

import lombok.Singular;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.zalando.tracer.TraceListeners.compound;

public final class TracerFactory {

    TracerFactory() {
        // package private so we can trick code coverage
    }

    public static class Builder {

        public Builder stacked(final boolean stacked) {
            this.stacked = stacked;
            return this;
        }

        public Builder stacked() {
            this.stacked = true;
            return this;
        }

    }

    @lombok.Builder(builderClassName = "Builder")
    static Tracer create(
            final boolean stacked,
            @Singular final List<String> traces,
            @Singular("trace") final Map<String, Generator> customs,
            @Singular final List<TraceListener> listeners) {

        final UUIDGenerator defaultGenerator = new UUIDGenerator();
        final Map<String, Generator> combined = new LinkedHashMap<>();
        combined.putAll(customs);
        combined.putAll(traces.stream().collect(toMap(Function.identity(), trace -> defaultGenerator)));

        if (stacked) {
            return new StackedTracer(combined, compound(listeners));
        } else {
            return new DefaultTracer(combined, compound(listeners));
        }
    }

}
