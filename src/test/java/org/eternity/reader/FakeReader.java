package org.eternity.reader;

import java.util.List;
import org.eternity.calls.Call;
import org.eternity.calls.Reader;

public class FakeReader implements Reader {
    private List<Call> calls;

    public FakeReader(Call... calls) {
        this.calls = List.of(calls);
    }

    @Override
    public List<Call> read() {
        return calls;
    }
     
}
