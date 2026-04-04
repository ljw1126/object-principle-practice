package org.eternity.calls;

public class CallCollector {
    private Reader reader;

    public CallCollector(Reader reader) {
        this.reader = reader;
    }

    public CallHistory collect(String phone) {
        CallHistory callHistory = new CallHistory(phone);
        reader.read().forEach(callHistory::append);
        return callHistory;
    }
}
