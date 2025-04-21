package com.glebestraikh.stackcalculator;

import com.glebestraikh.stackcalculator.exceptions.executionContext.ArgumentsFormatException;
import com.glebestraikh.stackcalculator.exceptions.executionContext.EmptyContextStackException;
import com.glebestraikh.stackcalculator.exceptions.executionContext.VariableNameException;
import com.glebestraikh.stackcalculator.exceptions.executionContext.VariableOverwritingException;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Pattern;

public class ExecutionContext {
    private final Stack<Double> values;
    private final HashMap<String, Double> variables;
    private final PrintStream out;

    public ExecutionContext(OutputStream out) {
        values = new Stack<>();
        variables = new HashMap<>();
        this.out = new PrintStream(out);
    }

    public void printTopElement(Double obj) {
        out.println(obj);
    }

    public void pushStackValue(Double value) {
        try {
            values.push(Double.valueOf(value.toString()));
        } catch (NumberFormatException | NullPointerException ex) {
            throw new ArgumentsFormatException();
        }
    }

    public Double peekStackValue() {
        if (values.isEmpty()) {
            throw new EmptyContextStackException();
        }
        return values.peek();
    }

    public Double popStackValue() {
        if (values.isEmpty()) {
            throw new EmptyContextStackException();
        }
        return values.pop();
    }

    public boolean isCorrectVariableName(String name) {
        return Pattern.matches("^[a-zA-Z]\\w*$", name);
    }

    public void addVariable(String name, String value) {
        if (name == null || !isCorrectVariableName(name)) {
            throw new VariableNameException();
        }
        if (variables.containsKey(name)) {
            throw new VariableOverwritingException();
        }
        try {
            variables.put(name, Double.valueOf(value));
        } catch (NumberFormatException | NullPointerException ex) {
            throw new ArgumentsFormatException();
        }
    }

    public Double getVariableValue(String name) {
        return variables.get(name);
    }
}

