package com.boulmier.machinelearning.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author antho
 * @param <T1> left type
 * @param <T2> right type
 */
public class Property<T1, T2>{
    
    private final T1 a;
    private final T2 b;
    private static final Pattern p = Pattern.compile("^([a-zA-Z-_]+)\\|([ -_A-Za-z0-9]*)");
    
    public Property(T1 a, T2 b) {
        this.a = a;
        this.b = b;
    }

    public T1 getA() {
        return a;
    }

    public T2 getB() {
        return b;
    }

    @Override
    public String toString() {
        return a.toString()+"|"+b.toString();
    }
    
    public static Property<String,String> fromString(String s) throws BadPropertyFormattingException{
        Matcher m = Property.p.matcher(s);
        if(!m.matches())
            throw new BadPropertyFormattingException();
        return RequestProperty.fromString(m.group(1));
    }
}
