package ru.incretio.mzto.publisher;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

class WatcherDirectory {
    private final EventHandler eventHandler;

    public WatcherDirectory(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void start(String directoryURI) throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(directoryURI);
        path.register(watcher,
                ENTRY_CREATE,
                ENTRY_DELETE);

        boolean isExit = false;
        while (!isExit) {
            WatchKey watchKey = null;
            try {
                watchKey = watcher.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                isExit = true;
            }

            if (watchKey == null) {
                continue;
            }

            for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                WatchEvent.Kind<?> kind = watchEvent.kind();
                if (kind == OVERFLOW) {
                    continue;
                }

                Path currentPath = ((WatchEvent<Path>) watchEvent).context();

                if (eventHandler != null) {
                    eventHandler.process(kind, currentPath.getFileName().toString());
                }
            }

            if (!watchKey.reset()) {
                break;
            }
        }
    }

}
