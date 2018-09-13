package com.swiftcode.domain.base;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.lang.reflect.Field;

/**
 * @author chen
 */
@SuppressWarnings("rawtypes")
public class AbstractVo implements Comparable {
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        return other.getClass() == this.getClass() && EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public int compareTo(final Object other) {
        if (other == null) {
            return - 1;
        }

        //Other value check
        Object otherValue = null;
        try {
            otherValue = getValue(other);
        } catch (final Exception e) {
            return 0;
        }
        if (otherValue == null) {
            return - 1;
        }

        Object thisValue;
        try {
            thisValue = getValue(this);
        } catch (final Exception e) {
            return 0;
        }
        if (thisValue == null) {
            return 1;
        }

        return thisValue.toString().compareTo(otherValue.toString());
    }

    /**
     * 获取字段值
     * @param instance instance
     * @return value
     * @throws Exception Exception
     */
    private Object getValue(final Object instance) throws Exception {
        final Field valueField = instance.getClass().getDeclaredField("value");
        valueField.setAccessible(true);
        return valueField.get(instance);
    }
}
