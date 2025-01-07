public enum LogResult {
    SUCCESS,
    FAIL,
    ERROR
}


enum LogReason {
    INVALID_CREDENTIAL,
    NOT_FOUND,
    TIMEOUT,
    UNKNOWN
    // 필요 시 계속 추가
}


public class LogMessage {
    private LogAction action;    // 예: LOGIN, LOGOUT
    private String subject;      // 예: "Student(20230001)"
    private String objectName;   // 예: "Course(CS101)" 등
    private LogResult result;    // 예: SUCCESS, FAIL
    private LogReason reason;    // 예: INVALID_CREDENTIAL
    private String extras;       // 예: "IP:192.168.0.10, UA:Mozilla/5.0" 등

    // private 생성자 (Builder를 통해서만 객체 생성)
    private LogMessage(LogAction action,
                       String subject,
                       String objectName,
                       LogResult result,
                       LogReason reason,
                       String extras) {
        this.action = action;
        this.subject = subject;
        this.objectName = objectName;
        this.result = result;
        this.reason = reason;
        this.extras = extras;
    }

    // 빌더로 LogMessage 생성
    public static LogMessageBuilder builder() {
        return new LogMessageBuilder();
    }

    // 최종적으로 로그 문자열로 출력될 형태
    // ACTION=액션 | SUBJECT=주체 | OBJECT=대상 | RESULT=결과 | REASON=원인 | EXTRAS=추가정보
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (action != null) {
            sb.append("ACTION=").append(action.name());
        }
        if (subject != null) {
            sb.append(" | SUBJECT=").append(subject);
        }
        if (objectName != null) {
            sb.append(" | OBJECT=").append(objectName);
        }
        if (result != null) {
            sb.append(" | RESULT=").append(result.name());
        }
        if (reason != null) {
            sb.append(" | REASON=").append(reason.name());
        }
        if (extras != null && !extras.isEmpty()) {
            sb.append(" | EXTRAS=").append(extras);
        }
        return sb.toString();
    }

    // 빌더 내부 클래스
    public static class LogMessageBuilder {
        private LogAction action;
        private String subject;
        private String objectName;
        private LogResult result;
        private LogReason reason;
        private String extras;

        public LogMessageBuilder action(LogAction action) {
            this.action = action;
            return this;
        }

        public LogMessageBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public LogMessageBuilder objectName(String objectName) {
            this.objectName = objectName;
            return this;
        }

        public LogMessageBuilder result(LogResult result) {
            this.result = result;
            return this;
        }

        public LogMessageBuilder reason(LogReason reason) {
            this.reason = reason;
            return this;
        }

        public LogMessageBuilder extras(String extras) {
            this.extras = extras;
            return this;
        }

        public LogMessage build() {
            return new LogMessage(action, subject, objectName, result, reason, extras);
        }
    }
}
