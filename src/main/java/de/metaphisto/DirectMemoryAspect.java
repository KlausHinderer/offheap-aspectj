/*
 * Released to the public domain.
 */
package de.metaphisto;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * This is the aspect that logs the allocation
 */
@Aspect
public class DirectMemoryAspect {

    private static final long START_TIME = System.currentTimeMillis();
    private static final long TEN_SECONDS = 10000L;

    @Before("call(* java.nio.ByteBuffer.allocateDirect(..))")
    public void directMemoryAllocation(JoinPoint joinPoint) throws Throwable {

        //ignore allocations during warmup
        if (System.currentTimeMillis() > (START_TIME + TEN_SECONDS)) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            long id = Thread.currentThread().getId();
            //don't mix the output with concurrent calls, so first write to a StringBuffer an then print
            StringBuilder stringBuilder = new StringBuilder();
            for (StackTraceElement stackTraceElement : stackTrace) {
                stringBuilder.append(id).append("\t").append(stackTraceElement).append("\n");
            }
            System.out.println(stringBuilder.toString());
        }

        //TODO: add more pointcuts (for filechannel.map(), Unsafe, ...)
    }

}