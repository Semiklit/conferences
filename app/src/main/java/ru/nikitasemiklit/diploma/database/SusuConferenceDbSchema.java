package ru.nikitasemiklit.diploma.database;

/**
 * Created by nikitasemiklit1 on 28.05.17.
 */

public class SusuConferenceDbSchema {
    public static final class ConferenceTable {
        public static final String NAME = "conferences";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESC = "desc";
            public static final String START_DATE = "start_date";
            public static final String END_DATE = "end_date";
            public static final String END_REGISTRAIOTN_DATE = "end_registration_date";
        }
    }

    public static final class ReportTable {
        public static final String NAME = "reports";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String SECTION_UUID = "section_uuid";
            public static final String USER_UUID = "user_uuid";
            public static final String TITLE = "title";
            public static final String DESC = "desc";
            public static final String DATE_ARRIVE = "date_arrive";
            public static final String DATE_LEAVE = "date_leave";
            public static final String TIME = "time";
        }
    }

    public static final class SectionTable {
        public static final String NAME = "sections";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String CONFERENCE_UUID = "conference_uuid";
            public static final String TITLE = "title";
            public static final String DESC = "desc";
        }
    }

    public static final class UserTable {
        public static final String NAME = "users";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String SURNAME = "surname";
        }
    }
}
