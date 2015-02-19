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

import kz.kase.fix.ApplVerID;
import kz.kase.fix.FixProtocol;
import org.quickfixj.QFJException;
import quickfix.Message.Header;

import java.util.HashMap;
import java.util.Map;

public class MessageUtils {

    private static final char FIELD_SEPARATOR = '\001';
    public static final String ADMIN_MSG_TYPES = "0A12345";

    public static SessionID getSessionID(Message fixMessage) {
        final Header header = fixMessage.getHeader();
        return new SessionID(
                getFieldOrDefault(header, FixProtocol.FIELD_BEGIN_STRING, null),
                getFieldOrDefault(header, FixProtocol.FIELD_SENDER_COMP_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_SENDER_SUB_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_SENDER_LOCATION_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_TARGET_COMP_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_TARGET_SUB_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_TARGET_LOCATION_ID, null),
                null);
    }

    public static SessionID getSessionID(String messageString) {
        return new SessionID(
                getStringField(messageString, FixProtocol.FIELD_BEGIN_STRING),
                getStringField(messageString, FixProtocol.FIELD_SENDER_COMP_ID),
                getStringField(messageString, FixProtocol.FIELD_SENDER_SUB_ID),
                getStringField(messageString, FixProtocol.FIELD_SENDER_LOCATION_ID),
                getStringField(messageString, FixProtocol.FIELD_TARGET_COMP_ID),
                getStringField(messageString, FixProtocol.FIELD_TARGET_SUB_ID),
                getStringField(messageString, FixProtocol.FIELD_TARGET_LOCATION_ID),
                null);
    }

    public static SessionID getReverseSessionID(Message fixMessage) {
        final Header header = fixMessage.getHeader();
        return new SessionID(
                getFieldOrDefault(header, FixProtocol.FIELD_BEGIN_STRING, null),
                getFieldOrDefault(header, FixProtocol.FIELD_TARGET_COMP_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_TARGET_SUB_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_TARGET_LOCATION_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_SENDER_COMP_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_SENDER_SUB_ID, null),
                getFieldOrDefault(header, FixProtocol.FIELD_SENDER_LOCATION_ID, null),
                null);
    }

    public static SessionID getReverseSessionID(String messageString) {
        return new SessionID(
                getStringField(messageString, FixProtocol.FIELD_BEGIN_STRING),
                getStringField(messageString, FixProtocol.FIELD_TARGET_COMP_ID),
                getStringField(messageString, FixProtocol.FIELD_TARGET_SUB_ID),
                getStringField(messageString, FixProtocol.FIELD_TARGET_LOCATION_ID),
                getStringField(messageString, FixProtocol.FIELD_SENDER_COMP_ID),
                getStringField(messageString, FixProtocol.FIELD_SENDER_SUB_ID),
                getStringField(messageString, FixProtocol.FIELD_SENDER_LOCATION_ID),
                null);
    }

    private static String getFieldOrDefault(FieldMap fields, int tag, String defaultValue) {
        if (fields.isSetField(tag)) {
            return fields.getString(tag);
        } else {
            return defaultValue;
        }
    }

    /**
     * Utility method for parsing a mesasge. This should only be used for parsing messages from
     * FIX versions 4.4 or earlier.
     *
     * @param messageFactory
     * @param dataDictionary
     * @param messageString
     * @return the parsed message
     * @throws quickfix.InvalidMessage
     */
    public static Message parse(MessageFactory messageFactory, DataDictionary dataDictionary,
                                String messageString) throws InvalidMessage {
        final int index = messageString.indexOf(FIELD_SEPARATOR);
        if (index < 0) {
            throw new InvalidMessage("Message does not contain any field separator");
        }
        final String beginString = messageString.substring(2, index);
        final String messageType = getMessageType(messageString);
        final Message message = messageFactory.create(beginString, messageType);
        message.fromString(messageString, dataDictionary, dataDictionary != null);
        return message;
    }

    /**
     * NOTE: This method is intended for internal use.
     *
     * @param session       - the Session that will process the message
     * @param messageString
     * @return the parsed message
     * @throws quickfix.InvalidMessage
     */
    public static Message parse(Session session, String messageString) throws InvalidMessage {
        final String beginString = getStringField(messageString, FixProtocol.FIELD_BEGIN_STRING);
        final String msgType = getMessageType(messageString);

        ApplVerID applVerID;

        if (FixVersions.BEGINSTRING_FIXT11.equals(beginString)) {
            applVerID = getApplVerID(session, messageString);
        } else {
            applVerID = toApplVerID(beginString);
        }

        final MessageFactory messageFactory = session.getMessageFactory();

        final DataDictionaryProvider ddProvider = session.getDataDictionaryProvider();
        final DataDictionary sessionDataDictionary =
                ddProvider == null ? null :
                        ddProvider.getSessionDataDictionary(beginString);
        final DataDictionary applicationDataDictionary =
                ddProvider == null ? null :
                        ddProvider.getApplicationDataDictionary(applVerID);

        final Message message = messageFactory.create(beginString, msgType);
        final DataDictionary payloadDictionary =
                MessageUtils.isAdminMessage(msgType) ?
                        sessionDataDictionary :
                        applicationDataDictionary;

        message.parse(messageString, sessionDataDictionary, payloadDictionary, payloadDictionary != null);

        return message;
    }

    private static ApplVerID getApplVerID(Session session, String messageString) throws InvalidMessage {

        ApplVerID applVerID = null;

        final String applVerIdString = getStringField(messageString, FixProtocol.FIELD_APP_VERSION_ID);
        if (applVerIdString != null) {
            applVerID = ApplVerID.getByValue(applVerIdString);
        }

        if (applVerID == null) {
            applVerID = session.getTargetDefaultApplicationVersionID();
        }

        if (applVerID == null && (isLogon(messageString) || isLogout(messageString))) {
            final String defaultApplVerIdString =
                    getStringField(messageString, FixProtocol.FIELD_DEFAULT_APP_VERSION_ID);

            if (defaultApplVerIdString != null) {
                applVerID = ApplVerID.getByValue(defaultApplVerIdString);
            }
        }

        if (applVerID == null) {
            throw new InvalidMessage("Can't determine ApplVerID for message");
        }

        return applVerID;
    }

    public static boolean isAdminMessage(String msgType) {
        return msgType.length() == 1 && ADMIN_MSG_TYPES.contains(msgType);
    }

    public static boolean isHeartbeat(String message) {
        return isMessageType(message, FixProtocol.MESSAGE_HEARTBEAT);
    }

    public static boolean isLogon(String message) {
        return isMessageType(message, FixProtocol.MESSAGE_LOGON);
    }

    public static boolean isLogout(String message) {
        return isMessageType(message, FixProtocol.MESSAGE_LOGOUT);
    }

    private static boolean isMessageType(String message, String msgType) {
        try {
            return msgType.equals(getMessageType(message));
        } catch (final InvalidMessage e) {
            return false;
        }
    }

    public static String getMessageType(String messageString) throws InvalidMessage {
        final String value = getStringField(messageString, FixProtocol.FIELD_MESSAGE_TYPE);
        if (value == null) {
            throw new InvalidMessage("Missing or garbled message type in " + messageString);
        }
        return value;
    }

    public static String getStringField(String messageString, int tag) {
        String value = null;
        final String tagString = Integer.toString(tag);
        int start = messageString.indexOf(tagString, 0);
        while (start != -1 && value == null) {
            if ((start == 0 || messageString.charAt(start - 1) == FIELD_SEPARATOR)) {
                int end = start + tagString.length();
                if ((end + 1) < messageString.length() && messageString.charAt(end) == '=') {
                    // found tag, get value
                    start = end = (end + 1);
                    for (; end < messageString.length()
                            && messageString.charAt(end) != FIELD_SEPARATOR; end++) {
                    }
                    if (end == messageString.length()) {
                        return null;
                    } else {
                        value = messageString.substring(start, end);
                    }
                }
            }
            start = messageString.indexOf(tagString, start + 1);
        }
        return value;
    }

    private static Map<ApplVerID, String> applVerIDtoBeginString = new HashMap<ApplVerID, String>() {
        {
            // No support for earlier versions of FIX
            put(ApplVerID.FIX40, FixVersions.BEGINSTRING_FIX40);
            put(ApplVerID.FIX41, FixVersions.BEGINSTRING_FIX41);
            put(ApplVerID.FIX42, FixVersions.BEGINSTRING_FIX42);
            put(ApplVerID.FIX43, FixVersions.BEGINSTRING_FIX43);
            put(ApplVerID.FIX44, FixVersions.BEGINSTRING_FIX44);
            put(ApplVerID.FIX50, FixVersions.FIX50);
        }
    };

    /**
     * Convert an ApplVerID to a "begin string"
     *
     * @param applVerID
     * @return the begin string for the specified ApplVerID.
     * @throws org.quickfixj.QFJException if conversion fails.
     * @see kz.kase.fix.ApplVerID
     */
    public static String toBeginString(ApplVerID applVerID) throws QFJException {
        final String beginString = applVerIDtoBeginString.get(applVerID);
        if (beginString == null) {
            throw new QFJException("Unknown or unsupported ApplVerID: " + applVerID.getValue());
        }
        return beginString;
    }

    private static Map<String, ApplVerID> beginStringToApplVerID = new HashMap<String, ApplVerID>() {
        {
            // No support for earlier versions of FIX
            put(FixVersions.BEGINSTRING_FIX40, ApplVerID.FIX40);
            put(FixVersions.BEGINSTRING_FIX41, ApplVerID.FIX41);
            put(FixVersions.BEGINSTRING_FIX42, ApplVerID.FIX42);
            put(FixVersions.BEGINSTRING_FIX43, ApplVerID.FIX43);
            put(FixVersions.BEGINSTRING_FIX44, ApplVerID.FIX44);
            put(FixVersions.FIX50, ApplVerID.FIX50);
            put(FixVersions.FIX50SP2, ApplVerID.FIX50SP2);
        }
    };

    /**
     * Convert a begin string to an ApplVerID
     *
     * @param beginString
     * @return the ApplVerID for the specified begin string.
     * @throws org.quickfixj.QFJException if conversion fails.
     * @see quickfix.FixVersions
     */
    public static ApplVerID toApplVerID(String beginString) throws QFJException {
        final ApplVerID applVerID = beginStringToApplVerID.get(beginString);
        if (applVerID == null) {
            throw new QFJException("Can't convert to ApplVerID: " + beginString);
        }
        return applVerID;
    }

    public static Message parse(String messageString,
                                MessageFactory messageFactory,
                                DataDictionaryProvider ddProvider) throws InvalidMessage {

        final String beginString = getStringField(messageString, FixProtocol.FIELD_BEGIN_STRING);
        final String msgType = getMessageType(messageString);
        ApplVerID applVerID = toApplVerID(beginString);

        final DataDictionary sessionDataDictionary =
                ddProvider == null ? null :
                        ddProvider.getSessionDataDictionary(beginString);
        final DataDictionary applicationDataDictionary =
                ddProvider == null ? null :
                        ddProvider.getApplicationDataDictionary(applVerID);

        final Message message = messageFactory.create(beginString, msgType);
        final DataDictionary payloadDictionary =
                isAdminMessage(msgType) ?
                        sessionDataDictionary :
                        applicationDataDictionary;

        message.parse(messageString, sessionDataDictionary, payloadDictionary, payloadDictionary != null);

        return message;
    }
}
