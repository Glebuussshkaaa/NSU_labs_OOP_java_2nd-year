package com.glebestraikh.dualfactorial;

class IterativeFactorialCalculator implements Runnable {
    private final int number;
    private java.math.BigInteger result;
    private long executionTime;

    public IterativeFactorialCalculator(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        result = calculateFactorial(number);
        long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
    }

    private java.math.BigInteger calculateFactorial(int n) {
        java.math.BigInteger result = java.math.BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            result = result.multiply(java.math.BigInteger.valueOf(i));
        }

        return result;
    }

    public java.math.BigInteger getResult() {
        return result;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
