package com.boulmier.machinelearning.request;

import java.util.StringTokenizer;

/**
 *
 * @author antho
 * @param <T1> left type
 * @param <T2> right type
 */
public class Property<T1, T2>{
    
    private final T1 a;
    private final T2 b;

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
        return ""+a.toString()+"|"+b.toString()+"";
    }
    
    public static Property<String,String> fromString(String s){
        assert(s.matches("^[a-zA-Z-_]+|[ -_A-Za-z0-9]*"));
        StringTokenizer st = new StringTokenizer(s);
        String a = st.nextToken("|");
        String b = st.nextToken();
        return new Property<>(a,b);
    }
}
