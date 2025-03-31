package com.glebestraikh.stackcalculator;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;

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
        }
        catch (NumberFormatException |
               NullPointerException ex) {
            throw new ArgumentsFormatException();
        }
    }

    public Double peekStackValue() {
        try {
            return values.peek();
        }
        catch (java.util.EmptyStackException ex) {
            throw new EmptyStackException();
        }
    }

    public Double popStackValue() {
        try {
            return values.pop();
        }
        catch (java.util.EmptyStackException ex) {
            throw new EmptyStackException();
        }
    }

    public boolean isCorrectVariableName(String name) {
        Pattern namePattern = Pattern.compile("^[a-zA-Z]\\w*$");
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public void addVariable(Object name, Object value) {
        if (name == null || !isCorrectVariableName(name.toString()))
            throw new VariableNameException();

        if (variables.containsKey(name.toString()))
            throw new VariableOverwritingException();

        try {
            variables.put(name.toString(),
                    Double.valueOf(value.toString()));
        }
        catch (NumberFormatException |
               NullPointerException ex) {
            throw new ArgumentsFormatException();
        }
    }

    public Double getVariable(String name) {
        return variables.get(name);
    }
}
