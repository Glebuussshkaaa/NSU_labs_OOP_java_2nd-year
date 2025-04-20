package com.glebestraikh.stackcalculator.commands;

import com.glebestraikh.stackcalculator.Context;

import java.util.List;

public interface Command {
    void run(List<String> args, Context context);
}