package org.eternity.calls;

public class CallCollector {
    private Reader reader;

    public CallCollector(Reader reader) {
        this.reader = reader;
    }

    public CallHistory collect(String phone) {
        CallHistory callHistory = new CallHistory(phone);
        for (Call call : reader.read()) {
            if (call.from().equals(phone)) {
                callHistory.append(call);
            }
        }
        return callHistory;
    }
}
