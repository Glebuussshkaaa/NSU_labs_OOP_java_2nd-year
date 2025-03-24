package com.glebestraikh.classexplorer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class ClassExplorer {
    public void dig(Object obj) {
        if (obj == null) {
            System.out.println("Объект не может быть null");
            return;
        }

        Class<?> clazz = obj.getClass();
        System.out.println("Анализ класса: " + clazz.getName());

        System.out.println("\nПОЛЯ КЛАССА:");
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            System.out.println("У класса нет объявленных полей");
        } else {
            for (Field field : fields) {
                System.out.println("  " + getModifierString(field.getModifiers()) +
                        field.getType().getSimpleName() + " " +
                        field.getName());
            }
        }

        System.out.println("\nМЕТОДЫ КЛАССА:");
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length == 0) {
            System.out.println("У класса нет объявленных методов");
        } else {
            for (Method method : methods) {
                StringBuilder params = new StringBuilder();
                Class<?>[] paramTypes = method.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    if (i > 0) {
                        params.append(", ");
                    }
                    params.append(paramTypes[i].getSimpleName());
                }

                System.out.println("  " + getModifierString(method.getModifiers()) +
                        method.getReturnType().getSimpleName() + " " +
                        method.getName() + "(" + params + ")");
            }
        }
    }

    private String getModifierString(int modifiers) {
        StringBuilder sb = new StringBuilder();

        if (Modifier.isPublic(modifiers)) sb.append("public ");
        if (Modifier.isProtected(modifiers)) sb.append("protected ");
        if (Modifier.isPrivate(modifiers)) sb.append("private ");
        if (Modifier.isStatic(modifiers)) sb.append("static ");
        if (Modifier.isFinal(modifiers)) sb.append("final ");
        if (Modifier.isAbstract(modifiers)) sb.append("abstract ");

        return sb.toString();
    }
}