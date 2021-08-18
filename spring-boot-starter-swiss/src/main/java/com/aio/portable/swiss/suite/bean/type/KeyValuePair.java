package com.aio.portable.swiss.suite.bean.type;

import java.util.Map;
import java.util.Objects;

public class KeyValuePair<K,V> implements Map.Entry<K,V> {
//    @Stable
    final K key;
//    @Stable
    final V value;

    public KeyValuePair(K k, V v) {
        key = Objects.requireNonNull(k);
        value = Objects.requireNonNull(v);
    }

    /**
     * Gets the key from this holder.
     *
     * @return the key
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * Gets the value from this holder.
     *
     * @return the value
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * Throws {@link UnsupportedOperationException}.
     *
     * @param value ignored
     * @return never returns normally
     */
    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException("not supported");
    }

    /**
     * Compares the specified object with this entry for equality.
     * Returns {@code true} if the given object is also a map entry and
     * the two entries' keys and values are equal. Note that key and
     * value are non-null, so equals() can be called safely on them.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        return key.equals(e.getKey()) && value.equals(e.getValue());
    }

    /**
     * Returns the hash code value for this map entry. The hash code
     * is {@code key.hashCode() ^ value.hashCode()}. Note that key and
     * value are non-null, so hashCode() can be called safely on them.
     */
    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }

    /**
     * Returns a String representation of this map entry.  This
     * implementation returns the string representation of this
     * entry's key followed by the equals character ("{@code =}")
     * followed by the string representation of this entry's value.
     *
     * @return a String representation of this map entry
     */
    @Override
    public String toString() {
        return key + "=" + value;
    }
}
