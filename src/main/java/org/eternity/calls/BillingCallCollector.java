package org.eternity.calls;

import java.time.Duration;

public class BillingCallCollector {
    private CallCollector callCollector;

    public BillingCallCollector(CallCollector callCollector) {
        this.callCollector = callCollector;
    }

    public CallHistory collect(String phone) {
        CallHistory callHistory = callCollector.collect(phone);
        
        CallHistory result = new CallHistory(phone);
        for(Call call : callHistory.calls()) {
            if(call.duration().compareTo(Duration.ofSeconds(10)) >= 0) {
                result.append(call);
            }
        }

        return result;
    }
}
