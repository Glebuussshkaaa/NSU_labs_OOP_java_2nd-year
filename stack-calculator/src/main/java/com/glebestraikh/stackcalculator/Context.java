package com.glebestraikh.stackcalculator;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// нужно перенаазвать класс
// потмоу что у java такой же есть
public class Context {
    private final Stack<Double> values;
    private final HashMap<String, Double> variables;
    private final PrintStream out;

    public Context(OutputStream out) {
        values = new Stack<>();
        variables = new HashMap<>();
        this.out = new PrintStream(out);
    }

    public void println(Object obj) {
        out.println(obj);
    }

    public void pushStackValue(Object value) {
        try {
            values.push(Double.valueOf(value.toString()));
        } catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException("Invalid number format for stack value", ex);
        }
    }

    public Double peekStackValue() {
        if (values.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return values.peek();
    }

    public Double popStackValue() {
        if (values.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return values.pop();
    }

    public boolean isCorrectVariableName(String name) {
        Pattern namePattern = Pattern.compile("^[a-zA-Z]\\w*$");
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public void addVariable(Object name, Object value) {
        if (name == null || !isCorrectVariableName(name.toString())) {
            throw new IllegalArgumentException("Invalid variable name");
        }
        if (variables.containsKey(name.toString())) {
            throw new IllegalStateException("Variable already exists");
        }
        try {
            variables.put(name.toString(), Double.valueOf(value.toString()));
        } catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException("Invalid number format for variable value", ex);
        }
    }

    public Double getVariable(String name) {
        return variables.get(name);
    }
}
