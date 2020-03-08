package dev.titanlabs.punishment.objects.user;

import java.util.UUID;

public class Note {
    private final UUID subject;
    private final UUID executor;
    private final long noteTime;
    private long lastEditTime;
    private String contents;

    public Note(String contents, UUID subject, UUID executor) {
        this.contents = contents;
        this.subject = subject;
        this.executor = executor;
        this.noteTime = System.currentTimeMillis();
        this.lastEditTime = System.currentTimeMillis();
    }

    public UUID getSubject() {
        return this.subject;
    }

    public UUID getExecutor() {
        return this.executor;
    }

    public long getNoteTime() {
        return this.noteTime;
    }

    public long getLastEditTime() {
        return this.lastEditTime;
    }

    public void setLastEditTime(long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getContents() {
        return this.contents;
    }

    public void updateContents(String contents) {
        this.contents = contents;
        this.lastEditTime = System.currentTimeMillis();
    }
}
