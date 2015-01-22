package kz.kase.fix.messages;

import kz.kase.fix.extend.PosKeyType;

@Deprecated
public class Position {

    private final PosKeyType type;
    private final long instrId;

    private Long initial;
    private Long current;
    private Long blocked;
    private Long planned;

    public Position(PosKeyType type, Long instrId) {
        this.type = type;
        this.instrId = instrId != null ? instrId : 0L;
    }

    public PosKeyType getType() {
        return type;
    }

    public Long getInstrId() {
        return instrId;
    }

    public Long getInitial() {
        return initial;
    }

    public void setInitial(Long initial) {
        this.initial = initial;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getBlocked() {
        return blocked;
    }

    public void setBlocked(Long blocked) {
        this.blocked = blocked;
    }

    public Long getPlanned() {
        return planned;
    }

    public void setPlanned(Long planned) {
        this.planned = planned;
    }
}
