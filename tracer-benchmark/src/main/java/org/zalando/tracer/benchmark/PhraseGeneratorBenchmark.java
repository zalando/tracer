package org.zalando.tracer.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.zalando.tracer.Generator;
import org.zalando.tracer.PhraseGenerator;

@State(Scope.Thread)
public class PhraseGeneratorBenchmark {

    private Generator generator;

    @Setup(Level.Trial)
    public void doSetup() {
        this.generator = new PhraseGenerator();
    }

    @Benchmark
    public void benchmark(final Blackhole blackhole) {
        blackhole.consume(generator.generate());
    }

}
