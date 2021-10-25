package ru.akirakozov.sd.refactoring;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MockWriter extends PrintWriter {
    public MockWriter() {
        super(new StringWriter());
    }

    @Override
    public String toString() {
        return out.toString();
    }
}
