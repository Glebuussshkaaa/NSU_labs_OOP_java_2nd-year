package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.ExecutionContext;

import java.util.List;

public interface Command {
    void run(List<String> CommandArgs, ExecutionContext context);
}