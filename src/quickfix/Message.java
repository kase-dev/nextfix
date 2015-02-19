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

import kz.kase.fix.SessionRejectReason;
import quickfix.field.Field;
import quickfix.field.StringField;

import java.text.DecimalFormat;

import static kz.kase.fix.FixProtocol.*;

/**
 * Represents a FIX message.
 */
public class Message extends FieldMap {

    static final long serialVersionUID = -3193357271891865972L;

    protected Header header = new Header();
    protected Trailer trailer = new Trailer();

    // @GuardedBy("this")
    private FieldException exception;

    public Message() {
        // empty
    }

    protected Message(int[] fieldOrder) {
        super(fieldOrder);
    }

    public Message(String string) throws InvalidMessage {
        fromString(string, null, true);
    }

    public Message(String string, boolean validate) throws InvalidMessage {
        fromString(string, null, validate);
    }

    public Message(String string, DataDictionary dd) throws InvalidMessage {
        fromString(string, dd, true);
    }

    public Message(String string, DataDictionary dd, boolean validate) throws InvalidMessage {
        fromString(string, dd, validate);
    }


    @Override
    public Object clone() {
        try {
            final Message message = getClass().newInstance();
            return cloneTo(message);
        } catch (final InstantiationException e) {
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object cloneTo(Message message) {
        message.initializeFrom(this);
        message.header.initializeFrom(getHeader());
        message.trailer.initializeFrom(getTrailer());
        return message;
    }

    @Override
    public String toString() {
        header.setInt(FIELD_BODY_LENGTH, bodyLength());
        trailer.setString(FIELD_CHECKSUM, checkSum());

        final StringBuilder sb = new StringBuilder();
        header.calculateString(sb, null, null);
        calculateString(sb, null, null);
        trailer.calculateString(sb, null, null);

        return sb.toString();
    }

    public int bodyLength() {
        return header.calculateLength() + calculateLength() + trailer.calculateLength();
    }

    private static DecimalFormat checksumFormat = new DecimalFormat("000");

    private int checkSum(String s) {
        final int offset = s.lastIndexOf("\00110=");
        int sum = 0;
        for (int i = 0; i < offset; i++) {
            sum += s.charAt(i);
        }
        return (sum + 1) % 256;
    }

    private String checkSum() {
        return checksumFormat.format((header.calculateTotal() + calculateTotal() + trailer
                .calculateTotal()) % 256);
    }

    public void headerAddGroup(Group group) {
        header.addGroup(group);
    }

    public void headerReplaceGroup(int num, Group group) {
        header.replaceGroup(num, group);
    }

    public Group headerGetGroup(int num, Group group) {
        return header.getGroup(num, group);
    }

    public void headerRemoveGroup(Group group) {
        header.removeGroup(group);
    }

    public boolean headerHasGroup(int field) {
        return header.hasGroup(field);
    }

    public boolean headerHasGroup(int num, int field) {
        return header.hasGroup(num, field);
    }

    public boolean headerHasGroup(int num, Group group) {
        return headerHasGroup(num, group.getFieldTag());
    }

    public boolean headerHasGroup(Group group) {
        return headerHasGroup(group.getFieldTag());
    }

    public void trailerAddGroup(Group group) {
        trailer.addGroup(group);
    }

    public Group trailerGetGroup(int num, Group group)  {
        return trailer.getGroup(num, group);
    }

    public void trailerReplaceGroup(int num, Group group) {
        trailer.replaceGroup(num, group);
    }

    public void trailerRemoveGroup(Group group) {
        trailer.removeGroup(group);
    }

    public boolean trailerHasGroup(int field) {
        return trailer.hasGroup(field);
    }

    public boolean trailerHasGroup(int num, int field) {
        return trailer.hasGroup(num, field);
    }

    public boolean trailerHasGroup(int num, Group group) {
        return trailerHasGroup(num, group.getFieldTag());
    }

    public boolean trailerHasGroup(Group group) {
        return trailerHasGroup(group.getFieldTag());
    }


    public final Header getHeader() {
        return header;
    }

    public final Trailer getTrailer() {
        return trailer;
    }

    public boolean isAdmin() {
        if (header.isSetField(FIELD_MESSAGE_TYPE)) {
            final String msgType = header.getString(FIELD_MESSAGE_TYPE);
            return MessageUtils.isAdminMessage(msgType);
        }
        return false;
    }

    public boolean isApp() {
        return !isAdmin();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && header.isEmpty() && trailer.isEmpty();
    }

    @Override
    public void clear() {
        super.clear();
        header.clear();
        trailer.clear();
    }

    public static class Header extends FieldMap {
        static final long serialVersionUID = -3193357271891865972L;
        private static final int[] EXCLUDED_HEADER_FIELDS = {
                FIELD_BEGIN_STRING,
                FIELD_BODY_LENGTH,
                FIELD_MESSAGE_TYPE
        };

        @Override
        protected void calculateString(StringBuilder builder, int[] excludedFields, int[] postFields) {
            super.calculateString(builder, EXCLUDED_HEADER_FIELDS, postFields);
        }

    }

    public static class Trailer extends FieldMap {
        static final long serialVersionUID = -3193357271891865972L;
        private static final int[] TRAILER_FIELD_ORDER = {
                FIELD_SIGNATURE_LEN,
                FIELD_SIGNATURE,
                FIELD_CHECKSUM
        };

        public Trailer() {
            super(TRAILER_FIELD_ORDER);
        }

        @Override
        protected void calculateString(StringBuilder builder, int[] excludedFields, int[] postFields) {
            super.calculateString(builder, null, new int[]{FIELD_CHECKSUM});
        }
    }

    public void reverseRoute(Header header) {
        this.header.removeField(FIELD_BEGIN_STRING);
        this.header.removeField(FIELD_SENDER_COMP_ID);
        this.header.removeField(FIELD_SENDER_SUB_ID);
        this.header.removeField(FIELD_SENDER_LOCATION_ID);
        this.header.removeField(FIELD_TARGET_COMP_ID);
        this.header.removeField(FIELD_TARGET_SUB_ID);
        this.header.removeField(FIELD_TARGET_LOCATION_ID);

        if (header.isSetField(FIELD_BEGIN_STRING)) {
            copyField(header, FIELD_BEGIN_STRING, FIELD_BEGIN_STRING);

            copyField(header, FIELD_SENDER_COMP_ID, FIELD_TARGET_COMP_ID);
            copyField(header, FIELD_SENDER_SUB_ID, FIELD_TARGET_SUB_ID);
            copyField(header, FIELD_SENDER_LOCATION_ID, FIELD_TARGET_LOCATION_ID);

            copyField(header, FIELD_TARGET_COMP_ID, FIELD_SENDER_COMP_ID);
            copyField(header, FIELD_TARGET_SUB_ID, FIELD_SENDER_SUB_ID);
            copyField(header, FIELD_TARGET_LOCATION_ID, FIELD_SENDER_LOCATION_ID);

            this.header.removeField(FIELD_ON_BEHALF_ON_COMP_ID);
            this.header.removeField(FIELD_ON_BEHALF_ON_SUB_ID);
            this.header.removeField(FIELD_DELIVER_TO_COMP_ID);
            this.header.removeField(FIELD_DELIVER_TO_SUB_ID);

            copyField(header, FIELD_ON_BEHALF_ON_COMP_ID, FIELD_DELIVER_TO_COMP_ID);
            copyField(header, FIELD_ON_BEHALF_ON_SUB_ID, FIELD_DELIVER_TO_SUB_ID);
            copyField(header, FIELD_DELIVER_TO_COMP_ID, FIELD_ON_BEHALF_ON_COMP_ID);
            copyField(header, FIELD_DELIVER_TO_SUB_ID, FIELD_ON_BEHALF_ON_SUB_ID);

            this.header.removeField(FIELD_ON_BEHALF_ON_LOCATION_ID);
            this.header.removeField(FIELD_DELIVER_TO_LOCATION_ID);

            if (header.getString(FIELD_BEGIN_STRING).compareTo(FixVersions.BEGINSTRING_FIX41) >= 0) {
                copyField(header, FIELD_ON_BEHALF_ON_LOCATION_ID, FIELD_DELIVER_TO_LOCATION_ID);
                copyField(header, FIELD_DELIVER_TO_LOCATION_ID, FIELD_ON_BEHALF_ON_LOCATION_ID);
            }
        }
    }

    private void copyField(Header header, int fromField, int toField)  {
        if (header.isSetField(fromField)) {
            final String value = header.getString(fromField);
            if (value.length() > 0) {
                this.header.setString(toField, value);
            }
        }
    }

    void setSessionID(SessionID sesId) {
        header.setString(FIELD_BEGIN_STRING, sesId.getBeginString());
        header.setString(FIELD_SENDER_COMP_ID, sesId.getSenderCompID());
        optionallySetID(header, FIELD_SENDER_SUB_ID, sesId.getSenderSubID());
        optionallySetID(header, FIELD_SENDER_LOCATION_ID, sesId.getSenderLocationID());
        header.setString(FIELD_TARGET_COMP_ID, sesId.getTargetCompID());
        optionallySetID(header, FIELD_TARGET_SUB_ID, sesId.getTargetSubID());
        optionallySetID(header, FIELD_TARGET_LOCATION_ID, sesId.getTargetLocationID());
    }

    private void optionallySetID(Header header, int field, String value) {
        if (!value.equals(SessionID.NOT_SET)) {
            header.setString(field, value);
        }
    }

    public void fromString(String messageData, DataDictionary dd, boolean doValidation)
            throws InvalidMessage {
        parse(messageData, dd, dd, doValidation);
    }

    public void fromString(String messageData, DataDictionary sessionDictionary,
                           DataDictionary applicationDictionary, boolean doValidation) throws InvalidMessage {
        if (sessionDictionary.isAdminMessage(MessageUtils.getMessageType(messageData))) {
            applicationDictionary = sessionDictionary;
        }
        parse(messageData, sessionDictionary, applicationDictionary, doValidation);
    }

    public void parse(String messageData, DataDictionary sessionDataDictionary,
               DataDictionary applicationDataDictionary, boolean doValidation) throws InvalidMessage {
        this.messageData = messageData;

        try {
            parseHeader(sessionDataDictionary, doValidation);
            parseBody(applicationDataDictionary, doValidation);
            parseTrailer(sessionDataDictionary);
            if (doValidation) {
                validateCheckSum(messageData);
            }
        } catch (final FieldException e) {
            exception = e;
        }
    }

    private void validateCheckSum(String messageData) throws InvalidMessage {
        // Body length is checked at the protocol layer
        final int checkSum = trailer.getInt(FIELD_CHECKSUM);
        if (checkSum != checkSum(messageData)) {
            // message will be ignored if checksum is wrong or missing
            throw new InvalidMessage("Expected CheckSum=" + checkSum(messageData) + ", Received CheckSum="
                    + checkSum + " in " + messageData);
        }

    }

    private void parseHeader(DataDictionary dd, boolean doValidation) throws InvalidMessage {
        final boolean validHeaderFieldOrder = isNextField(dd, header, FIELD_BEGIN_STRING)
                && isNextField(dd, header, FIELD_BODY_LENGTH)
                && isNextField(dd, header, FIELD_MESSAGE_TYPE);

        if (doValidation && !validHeaderFieldOrder) {
            // Invalid message preamble (first three fields) is a serious
            // condition and is handled differently from other message parsing errors.
            throw new InvalidMessage("Header fields out of order in " + messageData);
        }

        StringField field = extractField(dd, header);
        while (field != null && isHeaderField(field, dd)) {
            header.setField(field);

            if (dd != null && dd.isGroup(DataDictionary.HEADER_ID, field.getField())) {
                parseGroup(DataDictionary.HEADER_ID, field, dd, header);
            }

            field = extractField(dd, header);
        }
        pushBack(field);
    }

    private boolean isNextField(DataDictionary dd, Header fields, int tag) throws InvalidMessage {
        final StringField field = extractField(dd, header);
        if (field == null || field.getTag() != tag) {
            return false;
        }
        fields.setField(field);
        return true;
    }

    private String getMsgType() throws InvalidMessage {
        return header.getString(FIELD_MESSAGE_TYPE);
    }

    private void parseBody(DataDictionary dd, boolean doValidation) throws InvalidMessage {
        StringField field = extractField(dd, this);
        while (field != null) {
            if (isTrailerField(field.getField())) {
                pushBack(field);
                return;
            }

            if (isHeaderField(field.getField())) {
                // An acceptance test requires the sequence number to
                // be available even if the related field is out of order
                setField(header, field);
                // Group case
                if (dd != null && dd.isGroup(DataDictionary.HEADER_ID, field.getField())) {
                    parseGroup(DataDictionary.HEADER_ID, field, dd, header);
                }
                if (doValidation && dd != null && dd.isCheckFieldsOutOfOrder())
                    throw new FieldException(SessionRejectReason.TAG_SPECIFIED_OUT_OF_REQUIRED_ORDER,
                            field.getTag());
            } else {
                setField(this, field);
                // Group case
                if (dd != null && dd.isGroup(getMsgType(), field.getField())) {
                    parseGroup(getMsgType(), field, dd, this);
                }
            }


            field = extractField(dd, this);
        }
    }

    private void setField(FieldMap fields, StringField field) {
        if (fields.isSetField(field)) {
            throw new FieldException(SessionRejectReason.TAG_APPEARS_MORE_THAN_ONCE, field.getTag());
        }
        fields.setField(field);
    }

    private void parseGroup(String msgType, StringField field, DataDictionary dd, FieldMap parent)
            throws InvalidMessage {
        final DataDictionary.GroupInfo rg = dd.getGroup(msgType, field.getField());
        final DataDictionary groupDataDictionary = rg.getDataDictionary();
        final int[] fieldOrder = groupDataDictionary.getOrderedFields();
        int previousOffset = -1;
        final int groupCountTag = field.getField();
        final int declaredGroupCount = Integer.parseInt(field.getValue());
        parent.setField(groupCountTag, field);
        final int firstField = rg.getDelimeterField();
        boolean firstFieldFound = false;
        Group group = null;
        boolean inGroupParse = true;
        while (inGroupParse) {
            field = extractField(group, dd, parent);
            if (field.getTag() == firstField) {
                if (group != null) {
                    parent.addGroupRef(group);
                }
                group = new Group(groupCountTag, firstField, groupDataDictionary.getOrderedFields());
                group.setField(field);
                firstFieldFound = true;
                previousOffset = -1;
            } else {
                if (groupDataDictionary.isGroup(msgType, field.getField())) {
                    if (firstFieldFound) {
                        parseGroup(msgType, field, groupDataDictionary, group);
                    } else {
                        throw new InvalidMessage("The group " + groupCountTag
                                + " must set the delimiter field " + firstField + " in " + messageData);
                    }
                } else {
                    if (groupDataDictionary.isField(field.getTag())) {
                        if (!firstFieldFound) {
                            throw new FieldException(
                                    SessionRejectReason.REPEATING_GROUP_FIELDS_OUT_OF_ORDER, field
                                    .getTag());
                        }

                        if (fieldOrder != null && dd.isCheckUnorderedGroupFields()) {
                            final int offset = index(fieldOrder, field.getTag());
                            if (offset >= 0) {
                                if (offset > previousOffset) {
                                    previousOffset = offset;
                                } else {
                                    throw new FieldException(
                                            SessionRejectReason.REPEATING_GROUP_FIELDS_OUT_OF_ORDER,
                                            field.getTag());
                                }
                            }
                        }
                        group.setField(field);
                    } else {
                        if (group != null) {
                            parent.addGroupRef(group);
                        }
                        pushBack(field);
                        inGroupParse = false;
                    }
                }
            }
        }
        // For later validation that the group size matches the parsed group count
        parent.setGroupCount(groupCountTag, declaredGroupCount);
    }

    private int index(int[] fieldOrder, int tag) {
        for (int i = 0; i < fieldOrder.length; i++) {
            if (fieldOrder[i] == tag) {
                return i;
            }
        }
        return -1;
    }

    private void parseTrailer(DataDictionary dd) throws InvalidMessage {
        StringField field = extractField(dd, trailer);
        while (field != null) {
            if (!isTrailerField(field, dd)) {
                throw new FieldException(SessionRejectReason.TAG_SPECIFIED_OUT_OF_REQUIRED_ORDER,
                        field.getTag());
            }
            trailer.setField(field);
            field = extractField(dd, trailer);
        }
    }

    static boolean isHeaderField(Field<?> field, DataDictionary dd) {
        return isHeaderField(field.getField())
                || (dd != null && dd.isHeaderField(field.getField()));
    }

    static boolean isHeaderField(int field) {
        switch (field) {
            case FIELD_BEGIN_STRING:
            case FIELD_BODY_LENGTH:
            case FIELD_MESSAGE_TYPE:
            case FIELD_SENDER_COMP_ID:
            case FIELD_TARGET_COMP_ID:
            case FIELD_ON_BEHALF_ON_COMP_ID:
            case FIELD_DELIVER_TO_COMP_ID:
            case FIELD_SECURE_DATA_LEN:
            case FIELD_MSG_SEQ_NUM:
            case FIELD_SENDER_SUB_ID:
            case FIELD_SENDER_LOCATION_ID:
            case FIELD_TARGET_SUB_ID:
            case FIELD_TARGET_LOCATION_ID:
            case FIELD_ON_BEHALF_ON_SUB_ID:
            case FIELD_ON_BEHALF_ON_LOCATION_ID:
            case FIELD_DELIVER_TO_SUB_ID:
            case FIELD_DELIVER_TO_LOCATION_ID:
            case FIELD_POSS_DUP_FLAG:
            case FIELD_POSS_RESEND:
            case FIELD_SENDING_TIME:
            case FIELD_ORIG_SENDING_TIME:
            case FIELD_MESSAGE_ENCODING:
            case FIELD_LAST_MSG_SEQ_NUM_PROCESSED:
            case FIELD_ON_BEHALF_ON_SENDING_TIME:
            case FIELD_APP_VERSION_ID:
            case FIELD_CUSTOM_APP_VERSION_ID:
            case FIELD_NO_HOOPS:
                return true;
            default:
                return false;
        }
    }

    static boolean isTrailerField(Field<?> field, DataDictionary dd) {
        return isTrailerField(field.getField())
                || (dd != null && dd.isTrailerField(field.getField()));
    }

    static boolean isTrailerField(int field) {
        switch (field) {
            case FIELD_SIGNATURE_LEN:
            case FIELD_SIGNATURE:
            case FIELD_CHECKSUM:
                return true;
            default:
                return false;
        }
    }

    //
    // Extract field
    //
    private String messageData;

    private int position;

    private StringField pushedBackField;

    public void pushBack(StringField field) {
        pushedBackField = field;
    }

    private StringField extractField(DataDictionary dataDictionary, FieldMap fields)
            throws InvalidMessage {
        return extractField(null, dataDictionary, fields);
    }

    private StringField extractField(Group group, DataDictionary dataDictionary, FieldMap fields)
            throws InvalidMessage {
        if (pushedBackField != null) {
            final StringField f = pushedBackField;
            pushedBackField = null;
            return f;
        }

        if (position >= messageData.length()) {
            return null;
        }

        final int equalsOffset = messageData.indexOf('=', position);
        if (equalsOffset == -1) {
            throw new InvalidMessage("Equal sign not found in field" + " in " + messageData);
        }

        int tag = -1;
        try {
            tag = Integer.parseInt(messageData.substring(position, equalsOffset));
        } catch (final NumberFormatException e) {
            position = messageData.indexOf('\001', position + 1) + 1;
            throw new InvalidMessage("Bad tag format: " + e.getMessage() + " in " + messageData);
        }

        int sohOffset = messageData.indexOf('\001', equalsOffset + 1);
        if (sohOffset == -1) {
            throw new InvalidMessage("SOH not found at end of field: " + tag + " in " + messageData);
        }

        if (dataDictionary != null && dataDictionary.isDataField(tag)) {
            /* Assume length field is 1 less. */
            int lengthField = tag - 1;
            /* Special case for Signature which violates above assumption. */
            if (tag == 89) {
                lengthField = 93;
            }
            int fieldLength;
            if (group == null) {
                fieldLength = fields.getInt(lengthField);
            } else {
                fieldLength = group.getInt(lengthField);
            }
            sohOffset = equalsOffset + 1 + fieldLength;
        }

        position = sohOffset + 1;
        return new StringField(tag, messageData.substring(equalsOffset + 1, sohOffset));
    }

    /**
     * Queries message structural validity.
     *
     * @return flag indicating whether the message has a valid structure
     */
    /*synchronized*/ boolean hasValidStructure() {
        return exception == null;
    }

    public /*synchronized*/ FieldException getException() {
        return exception;
    }

    /**
     * Returns the first invalid tag, which is all that can be reported
     * in the resulting FIX reject message.
     *
     * @return the first invalid tag
     */
    /*synchronized*/ int getInvalidTag() {
        return exception != null ? exception.getField() : 0;
    }

    /**
     * Returns the msg type specified in a FIX message string.
     *
     * @param message the FIX message string
     * @return the message type
     * @throws quickfix.MessageParseError (QF JNI compatibility)
     */
    public static StringField identifyType(String message) throws MessageParseError {
        try {
            return new StringField(FIELD_MESSAGE_TYPE, MessageUtils.getMessageType(message));
        } catch (final InvalidMessage e) {
            throw new MessageParseError(e.getMessage(), e);
        }
    }

}