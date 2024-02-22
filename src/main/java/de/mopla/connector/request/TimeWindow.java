package de.mopla.connector.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.mopla.connector.request.serializer.CustomTimeWindowDeserializer;
import de.mopla.connector.request.serializer.CustomTimeWindowSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@JsonSerialize(using = CustomTimeWindowSerializer.class)
@JsonDeserialize(using = CustomTimeWindowDeserializer.class)
public final class TimeWindow {
    private Long start;
    private Long end;

    /**
     * Creates a time-window
     * @param start start in epoch seconds
     * @param end end in epoch seconds
     */
    public TimeWindow(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public TimeWindow(LocalDateTime start, LocalDateTime end) {
        this.start = start.atZone(ZoneId.of("Europe/Berlin")).toEpochSecond();
        this.end = end.atZone(ZoneId.of("Europe/Berlin")).toEpochSecond();
    }

    // todo make it easier: constructor with localdatetime

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TimeWindow) obj;
        return Objects.equals(this.start, that.start) &&
                Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "TimeWindow[" +
                "start=" + start + ", " +
                "end=" + end + ']';
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

}
