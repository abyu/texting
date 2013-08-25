package com.skk.texting.constants;

public class TextMessageConstants {

    public static final String ID = "_id";
    public static final String THREAD_ID = "thread_id";
    public static final String ADDRESS = "address";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String DATE_SENT = "date_sent";
    public static final String PROTOCOL = "protocol";
    public static final String READ_STATUS = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String REPLY_PATH_PRESENT = "reply_path_present";
    public static final String SUBJECT = "subject";
    public static final String MESSAGE_TEXT = "body";
    public static final String SERVICE_CENTER = "service_center";
    public static final String LOCKED = "locked";
    public static final String ERROR_CODE = "error_code";
    public static final String SEEN = "seen";


    public static class MessageType {

        public static String ALL = "0";
        public static String INBOX = "1";
        public static String SENT = "2";
        public static String DRAFT = "3";
        public static String OUTBOX = "4";
        public static String FAILED = "5"; // for failed outgoing messages
        public static String QUEUED = "6";
    }

}
