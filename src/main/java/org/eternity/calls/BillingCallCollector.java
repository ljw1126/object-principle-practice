package org.eternity.calls;

public class BillingCallCollector {
    private CallCollector callCollector;

    public BillingCallCollector(CallCollector callCollector) {
        this.callCollector = callCollector;
    }

    public CallHistory collect(String phone) {
        return callCollector.collect(phone)
                        .find(call -> call.duration().toSeconds() >= 10);
    }
}
