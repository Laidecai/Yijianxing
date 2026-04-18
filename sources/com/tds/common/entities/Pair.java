package com.tds.common.entities;

import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Pair<U, V> {
    public U first;
    public V second;

    private Pair(U u, V v) {
        this.first = u;
        this.second = v;
    }

    public static <U, V> Pair<U, V> create(U u, V v) {
        return new Pair<>(u, v);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair pair = (Pair) obj;
        return Objects.equals(this.first, pair.first) && Objects.equals(this.second, pair.second);
    }

    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    public String toKV() {
        return "{\"" + this.first + "\":\"" + this.second + "\"}";
    }

    public String toString() {
        return "Pair{first=" + this.first + ", second=" + this.second + '}';
    }
}
