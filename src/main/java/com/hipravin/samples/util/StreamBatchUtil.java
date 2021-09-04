package com.hipravin.samples.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

//from: https://stackoverflow.com/questions/30641383/java-8-stream-with-batch-processing
public abstract class StreamBatchUtil {

    private StreamBatchUtil() {}

    public static <T> Stream<List<T>> batches(Stream<T> stream, int batchSize) {
        if(batchSize <= 0) {
            throw new IllegalArgumentException("batchSize should be positive: " + batchSize);
        }
        if (batchSize == 1) {
            return stream.map(List::of);
        }
        return StreamSupport.stream(
                new BatchSpliterator<T>(stream.spliterator(), batchSize), stream.isParallel());
    }

    private static class BatchSpliterator<E> implements Spliterator<List<E>> {

        private final Spliterator<E> base;
        private final int batchSize;

        public BatchSpliterator(Spliterator<E> base, int batchSize) {
            this.base = base;
            this.batchSize = batchSize;
        }

        @Override
        public boolean tryAdvance(Consumer<? super List<E>> batchConsumer) {
            final List<E> batch = new ArrayList<>(batchSize);
            for (int i = 0; i < batchSize && base.tryAdvance(batch::add); i++) {
            }
            if (batch.isEmpty()) {
                return false;
            }
            batchConsumer.accept(batch);
            return true;
        }

        @Override
        public Spliterator<List<E>> trySplit() {
            return null;
            //not sure below is correct
//            if (base.estimateSize() <= batchSize) {
//                return null;
//            }
//            final Spliterator<E> splitBase = this.base.trySplit();
//            return splitBase == null ? null
//                    : new BatchSpliterator<>(splitBase, batchSize);
        }

        @Override
        public long estimateSize() {
            final double baseSize = base.estimateSize();
            return baseSize == 0 ? 0
                    : (long) Math.ceil(baseSize / (double) batchSize);
        }

        @Override
        public int characteristics() {
            return base.characteristics();
        }
    }
}
