package org.eternity.calls;

import java.time.Duration;

public class BillingCallCollector extends CallCollector {
    public BillingCallCollector(Reader reader) {
        super(reader);
    }

    @Override
    public CallHistory collect(String phone) {
        CallHistory callHistory = super.collect(phone);
        
        CallHistory result = new CallHistory(phone);
        for(Call call : callHistory.calls()) {
            if(call.duration().compareTo(Duration.ofSeconds(10)) >= 0) {
                result.append(call);
            }
        }

        return result;
    }
}
