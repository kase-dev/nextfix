/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved. 
 *
 * This file is part of the QuickFIX FIX Engine 
 *
 * This file may be distributed under the terms of the quickfixengine.org 
 * license as defined by quickfixengine.org and appearing in the file 
 * LICENSE included in the packaging of this file. 
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING 
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.quickfixengine.org/LICENSE for licensing information. 
 *
 * Contact ask@quickfixengine.org if any conditions of this licensing 
 * are not clear to you.
 ******************************************************************************/

package quickfix;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.SessionRejectReason;
import quickfix.field.BytesField;
import quickfix.field.Field;
import quickfix.field.IntField;
import quickfix.field.StringField;
import quickfix.field.converter.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * Field container used by messages, groups, and composites.
 */
public abstract class FieldMap implements Serializable {

    static final long serialVersionUID = -3193357271891865972L;
    public static final int DEFAULT_INT = 0;
    public static final char DEFAULT_CHAR = (char) 0;
    public static final double DEFAULT_DOUBLE = 0.0;
    public static final boolean DEFAULT_BOOL = false;
    public static final BigDecimal DEFAULT_DECIMAL = BigDecimal.ZERO;
    public static final Date DEFAULT_DATE = new Date(0);

    private final int[] fieldOrder;

    private final TreeMap<Integer, Field<?>> fields;

    private final TreeMap<Integer, List<Group>> groups = new TreeMap<Integer, List<Group>>();

    protected FieldMap(int[] fieldOrder) {
        this.fieldOrder = fieldOrder;
        fields = new TreeMap<Integer, Field<?>>(fieldOrder != null
                ? new FieldOrderComparator()
                : null);
    }

    protected FieldMap() {
        this(null);
    }

    public int[] getFieldOrder() {
        return fieldOrder;
    }

    public void clear() {
        fields.clear();
        groups.clear();
    }

    public boolean isEmpty() {
        return fields.size() == 0;
    }

    private class FieldOrderComparator implements Comparator<Integer>, Serializable {
        static final long serialVersionUID = 3416006398018829270L;

        public int compare(Integer tag1, Integer tag2) {
            final int index1 = indexOf(tag1, getFieldOrder());
            final int index2 = indexOf(tag2, getFieldOrder());

            if ((index1 != Integer.MAX_VALUE) && (index2 != Integer.MAX_VALUE)) {
                // We manage two ordered fields
                return index1 != index2 ? (index1 < index2 ? -1 : 1) : 0;
            } else {
                if (index1 != index2) {
                    return (index1 == Integer.MAX_VALUE ? 1 : -1);
                } else {
                    // index1 and index2 equals to Integer.MAX_VALUE so use the
                    // tags
                    return tag1.intValue() != tag2.intValue() ? (tag1 < tag2
                            ? -1
                            : 1) : 0;
                }
            }
        }

        private int indexOf(int tag, int[] order) {
            if (order != null) {
                for (int i = 0; i < order.length; i++) {
                    if (tag == order[i]) {
                        return i;
                    }
                }
            }
            return Integer.MAX_VALUE;
        }
    }

    public void setFields(FieldMap fieldMap) {
        fields.clear();
        fields.putAll(fieldMap.fields);
    }

    protected void setComponent(MessageComponent component) {
        component.copyTo(this);
    }

    protected void getComponent(MessageComponent component) {
        component.clear();
        component.copyFrom(this);
    }

    public void setGroups(FieldMap fieldMap) {
        groups.clear();
        groups.putAll(fieldMap.groups);
    }

    protected void setGroups(int key, List<Group> groupList) {
        groups.put(key, groupList);
    }

    public void setString(int field, String value) {
        setField(new StringField(field, value));
    }

    public void setBytes(int field, byte[] value) {
        setField(field, new BytesField(field, value));
    }

    public void setBoolean(int field, boolean value) {
        final String s = BooleanConverter.convert(value);
        setField(new StringField(field, s));
    }

    public void setChar(int field, char value) {
        final String s = Character.toString(value);
        setField(new StringField(field, s));
    }

    public void setInt(int field, int value) {
        final String s = Integer.toString(value);
        setField(new StringField(field, s));
    }

    public void setLong(int field, long value) {
        final String s = Long.toString(value);
        setField(new StringField(field, s));
    }

    public void setDouble(int field, double value) {
        setDouble(field, value, 0);
    }

    public void setDouble(int field, double value, int padding) {
        final String s = DoubleConverter.convert(value, padding);
        setField(new StringField(field, s));
    }

    public void setDecimal(int field, BigDecimal value) {
        setField(new StringField(field, DecimalConverter.convert(value)));
    }

    public void setDecimal(int field, BigDecimal value, int padding) {
        final String s = DecimalConverter.convert(value, padding);
        setField(new StringField(field, s));
    }

    public void setUtcTimeStamp(int field, Date value) {
        setUtcTimeStamp(field, value, false);
    }

    public void setUtcTimeStamp(int field, Date value, boolean includeMilliseconds) {
        final String s = UtcTimestampConverter.convert(value, includeMilliseconds);
        setField(new StringField(field, s));
    }

    public void setUtcTimeOnly(int field, Date value) {
        setUtcTimeOnly(field, value, false);
    }

    public void setUtcTimeOnly(int field, Date value, boolean includeMillseconds) {
        final String s = UtcTimeOnlyConverter.convert(value, includeMillseconds);
        setField(new StringField(field, s));
    }

    public void setUtcDateOnly(int field, Date value) {
        final String s = UtcDateOnlyConverter.convert(value);
        setField(new StringField(field, s));
    }

    public String getString(int field) {
        StringField fld = getField(field);
        return fld != null ? fld.getValue() : null;
    }

    StringField getField(int field) {
        return (StringField) fields.get(field);
    }

    public /*ancalled*/ Field<?> getField(int field, Field<?> defaultValue) {
        final Field<?> f = fields.get(field);
        if (f == null) {
            return defaultValue;
        }
        return f;
    }

    public boolean getBoolean(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_BOOL;
        try {
            return BooleanConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public char getChar(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_CHAR;
        try {
            return CharConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public int getInt(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_INT;
        try {
            return IntConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public long getLong(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_INT;
        try {
            return IntConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public double getDouble(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_DOUBLE;
        try {
            return DoubleConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public BigDecimal getDecimal(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_DECIMAL;
        try {
            return DecimalConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public Date getUtcTimeStamp(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_DATE;
        try {
            return UtcTimestampConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public Date getUtcTimeOnly(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_DATE;
        try {
            return UtcTimeOnlyConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public Date getUtcDateOnly(int field) {
        StringField fld = getField(field);
        if (fld == null || FixProtocol.NOT_SET.equals(fld.getValue())) return DEFAULT_DATE;
        try {
            return UtcDateOnlyConverter.convert(fld.getValue());
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public void setField(int key, Field<?> field) {
        fields.put(key, field);
    }

    protected void setField(StringField field) {
        if (field.getValue() == null) {
            throw new NullPointerException("Null field values are not allowed.");
        }
        fields.put(field.getField(), field);
    }


    public StringField getField(StringField field) {
        return (StringField) getFieldInternal(field);
    }

    private Field<String> getFieldInternal(Field<String> field) {
        field.setObject(getField(field.getField()).getObject());
        return field;
    }


    private FieldException newIncorrectDataException(FieldConvertError e, int tag) {
        return new FieldException(SessionRejectReason.INCORRECT_DATA_FORMAT_FOR_VALUE,
                e.getMessage(), tag);
    }

    public boolean isSetField(int field) {
        return fields.containsKey(field);
    }

    public boolean isSetField(Field<?> field) {
        return isSetField(field.getField());
    }

    public void removeField(int field) {
        fields.remove(field);
    }

    public Iterator<Field<?>> iterator() {
        return fields.values().iterator();
    }

    protected void initializeFrom(FieldMap source) {
        fields.clear();
        fields.putAll(source.fields);
        for (Entry<Integer, List<Group>> entry : source.groups.entrySet()) {
            final List<Group> clonedMembers = new ArrayList<Group>();
            final List<Group> groupMembers = entry.getValue();
            for (final Group originalGroup : groupMembers) {
                final Group clonedGroup = new Group(originalGroup.getFieldTag(),
                        originalGroup.delim(), originalGroup.getFieldOrder());
                clonedGroup.initializeFrom(originalGroup);
                clonedMembers.add(clonedGroup);
            }
            groups.put(entry.getKey(), clonedMembers);
        }
    }

    private boolean isGroupField(int field) {
        return groups.containsKey(field);
    }

    protected void calculateString(StringBuilder builder, int[] preFields, int[] postFields) {
        if (preFields != null) {
            for (final int preField : preFields) {
                final Field<?> field = getField(preField, null);
                if (field != null) {
                    field.toString(builder);
                    builder.append('\001');
                }
            }
        }

        for (final Field<?> field2 : fields.values()) {
            final int tag = field2.getField();
            if (!isOrderedField(tag, preFields) && !isOrderedField(tag, postFields)
                    && !isGroupField(tag)) {
                field2.toString(builder);
                builder.append('\001');
            } else if (isGroupField(tag) && isOrderedField(tag, fieldOrder)
                    && getGroupCount(tag) > 0) {
                final List<Group> groups = getGroups(tag);
                field2.toString(builder);
                builder.append('\001');
                for (Group group : groups) {
                    group.calculateString(builder, preFields, postFields);
                }
            }
        }

        for (final Entry<Integer, List<Group>> entry : groups.entrySet()) {
            final Integer groupCountTag = entry.getKey();
            if (!isOrderedField(groupCountTag, fieldOrder)) {
                final List<Group> groups = entry.getValue();
                int groupCount = groups.size();
                if (groupCount > 0) {
                    final IntField countField = new IntField(groupCountTag.intValue(), groupCount);
                    countField.toString(builder);
                    builder.append('\001');
                    for (Group group : groups) {
                        group.calculateString(builder, preFields, postFields);
                    }
                }
            }
        }

        if (postFields != null) {
            for (final int postField : postFields) {
                final Field<?> field = getField(postField, null);
                field.toString(builder);
                builder.append('\001');
            }
        }
    }

    private boolean isOrderedField(int field, int[] afieldOrder) {
        if (afieldOrder != null) {
            for (final int element : afieldOrder) {
                if (field == element) {
                    return true;
                }
            }
        }
        return false;
    }

    int calculateLength() {
        int result = 0;
        int length;
        for (final Field<?> field : fields.values()) {
            if (field.getField() == FixProtocol.FIELD_BEGIN_STRING ||
                    field.getField() == FixProtocol.FIELD_BODY_LENGTH ||
                    field.getField() == FixProtocol.FIELD_CHECKSUM ||
                    isGroupField(field.getField())) {
                continue;
            }
            length = field.getLength();
            result += length;
        }

        for (Entry<Integer, List<Group>> entry : groups.entrySet()) {
            final List<Group> groupList = entry.getValue();
            if (!groupList.isEmpty()) {
                final IntField groupField = new IntField(entry.getKey());
                groupField.setValue(groupList.size());
                length = groupField.getLength();
                result += length;
                for (final Group group : groupList) {
                    length = group.calculateLength();
                    result += length;
                }
            }
        }
        return result;

    }

    int calculateTotal() {

        int result = 0;
        for (final Field<?> f : fields.values()) {
            if (f.getField() == FixProtocol.FIELD_CHECKSUM ||
                    isGroupField(f.getField())) {
                continue;
            }
            result += f.getTotal();
        }

        for (Entry<Integer, List<Group>> entry : groups.entrySet()) {
            final List<Group> groupList = entry.getValue();
            if (!groupList.isEmpty()) {
                final IntField groupField = new IntField(entry.getKey());
                groupField.setValue(groupList.size());
                result += groupField.getTotal();
                for (final Group group : groupList) {
                    result += group.calculateTotal();
                }
            }
        }

        return result;
    }

    /**
     * Returns the number of groups associated with the specified count tag.
     *
     * @param tag the count tag number
     * @return the number of times the group repeats
     */
    public int getGroupCount(int tag) {
        return getGroups(tag).size();
    }

    public Iterator<Integer> groupKeyIterator() {
        return groups.keySet().iterator();
    }

    Map<Integer, List<Group>> getGroups() {
        return groups;
    }

    public void addGroup(Group group) {
        final int countTag = group.getFieldTag();
        final List<Group> currentGroups = getGroups(countTag);
        currentGroups.add(new Group(group));
        setGroupCount(countTag, currentGroups.size());
    }

    public void addGroupRef(Group group) {
        int countTag = group.getFieldTag();
        List<Group> currentGroups = getGroups(countTag);
        currentGroups.add(group);
        setGroupCount(countTag, currentGroups.size());
    }

    protected void setGroupCount(int countTag, int groupSize) {
        StringField count;
        if (groupSize == 1) {
            count = new StringField(countTag, "1");
            setField(countTag, count);
        } else {
            count = getField(countTag);
        }
        count.setValue(Integer.toString(groupSize));

    }

    public List<Group> getGroups(int field) {
        List<Group> groupList = groups.get(field);
        if (groupList == null) {
            groupList = new ArrayList<Group>();
            groups.put(field, groupList);
        }
        return groupList;
    }

    public Group getGroup(int num, Group group) {
        final List<Group> groupList = getGroups(group.getFieldTag());
        if (num > groupList.size()) {
            throw new IllegalStateException(group.getFieldTag() + ", index=" + num);
        }
        final Group grp = groupList.get(num - 1);
        group.setFields(grp);
        group.setGroups(grp);
        return group;
    }

    public Group getGroup(int num, int groupTag) {
        List<Group> groupList = getGroups(groupTag);
        if (num > groupList.size()) {
            throw new IllegalStateException(groupTag + ", index=" + num);
        }
        return groupList.get(num - 1);
    }

    public void replaceGroup(int num, Group group) {
        final int offset = num - 1;
        final List<Group> groupList = getGroups(group.getFieldTag());
        if (offset < 0 || offset >= groupList.size()) {
            return;
        }
        groupList.set(offset, new Group(group));
    }

    public void removeGroup(int field) {
        getGroups(field).clear();
        removeField(field);
    }

    public void removeGroup(int num, int field) {
        final List<Group> groupList = getGroups(field);
        if (num <= groupList.size()) {
            groupList.remove(num - 1);
        }
        if (groupList.size() > 0) {
            setGroupCount(field, groupList.size());
        }
    }

    public void removeGroup(int num, Group group) {
        removeGroup(num, group.getFieldTag());
    }

    public void removeGroup(Group group) {
        removeGroup(group.getFieldTag());
    }

    public boolean hasGroup(int field) {
        return groups.containsKey(field);
    }

    public boolean hasGroup(int num, int field) {
        return hasGroup(field) && num <= getGroups(field).size();
    }

    public boolean hasGroup(int num, Group group) {
        return hasGroup(num, group.getFieldTag());
    }

    public boolean hasGroup(Group group) {
        return hasGroup(group.getFieldTag());
    }

}
