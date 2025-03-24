package com.glebestraikh.dualfactorial;

class RecursiveFactorialCalculator implements Runnable {
    private final int number;
    private java.math.BigInteger result;
    private long executionTime;

    public RecursiveFactorialCalculator(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        result = calculateFactorial(java.math.BigInteger.valueOf(number));
        long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
    }

    private java.math.BigInteger calculateFactorial(java.math.BigInteger n) {
        // n = 0 or n = 1
        if (n.equals(java.math.BigInteger.ZERO) || n.equals(java.math.BigInteger.ONE)) {
            return java.math.BigInteger.ONE;
        }

        return n.multiply(calculateFactorial(n.subtract(java.math.BigInteger.ONE)));
    }

    public java.math.BigInteger getResult() {
        return result;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
