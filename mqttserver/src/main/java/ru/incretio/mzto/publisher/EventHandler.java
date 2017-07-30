package ru.incretio.mzto.publisher;

import java.nio.file.WatchEvent;

interface EventHandler {
    void process(WatchEvent.Kind<?> kind, String fileName);
}
