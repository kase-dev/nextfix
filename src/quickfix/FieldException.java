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

public class FieldException extends RuntimeException {

    private final int field;

    private final SessionRejectReason sessionRejectReason;

    public FieldException(SessionRejectReason sessionRejectReason) {
        this(sessionRejectReason, -1);
    }

    public FieldException(SessionRejectReason reason, int field) {
        super(SessionRejectReasonText.getMessage(reason) + ", field=" + field);
        this.sessionRejectReason = reason;
        this.field = field;
    }

    public FieldException(SessionRejectReason reason, String msg, int field) {
        super(msg);
        this.sessionRejectReason = reason;
        this.field = field;
    }

    public boolean isFieldSpecified() {
        return field != -1;
    }

    public int getField() {
        return field;
    }

    public SessionRejectReason getSessionRejectReason() {
        return sessionRejectReason;
    }
}
